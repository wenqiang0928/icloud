package com.tckj.icloud.controller;

import com.tckj.icloud.service.DocsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
@RequestMapping("file")
public class DocsController {
    private DocsService docsService;

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
}
