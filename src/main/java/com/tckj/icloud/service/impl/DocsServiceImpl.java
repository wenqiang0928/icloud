package com.tckj.icloud.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tckj.icloud.config.UploadConfig;
import com.tckj.icloud.mapper.DocsMapper;
import com.tckj.icloud.pojo.Docs;
import com.tckj.icloud.service.DocsService;
import com.tckj.icloud.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

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
}
