package com.tckj.icloud.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.tckj.icloud.pojo.SuffixManage;
import com.tckj.icloud.pojo.User;
import com.tckj.icloud.vo.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SuffixManageService extends IService<SuffixManage> {
	/**
	 * 根据类型查询后缀名
	 * @param type
	 * @return java.util.List<java.lang.String>
	 * @author LiZG
	 * @date 2019/04/07 23:43
	 */
	List<String> selectNames(int type);
}
