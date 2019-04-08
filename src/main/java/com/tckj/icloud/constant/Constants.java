package com.tckj.icloud.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量定义
 *
 * @author zhaotao
 * @version 1.0
 * @date 2018/8/11 0011 11:38
 */
public interface Constants {

    /**
     * 结果代码枚举
     */
    enum ResultCodeConstants {
        /**
         * code 返回代码
         * name 代码描述
         */
        RCC_SUCCESS(0, "接口成功返回数据"),
        RCC_SUCCESS_NODATA(1, "接口成功但是无数据"),
        RCC_SUCCESS_REPEAT(2, "接口成功但是数据重复"),

        RCC_UNKNOWN_ERROR(-1, "系统设计之外的未知错误"),
        RCC_PARAMETER_INVALID(-2, "客户端请求参数无效(参数异常)"),
        RCC_SESSION_TOKEN_ERROR(-3, "会话token错误"),
        RCC_SESSION_RESTRICT_RATE(-4, "限制用户发送速率，用户请求太快"),
        RCC_PROJECT_ERROR(-5, "系统内部错误（报错）"),
        RCC_UPLOAD_ERROR(-6, "文件上传失败"),
        RCC_NO_PRIVILEGE(-7, "没有操作权限"),
        RCC_UPDATE_FAIL(-8, "更新失败"),
        RCC_FORMAT_ERROR(-9, "文件格式不正确"),
        RCC_DELETE_ERROR(-10, "文件删除失败"),
        RCC_VERSION_CHECKING_ERROR(-11, "版本检查失败"),

        /**
         * 文件/文件夹管理
         */
        FILE_NOT_EXIST(-201,"文件/文件夹不存在！"),
        FILE_WRONG_POSITION(-202,"要移动的文件不在当前文件夹中"),



        /**
         * 1 开头为判断文件在系统的状态
         */
        IS_HAVE(100, "文件已存在！"),

        NO_HAVE(101, "该文件没有上传过。"),

        ING_HAVE(102, "该文件上传了一部分。");

        private final int code;
        private final String msg;

        ResultCodeConstants(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

    }
    /**
     * 异常信息统一头信息<br>
     * 非常遗憾的通知您,程序发生了异常
     */
    public static final String Exception_Head = "boom。炸了。";
    /**
     * 缓存键值
     */
    public static final Map<Class<?>, String> cacheKeyMap = new HashMap<>();
    /**
     * 保存文件所在路径的key，eg.FILE_MD5:1243jkalsjflkwaejklgjawe
     */
    public static final String FILE_MD5_KEY = "FILE_MD5:";
    /**
     * 保存上传文件的状态
     */
    public static final String FILE_UPLOAD_STATUS = "FILE_UPLOAD_STATUS";
}
