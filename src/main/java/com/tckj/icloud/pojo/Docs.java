package com.tckj.icloud.pojo;

import com.baomidou.mybatisplus.annotations.TableLogic;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件类  为了防止和jdk File 使用过程中不方便 所以取名叫docs
 *
 */
public class Docs implements Serializable {
    private Integer id;
    /**
     * 文件或者目录名称
     */
    private String name;
    /**
     * 1 目录 2  文件
     */
    private Integer type;
    /**
     * 文件大小
     */
    private String size;
    /**
     * 文件类型 格式后缀
     */
    private String suffix;
    /**
     * 文件的md5校验值
     */
    private String md5CheckSum;
    /**
     * 目录的相对路径
     */
    private String path;
    /**
     * 父亲id
     */
    private Integer pid;
    /**
     * 上传者userId
     */
    private Integer createUserId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 是否删除0   1 是
     */
    @TableLogic
    private Integer isDelete;

    /**
     * 案件号
     */
    private String caseNo;
    /**
     * 案件详情
     */
    private String caseDesc;
    /**
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 警情编号
     */
    private String policeSentiment;

    /**
     * 案件名称
     */
    private String caseName;

    /**
     *案发时间
     */
    private String caseTime;
    /**
     * 案发地点
     */
    private String caseAddr;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getMd5CheckSum() {
        return md5CheckSum;
    }

    public void setMd5CheckSum(String md5CheckSum) {
        this.md5CheckSum = md5CheckSum;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public String getCaseDesc() {
        return caseDesc;
    }

    public void setCaseDesc(String caseDesc) {
        this.caseDesc = caseDesc;
    }


    public String getPoliceSentiment() {
        return policeSentiment;
    }

    public void setPoliceSentiment(String policeSentiment) {
        this.policeSentiment = policeSentiment;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getCaseTime() {
        return caseTime;
    }

    public void setCaseTime(String caseTime) {
        this.caseTime = caseTime;
    }

    public String getCaseAddr() {
        return caseAddr;
    }

    public void setCaseAddr(String caseAddr) {
        this.caseAddr = caseAddr;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Docs(){}

    public Docs(String name, Integer type, String size, String suffix, String md5CheckSum, String path, Integer pid,
                Integer createUserId,  String caseNo, String caseDesc,String caseName,String caseAddr,String caseTime,String policeSentiment) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.suffix = suffix;
        this.md5CheckSum = md5CheckSum;
        this.path = path;
        this.pid = pid;
        this.createUserId = createUserId;
        this.createTime = new Date();
        this.isDelete = 0;
        this.caseNo = caseNo;
        this.caseDesc = caseDesc;
        this.modifyTime = new Date();
        this.modifyTime = modifyTime;
        this.caseName=caseName;
        this.caseAddr=caseAddr;
        this.caseTime=caseTime;
        this.policeSentiment=policeSentiment;
    }
    /**
     * 目录
     * @param name
     * @param pid
     * @param createUserId
     * @param createTime
     * @param caseNo
     * @return
     * @author LiZG
     * @date 2019/04/05 9:11
     */
    public Docs(String name,String path,Integer pid,Integer createUserId,Date createTime, String caseNo,Date modifyTime){
        this.name = name;
        this.type = 1;
        this.path = path;
        this.pid = pid;
        this.createUserId = createUserId;
        this.createTime = createTime;
        this.isDelete = 0;
        this.caseNo = caseNo;
        this.modifyTime = modifyTime;
    }
}
