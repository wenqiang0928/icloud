package com.tckj.icloud.config;

import com.baomidou.mybatisplus.mapper.ISqlInjector;
import com.baomidou.mybatisplus.mapper.LogicSqlInjector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author LiZG
 * @version 1.0
 * @date 2019/4/6 10:26
 */
@Configuration
public class MyBatisPlusConfiguration {

	@Bean
	public ISqlInjector sqlInjector() {
		return new LogicSqlInjector();
	}
}
