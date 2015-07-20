package com.aliyun.databiz.ads.pot.web;

/**
 * Copyright 2012 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 *
 * File generated at: 2015年1月20日下午4:46:51
 */

import java.io.IOException;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * 标准的JOSN响应convert类
 *
 * @author 李昊龙
 * @created 2015年1月20日下午4:46:51
 * @version aslan 2.0
 */
public class StandardMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
	@Override
	protected void writeInternal(Object object, HttpOutputMessage outputMessage) throws IOException,
			HttpMessageNotWritableException {
		if (object instanceof StandardJsonResult) {
			super.writeInternal(object, outputMessage);
		} else {
			StandardJsonResult jsonResult = new StandardJsonResult();
			jsonResult.setData(object);
			jsonResult.setErrCode(0);
			jsonResult.setErrMsg("success");
			super.writeInternal(jsonResult, outputMessage);
		}
	}
}
