package com.tckj.icloud.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.tckj.icloud.pojo.SuffixManage;

import java.util.List;

public interface SuffixManageMapper extends BaseMapper<SuffixManage> {
	/**
	 * 根据类型查询后缀名
	 * @param type
	 * @return java.util.List<java.lang.String>
	 * @author LiZG
	 * @date 2019/04/07 23:44
	 */
	List<String> selectNames(int type);
}
