package com.tietong.web.conf;

import java.io.IOException;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * 标准的JOSN响应convert类
 *
 * @author 周玎  
 * @created 2015-7-20 11:40:06
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
