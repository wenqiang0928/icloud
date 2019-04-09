package com.tckj.icloud.controller;

import com.tckj.icloud.constant.Constants;
import com.tckj.icloud.mapper.DocsMapper;
import com.tckj.icloud.pojo.User;
import com.tckj.icloud.service.DocsService;
import com.tckj.icloud.service.StorageService;
import com.tckj.icloud.service.UserService;
import com.tckj.icloud.vo.ErrorResponse;
import com.tckj.icloud.vo.MultipartFileParam;
import com.tckj.icloud.vo.ResponseResult;
import com.tckj.icloud.vo.SuccessResponse;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("docs")
public class DocsController {
    @Autowired
    private DocsService docsService;
    @Autowired
    private UserService userService;

    private static Logger logger = LoggerFactory.getLogger(DocsController.class);

//    @PostMapping("/upload")
//    public void upload(String name, Integer type, String path, Integer pid,
//                       String md5,
//                       Long size,
//                       Integer chunks,
//                       Integer chunk, Integer uploadUserId, String caseNo,
//                       MultipartFile file) throws IOException {
//        if (chunks != null && chunks != 0) {
//            docsService.uploadWithBlock(name, type, path, pid, md5, size, chunks, chunk, uploadUserId, caseNo, file);
//        } else {
//            docsService.upload(name, type, path, pid, md5, uploadUserId, caseNo, file);
//        }
//    }

    /**
     * 下载文件
     *
     * @param path
     * @param response
     * @return
     */
    @PostMapping("/downLoad")
    public void download(String path, HttpServletResponse response) {
        try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 取得文件的后缀名。
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();
            // 清空response
            response.reset();
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();

            // 设置response的Header
            response.setHeader("Content-disposition", "attachment;filename=" + filename);
//            response.setHeader("Content-length", "" + file.length());
            if(ext.equals("JPG")){
                response.setContentType("image/jpeg");
            }else if(ext.equals("PNG")){
                response.setContentType("image/png");
            }else if(ext.equals("MP4")){
                response.setContentType("video/mp4");
            }
            OutputStream toClient = response.getOutputStream();
//            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @GetMapping(value = "/getVideoStream")
    public void getVideoStream(String path, HttpServletResponse response) {
        try {
            FileInputStream fis;
            OutputStream os;
            fis = new FileInputStream(path);
            int size = fis.available();
            byte data[] = new byte[size];
            fis.read(data);
            fis.close();
            response.setContentType("video/mp4");
            os = response.getOutputStream();
            os.write(data);
            os.flush();
            os.close();
        } catch (Exception e) {

        }
    }

    /**
     * 新建文件夹
     *
     * @param nowDirId
     * @param name
     * @return com.tckj.icloud.vo.ResponseResult
     * @author LiZG
     * @date 2019/04/05 9:08
     */
    @PostMapping(value = "addDir")
    @ResponseBody
    public ResponseResult addDir(@RequestParam(value = "nowDirId") int nowDirId,
                                 @RequestParam(value = "addDirName") String name, HttpSession session) {
        User user = (User) session.getAttribute("user");
        return docsService.addDir(nowDirId, name, user);
    }

    /**
     * 移动文件/文件夹
     *
     * @param nowDirId    当前目录id
     * @param targetDirId 目标目录id
     * @param ids         要移动的文件id
     * @return com.tckj.icloud.vo.ResponseResult
     * @author LiZG
     * @date 2019/04/05 9:19
     */
    @PostMapping(value = "moveDocs")
    @ResponseBody
    public ResponseResult moveDocs(@RequestParam(value = "nowDirId") int nowDirId,
                                   @RequestParam(value = "targetDirId") int targetDirId,
                                   @RequestParam(value = "ids") String ids, HttpSession session) {
        User user = (User) session.getAttribute("user");
        return docsService.moveDocs(nowDirId, targetDirId, ids, user);
    }

    /**
     * 查询该目录下的文件
     *
     * @param dirId
     * @return com.tckj.icloud.vo.ResponseResult
     * @author LiZG
     * @date 2019/04/05 9:47
     */
    @GetMapping(value = "getAllDocsByPid")
    @ResponseBody
    public ResponseResult getAllDocsByPid(@RequestParam(value = "dirId") int dirId, Integer userId, HttpSession session) {

        User user = userService.selectById(userId);
        return docsService.getAllDocsByPid(dirId, user);
    }

    /**
     * 获取文件/文件夹详情
     *
     * @param id
     * @return com.tckj.icloud.vo.ResponseResult
     * @author LiZG
     * @date 2019/04/05 9:50
     */
    @GetMapping(value = "getDetail")
    @ResponseBody
    public ResponseResult getDetail(@RequestParam(value = "id") int id, HttpSession session) {
//        int userId = 1;
//
//        User user = userService.selectById(userId);
        User user = (User) session.getAttribute("user");
        return docsService.getDetail(id, user);
    }

    /**
     * 文件搜索
     *
     * @param name   文件名
     * @param suffix 文件类型：图片，文档，视频，音频
     * @param type   1目录  2文件
     * @return com.tckj.icloud.vo.ResponseResult
     * @author LiZG
     * @date 2019/04/05 9:58
     */
    @GetMapping(value = "findDocs")
    @ResponseBody
    public ResponseResult findDocs(@RequestParam(value = "name") String name,
                                   @RequestParam(value = "suffix", required = false) String suffix,
                                   @RequestParam(value = "type", required = false) Integer type, HttpSession session) {
//        int userId = 1;
//        User user = userService.selectById(userId);
        User user = (User) session.getAttribute("user");
        return docsService.findDocs(name, suffix, type, user);
    }

    /**
     * 批量删除文件/文件夹
     *
     * @param nowDirId
     * @param ids
     * @return com.tckj.icloud.vo.ResponseResult
     * @author LiZG
     * @date 2019/04/06 8:16
     */
    @DeleteMapping(value = "deleteDocs")
    @ResponseBody
    public ResponseResult deleteDocs(@RequestParam(value = "nowDirId") int nowDirId,
                                     @RequestParam(value = "ids") String ids, HttpSession session) {
//        int userId = 1;
//        User user = userService.selectById(userId);
        User user = (User) session.getAttribute("user");
        return docsService.deleteDocs(nowDirId, ids, user);
    }

    /**
     * 重命名
     *
     * @param nowDirId
     * @param docsId
     * @param name
     * @return com.tckj.icloud.vo.ResponseResult
     * @author LiZG
     * @date 2019/04/06 22:48
     */
    @PostMapping(value = "renameDocs")
    @ResponseBody
    public ResponseResult renameDocs(@RequestParam(value = "nowDirId") int nowDirId,
                                     @RequestParam(value = "docsId") int docsId,
                                     @RequestParam(value = "name") String name, HttpSession session) {
//        int userId = 1;
//        User user = userService.selectById(userId);
        User user = (User) session.getAttribute("user");
        return docsService.renameDocs(nowDirId, docsId, name, user);
    }

    /**
     * 文件夹树
     *
     * @param
     * @return com.tckj.icloud.vo.ResponseResult
     * @author LiZG
     * @date 2019/04/07 13:39
     */
    @GetMapping(value = "dirTree")
    @ResponseBody
    public ResponseResult dirTree(@RequestParam(value = "ids", required = false) String ids, HttpSession session) {
//        int userId = 1;
//        User user = userService.selectById(userId);
        User user = (User) session.getAttribute("user");
        return docsService.dirTree(ids, user);
    }

    /**
     * @param type 文件类型 1图片 2文档 3视频 4音频 5其他
     * @return com.tckj.icloud.vo.ResponseResult
     * @author LiZG
     * @date 2019/04/07 23:34
     */
    @GetMapping(value = "getDocsByType")
    @ResponseBody
    public ResponseResult getDocsByType(@RequestParam(value = "type") int type, HttpSession session) {
        User user = (User) session.getAttribute("user");
//        User user = userService.selectById(userId);
        return docsService.getDocsByType(type, user);
    }


    //================================================================================================================


    @Autowired
    private StorageService storageService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 秒传判断，断点判断
     *
     * @return
     */
    @RequestMapping(value = "checkFileMd5", method = RequestMethod.POST)
    @ResponseBody
    public Object checkFileMd5(String md5) throws IOException {
        Object processingObj = stringRedisTemplate.opsForHash().get(Constants.FILE_UPLOAD_STATUS, md5);
        if (processingObj == null) {
            return new ResponseResult(Constants.ResultCodeConstants.NO_HAVE);
        }
        String processingStr = processingObj.toString();
        boolean processing = Boolean.parseBoolean(processingStr);
        String value = stringRedisTemplate.opsForValue().get(Constants.FILE_MD5_KEY + md5);
        if (processing) {
            return new ResponseResult(Constants.ResultCodeConstants.IS_HAVE, value);
        } else {
            File confFile = new File(value);
            byte[] completeList = FileUtils.readFileToByteArray(confFile);
            List<String> missChunkList = new LinkedList<>();
            for (int i = 0; i < completeList.length; i++) {
                if (completeList[i] != Byte.MAX_VALUE) {
                    missChunkList.add(i + "");
                }
            }
            return new ResponseResult(Constants.ResultCodeConstants.IS_HAVE, missChunkList);
        }
    }

    /**
     * 上传文件
     *
     * @param param
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Object fileUpload(MultipartFileParam param, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        param.setUser(user);
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            logger.info("上传文件start。");
            try {
                // 方法1
                //storageService.uploadFileRandomAccessFile(param);
                // 方法2 这个更快点
                storageService.uploadFileByMappedByteBuffer(param);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("文件上传失败。{}", param.toString());
            }
            logger.info("上传文件end。");
        }
        return new SuccessResponse("上传成功");
    }


}
