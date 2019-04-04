package com.tckj.icloud.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DocsService {
    void upload(String name,Integer type,String path,Integer pid,
                String md5,Integer createUserId,String caseNo,
                MultipartFile file) throws IOException;

    void uploadWithBlock(String name,Integer type,String path,Integer pid,
                         String md5,
                         Long size,
                         Integer chunks,
                         Integer chunk,Integer uploadUserId,String caseNo,
                         MultipartFile file) throws IOException;
}
