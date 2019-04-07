package com.tckj.icloud.pojo;

import java.io.Serializable;

/**
 * @author LiZG
 * @version 1.0
 * @date 2019/4/7 23:22
 */
public class SuffixManage implements Serializable {

	private Integer id;
	/**
	 * 扩展名
	 */
	private String name;
	/**
	 * 类型 1图片 2文档 3视频 4音频 0其他
	 */
	private Integer type;

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
}
