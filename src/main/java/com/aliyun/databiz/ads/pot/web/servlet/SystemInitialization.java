/**
 * Copyright 2012 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.aliyun.databiz.ads.pot.web.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 系統初始化
 *
 * @author 李昊龙
 * @created 2014年11月17日上午11:27:08
 * @version aslan 2.0
 */
public class SystemInitialization implements ServletContextListener {

	private static final Logger LOG = LoggerFactory.getLogger(SystemInitialization.class);

	public void contextInitialized(ServletContextEvent sce) {
		LOG.info("simba initialize...");
		final ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
		//TODO
		LOG.info("simba initialized.");
	}

	public void contextDestroyed(ServletContextEvent sce) {
		//不需要做任何对象的销毁
	}
}
