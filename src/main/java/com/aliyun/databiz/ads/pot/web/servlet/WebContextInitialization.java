/**
 * Copyright 2012 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 *
 * File generated at: 2015年1月5日下午11:49:09
 */
package com.aliyun.databiz.ads.pot.web.servlet;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.velocity.VelocityLayoutViewResolver;

/**
 * 
 *
 * @author 李昊龙
 * @created 2015年1月5日下午11:49:09
 * @version aslan 2.0
 */
public class WebContextInitialization implements ApplicationContextAware {

	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		WebApplicationContext webctx = (WebApplicationContext) ctx;
		ServletContext servletContext = webctx.getServletContext();
		//1.添加ctx属性
		Map<String, Object> attributes = new HashMap<String, Object>();
		String contextPath = servletContext.getContextPath();
		if (contextPath == null) {
			contextPath = "/";
		} else if (!contextPath.endsWith("/")) {
			contextPath = contextPath + "/";
		}
		attributes.put("ctx", contextPath);
		//2.将公共参数设置到Velocity中
		VelocityLayoutViewResolver resolver = ctx.getBean(VelocityLayoutViewResolver.class);
		resolver.setAttributesMap(MapUtils.putAll(new HashMap<String, Object>(), new Object[] { "s", attributes }));
	}
}
