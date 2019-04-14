package com.tckj.icloud.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.tckj.icloud.pojo.Docs;
import org.apache.ibatis.annotations.Param;

import javax.print.Doc;
import java.util.List;

public interface DocsMapper  extends BaseMapper<Docs> {
	/**
	 * 根据id逻辑删除
	 * @param id
	 * @return void
	 * @author LiZG
	 * @date 2019/04/06 10:58
	 */
	void deleteLogicById(int id);

	/**
	 * 更新文件状态
	 *
	 * @param ids
	 * @param status 0-未删除 1-已删除
	 */
	void updateDeleteStatus(@Param("ids") List<Integer> ids, @Param("status") int status);
}
