package com.tckj.icloud.controller;

import com.tckj.icloud.pojo.User;
import com.tckj.icloud.service.DocsService;
import com.tckj.icloud.service.UserService;
import com.tckj.icloud.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
@RequestMapping("docs")
public class DocsController {
    @Autowired
    private DocsService docsService;
    @Autowired
    private UserService userService;

    @PostMapping("/upload")
    public void upload(String name, Integer type, String path, Integer pid,
                       String md5,
                       Long size,
                       Integer chunks,
                       Integer chunk, Integer uploadUserId, String caseNo,
                       MultipartFile file) throws IOException {
        if (chunks != null && chunks != 0) {
            docsService.uploadWithBlock(name, type, path, pid, md5, size, chunks, chunk, uploadUserId, caseNo, file);
        } else {
            docsService.upload(name, type, path, pid, md5, uploadUserId, caseNo, file);
        }
    }

    /**
     * 下载文件
     * @param path
     * @param response
     * @return
     */
    @PostMapping("/downLoad")
    public HttpServletResponse download(String path, HttpServletResponse response) {
        try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 取得文件的后缀名。
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return response;
    }

    /**
     * 新建文件夹
     * @param nowDirId
     * @param name
     * @return com.tckj.icloud.vo.ResponseResult
     * @author LiZG
     * @date 2019/04/05 9:08
     */
    @PostMapping(value = "addDir")
    @ResponseBody
    public ResponseResult addDir(@RequestParam(value = "nowDirId") int nowDirId,
                                 @RequestParam(value = "addDirName")String name){
        int userId = 1;

        User user = userService.selectById(userId);
        return docsService.addDir(nowDirId,name,user);
    }

    /**
     * 移动文件/文件夹
     * @param nowDirId      当前目录id
     * @param targetDirId   目标目录id
     * @param targetId      要移动的文件id
     * @return com.tckj.icloud.vo.ResponseResult
     * @author LiZG
     * @date 2019/04/05 9:19
     */
    @PostMapping(value = "moveDocs")
    @ResponseBody
    public ResponseResult moveDocs(@RequestParam(value = "nowDirId")int nowDirId,
                                   @RequestParam(value = "targetDirId")int targetDirId,
                                   @RequestParam(value = "targetId")int targetId){
        int userId = 1;

        User user = userService.selectById(userId);
        return docsService.moveDocs(nowDirId,targetDirId,targetId,user);
    }

    /**
     * 查询该目录下的文件
     * @param dirId
     * @return com.tckj.icloud.vo.ResponseResult
     * @author LiZG
     * @date 2019/04/05 9:47
     */
    @GetMapping(value = "getAllDocsByPid")
    @ResponseBody
    public ResponseResult getAllDocsByPid(@RequestParam(value = "dirId")int dirId){
        int userId = 1;

        User user = userService.selectById(userId);
        return docsService.getAllDocsByPid(dirId,user);
    }
    /**
     * 获取文件/文件夹详情
     * @param id
     * @return com.tckj.icloud.vo.ResponseResult
     * @author LiZG
     * @date 2019/04/05 9:50
     */
    @GetMapping(value = "getDetail")
    @ResponseBody
    public ResponseResult getDetail(@RequestParam(value = "id")int id){
        int userId = 1;

        User user = userService.selectById(userId);
        return docsService.getDetail(id,user);
    }

    /**
     * 文件搜索
     * @param name
     * @param suffix
     * @param type
     * @return com.tckj.icloud.vo.ResponseResult
     * @author LiZG
     * @date 2019/04/05 9:58
     */
    @GetMapping(value = "findDocs")
    public ResponseResult findDocs(@RequestParam(value = "name")String name,
                                   @RequestParam(value = "suffix",required = false)String suffix,
                                   @RequestParam(value = "type",required = false)Integer type){
        int userId = 1;
        User user = userService.selectById(userId);
        return docsService.findDocs(name,suffix,type,user);
    }
}
