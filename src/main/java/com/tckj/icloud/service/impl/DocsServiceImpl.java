package com.tckj.icloud.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tckj.icloud.config.UploadConfig;
import com.tckj.icloud.constant.Constants;
import com.tckj.icloud.mapper.DocsMapper;
import com.tckj.icloud.pojo.Docs;
import com.tckj.icloud.pojo.SuffixManage;
import com.tckj.icloud.pojo.User;
import com.tckj.icloud.service.DocsService;
import com.tckj.icloud.service.SuffixManageService;
import com.tckj.icloud.utils.FileUtils;
import com.tckj.icloud.vo.ErrorResponse;
import com.tckj.icloud.vo.ResponseResult;
import com.tckj.icloud.vo.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tckj.icloud.utils.UploadUtils.*;
@Service
public class DocsServiceImpl extends ServiceImpl<DocsMapper, Docs> implements DocsService {
    @Autowired
    private DocsMapper docsMapper;
    @Autowired
    private SuffixManageService suffixManageService;
    @Override
    public void upload(String name, Integer type,  String path, Integer pid, String md5, Integer createUserId, String caseNo, MultipartFile file) throws IOException {
        String spath= UploadConfig.path+path+file.getName();
        FileUtils.write(spath,file.getInputStream());
        docsMapper.insert(new Docs(name,type,file.getSize(),file.getName().substring(file.getName().lastIndexOf(".")+1),md5,path,pid,createUserId,new Date(),0,caseNo));
    }

    @Override
    public void uploadWithBlock(String name, Integer type, String path, Integer pid, String md5, Long size, Integer chunks, Integer chunk, Integer createUserId, String caseNo, MultipartFile file) throws IOException {
        String fileName = getFileName(md5, chunks);
        FileUtils.writeWithBlok(UploadConfig.path + fileName, size, file.getInputStream(), file.getSize(), chunks, chunk);
        addChunk(md5,chunk);
        if (isUploaded(md5)) {
            removeKey(md5);
            docsMapper.insert(new Docs(name,type,file.getSize(),file.getName().substring(file.getName().lastIndexOf(".")+1),md5,path,pid,createUserId,new Date(),0,caseNo));
        }
    }
    @Override
    public ResponseResult addDir(int nowDirId, String name, User user) {
        initDir(user);
        Docs nowDir = getDocsByIdAndNotDelete(nowDirId);
        if (ObjectUtils.isEmpty(nowDir)){
            return new ResponseResult(Constants.ResultCodeConstants.FILE_NOT_EXIST);
        }
        Docs newDocs = new Docs(name,"/",nowDirId,user.getId(),new Date(),null,new Date());
        try{
            docsMapper.insert(newDocs);
            return new SuccessResponse(null);
        }catch (Exception e){
            e.printStackTrace();
            return new ErrorResponse(null);
        }
    }

    @Override
    public ResponseResult moveDocs(int nowDirId, int targetDirId, String ids, User user) {
        //TODO 权限判断：是否可移动（待完善）

        Docs nowDir = getDocsByIdAndNotDelete(nowDirId);
        Docs targetDir = getDocsByIdAndNotDelete(targetDirId);
        if (ObjectUtils.isEmpty(nowDir) || ObjectUtils.isEmpty(targetDir)){
            return new ResponseResult(Constants.ResultCodeConstants.FILE_NOT_EXIST);
        }

        String[] idStrArr = ids.split(",");
        for (String idStr : idStrArr){
            int targetId = Integer.parseInt(idStr);
            Docs target = getDocsByIdAndNotDelete(targetId);
            if (ObjectUtils.isEmpty(target)){
                return new ResponseResult(Constants.ResultCodeConstants.FILE_NOT_EXIST);
            }
            if (target.getPid() != nowDirId){
                return new ResponseResult(Constants.ResultCodeConstants.FILE_WRONG_POSITION);
            }
            target.setPid(targetDirId);
            target.setModifyTime(new Date());
            docsMapper.updateById(target);
        }

        return new SuccessResponse(null);
    }

    @Override
    public ResponseResult getAllDocsByPid(int dirId, User user) {
        //TODO 权限检查

        //如果当前是根目录，则dirId应该传0，根据pid=0进行查询
        Docs nowDir;
        if (dirId == 0){
            initDir(user);
            Docs queryDocs = new Docs();
            queryDocs.setPid(0);
            queryDocs.setCreateUserId(user.getId());
            queryDocs.setIsDelete(0);
            queryDocs.setType(1);
            nowDir = docsMapper.selectOne(queryDocs);
        } else {
            nowDir = docsMapper.selectById(dirId);

        }


        Wrapper<Docs> wrapper = new EntityWrapper<>();
        wrapper.eq("pid",dirId);
        wrapper.eq("is_delete",0);
        List<Docs> docsList = docsMapper.selectList(wrapper);

        Map<String,Object> resultMap = new HashMap<>(2);
        //当前文件夹内容
        resultMap.put("docsList",docsList);
        //当前文件夹对象
        resultMap.put("nowDir",nowDir);

        return new SuccessResponse(resultMap);
    }

    @Override
    public ResponseResult getDetail(int id, User user) {
        //TODO 权限

        //判断当前文件/文件夹是否存在
        Docs docs = getDocsByIdAndNotDelete(id);

        if (ObjectUtils.isEmpty(docs)){
            return new SuccessResponse(Constants.ResultCodeConstants.FILE_NOT_EXIST);
        }
        return new SuccessResponse(docs);
    }

    @Override
    public ResponseResult findDocs(String name, String suffix, Integer type, User user) {
        //TODO 权限检查

        Wrapper<Docs> wrapper = new EntityWrapper<>();
        wrapper.like("name",name);
        if (suffix != null && suffix.trim().length() > 0){
            wrapper.eq("suffix",suffix);
        }
        if (type != null){
            wrapper.eq("type",type);
        }
        wrapper.eq("create_user_id",user.getId());
        List<Docs> docsList = docsMapper.selectList(wrapper);
        return new SuccessResponse(docsList);
    }

    @Override
    public ResponseResult deleteDocs(int nowDirId, String ids, User user) {
        //TODO 权限检查

        String[] idStringArr = ids.split(",");
        //先查出要删除的文件对象，如果为文件，直接删除；如果为文件夹，递归删除子文件夹及其中的所有文件
        //删除所有的文佳和文件夹
        for (String idStr:idStringArr){
            int deleteId = Integer.parseInt(idStr);
            Docs deleteDocs = getDocsByIdAndNotDelete(deleteId);

            if (ObjectUtils.isEmpty(deleteDocs)){
                return new ResponseResult(Constants.ResultCodeConstants.FILE_NOT_EXIST);
            }

            if (deleteDocs.getType() == 2){
                //文件直接删除
                docsMapper.deleteLogicById(deleteId);
                //TODO 服务器删除文件实体
            } else if (deleteDocs.getType() == 1) {
                //删除该文件夹及文件夹下所有内容
                deleteByPid(deleteId);
            } else {
                //todo 文件类型错误
            }
        }


        return new SuccessResponse(null);
    }

    @Override
    public ResponseResult renameDocs(int nowDirId, int docsId, String name, User user) {
        //TODO 权限检查

        Docs docs = getDocsByIdAndNotDelete(docsId);
        if (ObjectUtils.isEmpty(docs)){
            return new ResponseResult(Constants.ResultCodeConstants.FILE_NOT_EXIST);
        }
        docs.setName(name);
        docs.setModifyTime(new Date());
        docsMapper.updateById(docs);

        return new SuccessResponse(null);
    }

    @Override
    public ResponseResult dirTree(String ids,User user) {
        //TODO 权限检查
        //如果ids为空，则查所有
        //否则，将ids剔除
        List<String> idStrList = new ArrayList<>();
        if (ids != null && ids.length() > 0) {
            String[] idStrArr = ids.split(",");
            idStrList = Arrays.asList(idStrArr);
        }

        Docs rootQueryDocs = new Docs();
        rootQueryDocs.setPid(0);
        rootQueryDocs.setName(user.getName());
        rootQueryDocs.setType(1);
        rootQueryDocs.setIsDelete(0);
        Docs rootDir = docsMapper.selectOne(rootQueryDocs);

        Map<String,Object> map = new HashMap<>(3);
        map.put("id",rootDir.getId());
        map.put("text","全部文件夹");
        map.put("nodes",getSubDir(rootDir.getId(),idStrList));

        List<Map<String,Object>> list = new ArrayList<>();
        list.add(map);

        return new SuccessResponse(list);
    }

    @Override
    public ResponseResult getDocsByType(int type,User user) {
        //todo 权限检查

        String suffixStr = "";
        if (type == 0){
            List<SuffixManage> all = suffixManageService.selectList(new EntityWrapper<>());
            for (SuffixManage suffixManage:all){
                suffixStr += suffixManage.getName() + ",";
            }
            System.out.println(suffixStr);
            suffixStr = suffixStr.substring(0,suffixStr.length() - 1);
        } else {
            List<String> suffixList = suffixManageService.selectNames(type);
            for (String suffix : suffixList){
                suffixStr += suffix + ",";
            }
            suffixStr = suffixStr.substring(0,suffixStr.length() - 1);


        }
        Wrapper<Docs> wrapper = new EntityWrapper<>();
        wrapper.eq("is_delete",0);
        if (type == 0) {
            wrapper.notIn("suffix",suffixStr);
        }else {
            wrapper.in("suffix", suffixStr);
        }
        wrapper.eq("create_user_id",user.getId());
        List<Docs> docsList = docsMapper.selectList(wrapper);

        return new SuccessResponse(docsList);
    }

    private List<Map<String,Object>> getSubDir(int dirId,List<String> idStrList){
        List<Map<String,Object>> resultList = new ArrayList<>();
        Wrapper<Docs> wrapper = new EntityWrapper<>();
        wrapper.eq("pid",dirId);
        wrapper.eq("type",1);
        wrapper.eq("is_delete",0);
        List<Docs> docsList = docsMapper.selectList(wrapper);
        if (docsList != null && docsList.size() > 0){
            for (Docs docs:docsList){
                if (idStrList.contains(docs.getId().toString())){
                    continue;
                }
                Map<String,Object> nodeMap = new HashMap<>(3);
                nodeMap.put("id",docs.getId());
                nodeMap.put("text",docs.getName());
                nodeMap.put("nodes",getSubDir(docs.getId(),idStrList));
                resultList.add(nodeMap);
            }
        }
        return resultList;
    }

    /**
     * 递归删除文件夹及里面的子文件/子文件夹
     * @param pid
     * @return void
     * @author LiZG
     * @date 2019/04/06 10:05
     */
    private void deleteByPid(int pid){
        /*
        两种方案：
        1、先查出所有的pid，再根据pid进行删除
        2、递归查询数据库，直到查不出pid为止
        目前选择：递归查询并删除
        */

        //删除pid这个对象
        docsMapper.deleteLogicById(pid);
        //查询pid的子文件/文件夹，如果是文件，直接删除；如果是文件夹，再调一次这个方法
        Wrapper<Docs> wrapper = new EntityWrapper<>();
        wrapper.eq("is_delete",0);
        wrapper.eq("pid",pid);
        List<Docs> subDocsList = docsMapper.selectList(wrapper);
        if (subDocsList != null && subDocsList.size() > 0) {
            for (Docs docs : subDocsList) {
                if (docs.getType() == 2) {
                    docsMapper.deleteLogicById(docs.getId());
                    //TODO 删除服务器文件实体
                } else if (docs.getType() == 1) {
                    deleteByPid(docs.getId());
                }
            }
        }
    }

    /**
     * 创建用户根目录
     * @param user
     * @return boolean
     * @author LiZG
     * @date 2019/04/05 23:17
     */
    private boolean initDir(User user){
        //从数据库查询，如果根目录已存在，直接返回true
        //如果根目录不存在，则创建根目录
        Docs queryDocs = new Docs();
        queryDocs.setPid(0);
        queryDocs.setName(user.getName());
        queryDocs.setIsDelete(0);
        Docs resultRootDir = docsMapper.selectOne(queryDocs);
        if (ObjectUtils.isEmpty(resultRootDir)) {
            String path = "/" + user.getName();
            Docs rootDir = new Docs(user.getName(), path, 0, user.getId(), new Date(), null,new Date());
            Integer sign = docsMapper.insert(rootDir);
            if (sign != null && sign == 1) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }
    /**
     * 根据id查询未删除的docs
     */
    private Docs getDocsByIdAndNotDelete(int id){
        Docs docs = new Docs();
        docs.setIsDelete(0);
        docs.setId(id);
        return docsMapper.selectOne(docs);
    }
}
