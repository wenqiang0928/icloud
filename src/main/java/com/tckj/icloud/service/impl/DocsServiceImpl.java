package com.tckj.icloud.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tckj.icloud.config.UploadConfig;
import com.tckj.icloud.constant.Constants;
import com.tckj.icloud.mapper.DocsMapper;
import com.tckj.icloud.pojo.Docs;
import com.tckj.icloud.pojo.User;
import com.tckj.icloud.service.DocsService;
import com.tckj.icloud.utils.FileUtils;
import com.tckj.icloud.vo.ErrorResponse;
import com.tckj.icloud.vo.ResponseResult;
import com.tckj.icloud.vo.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tckj.icloud.utils.UploadUtils.*;
@Service
public class DocsServiceImpl extends ServiceImpl<DocsMapper, Docs> implements DocsService {
    @Autowired
    private DocsMapper docsMapper;
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
        Docs nowDir = baseMapper.selectById(nowDirId);
        if (ObjectUtils.isEmpty(nowDir)){
            return new SuccessResponse(Constants.ResultCodeConstants.FILE_NOT_EXIST);
        }
        String path = nowDir.getPath() + "/" + name;
        Docs newDocs = new Docs(name,path,nowDirId,user.getId(),new Date(),null);
        try{
            baseMapper.insert(newDocs);
            return new SuccessResponse(null);
        }catch (Exception e){
            e.printStackTrace();
            return new ErrorResponse(null);
        }
    }

    @Override
    public ResponseResult moveDocs(int nowDirId, int targetDirId, int targetId, User user) {
        //TODO 权限判断：是否可移动（待完善）

        Docs nowDir = baseMapper.selectById(nowDirId);
        Docs targetDir = baseMapper.selectById(targetDirId);
        Docs target = baseMapper.selectById(targetId);
        if (ObjectUtils.isEmpty(nowDir) || ObjectUtils.isEmpty(targetDir) || ObjectUtils.isEmpty(target)){
            return new SuccessResponse(Constants.ResultCodeConstants.FILE_NOT_EXIST);
        }
        if (target.getPid() != nowDirId){
            return new SuccessResponse(Constants.ResultCodeConstants.FILE_WRONG_POSITION);
        }
        target.setPid(targetDirId);
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
            nowDir = baseMapper.selectOne(queryDocs);
        } else {
            nowDir = baseMapper.selectById(dirId);

        }


        Wrapper<Docs> wrapper = new EntityWrapper<>();
        wrapper.eq("pid",dirId);
        wrapper.eq("is_delete",0);
        List<Docs> docsList = baseMapper.selectList(wrapper);

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
        Docs docs = baseMapper.selectById(id);
        if (ObjectUtils.isEmpty(docs) || docs.getIsDelete() == 1){
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
        List<Docs> docsList = baseMapper.selectList(wrapper);
        return new SuccessResponse(docsList);
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
        Docs resultRootDir = baseMapper.selectOne(queryDocs);
        if (ObjectUtils.isEmpty(resultRootDir)) {
            String path = "/" + user.getName();
            Docs rootDir = new Docs(user.getName(), path, 0, user.getId(), new Date(), null);
            Integer sign = baseMapper.insert(rootDir);
            if (sign != null && sign == 1) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }

    }
}
