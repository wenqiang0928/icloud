package com.tckj.icloud.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tckj.icloud.config.UploadConfig;
import com.tckj.icloud.constant.Constants;
import com.tckj.icloud.mapper.DocsMapper;
import com.tckj.icloud.mapper.SuffixManageMapper;
import com.tckj.icloud.pojo.Docs;
import com.tckj.icloud.pojo.SuffixManage;
import com.tckj.icloud.pojo.User;
import com.tckj.icloud.service.DocsService;
import com.tckj.icloud.service.SuffixManageService;
import com.tckj.icloud.utils.FileUtils;
import com.tckj.icloud.vo.ErrorResponse;
import com.tckj.icloud.vo.ResponseResult;
import com.tckj.icloud.vo.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tckj.icloud.utils.UploadUtils.addChunk;
import static com.tckj.icloud.utils.UploadUtils.getFileName;
import static com.tckj.icloud.utils.UploadUtils.isUploaded;
import static com.tckj.icloud.utils.UploadUtils.removeKey;

@Service
public class SuffixManageServiceImpl extends ServiceImpl<SuffixManageMapper, SuffixManage> implements SuffixManageService {
	@Autowired
	private SuffixManageMapper suffixManageMapper;

	@Override
	public List<String> selectNames(int type) {
		return suffixManageMapper.selectNames(type);
	}
}
