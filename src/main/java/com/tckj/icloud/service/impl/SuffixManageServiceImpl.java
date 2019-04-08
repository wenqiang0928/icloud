package com.tckj.icloud.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tckj.icloud.mapper.SuffixManageMapper;
import com.tckj.icloud.pojo.SuffixManage;
import com.tckj.icloud.service.SuffixManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuffixManageServiceImpl extends ServiceImpl<SuffixManageMapper, SuffixManage> implements SuffixManageService {
	@Autowired
	private SuffixManageMapper suffixManageMapper;

	@Override
	public List<String> selectNames(int type) {
		return suffixManageMapper.selectNames(type);
	}
}
