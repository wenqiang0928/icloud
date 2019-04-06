package com.tckj.icloud.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.tckj.icloud.pojo.Docs;

import javax.print.Doc;

public interface DocsMapper  extends BaseMapper<Docs> {
	/**
	 * 根据id逻辑删除
	 * @param id
	 * @return void
	 * @author LiZG
	 * @date 2019/04/06 10:58
	 */
	void deleteLogicById(int id);
}
