package com.tckj.icloud.service;

import com.tckj.icloud.pojo.User;
import com.tckj.icloud.vo.ResponseResult;
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

    /**
     * 新建文件夹
     * @param nowDirId
     * @param name
     * @param user
     * @return com.tckj.icloud.utils.Result
     * @author LiZG
     * @date 2019/04/05 8:59
     */
    ResponseResult addDir(int nowDirId, String name, User user);

    /**
     * 移动文件/文件夹
     * @param nowDirId
     * @param targetDirId
     * @param targetId
     * @param user
     * @return com.tckj.icloud.vo.ResponseResult
     * @author LiZG
     * @date 2019/04/05 9:21
     */
    ResponseResult moveDocs(int nowDirId, int targetDirId, int targetId, User user);

    /**
     * 查询目录中所有的文件及文件夹
     * @param dirId
     * @param user
     * @return com.tckj.icloud.vo.ResponseResult
     * @author LiZG
     * @date 2019/04/05 9:40
     */
    ResponseResult getAllDocsByPid(int dirId, User user);

    /**
     * 获取文件/文件夹详情
     * @param id
     * @param user
     * @return com.tckj.icloud.vo.ResponseResult
     * @author LiZG
     * @date 2019/04/05 9:50
     */
    ResponseResult getDetail(int id, User user);

    /**
     * 文件搜索
     * @param name
     * @param suffix
     * @param type
     * @param user
     * @return com.tckj.icloud.vo.ResponseResult
     * @author LiZG
     * @date 2019/04/05 9:58
     */
    ResponseResult findDocs(String name, String suffix, Integer type, User user);

    /** 
     * 
     * @param nowDirId
     * @param deleteId
     * @param user
     * @return com.tckj.icloud.vo.ResponseResult
     * @author LiZG
     * @date 2019/04/06 8:16
     */
	ResponseResult deleteDocs(int nowDirId, int deleteId, User user);
}
