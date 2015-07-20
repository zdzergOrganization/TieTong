package com.tietong.web.conf;

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
 * @author 周玎
 * @created 2015年7月20日下午11:49:09
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
