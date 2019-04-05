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
import java.util.List;

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
        Docs nowDir = baseMapper.selectById(nowDirId);
        if (ObjectUtils.isEmpty(nowDir)){
            return new SuccessResponse(Constants.ResultCodeConstants.FILE_NOT_EXIST);
        }
        Docs newDocs = new Docs(name,nowDirId,user.getId(),new Date(),null);
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

        Wrapper<Docs> wrapper = new EntityWrapper<>();
        wrapper.eq("pid",dirId);
        wrapper.eq("is_delete",0);
        List<Docs> docsList = baseMapper.selectList(wrapper);

        return new SuccessResponse(docsList);
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
}
