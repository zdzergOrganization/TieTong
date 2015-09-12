package com.tietong.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/pages")
public class CommonPageController {
	
	@RequestMapping(value = "/{pageName}.html", method = RequestMethod.GET)
	public String to(@PathVariable("pageName") String pageName, ModelMap model, HttpServletRequest request) {
		return to(null, null, null, pageName, model, request);
	}
	
	@RequestMapping(value = "/{namespace}/{pageName}.html", method = RequestMethod.GET)
	public String to(@PathVariable("namespace") String namespace,
			@PathVariable("pageName") String pageName, ModelMap model, HttpServletRequest request) {
		return to(namespace, null, null, pageName, model, request);
	}

	@RequestMapping(value = "/{namespace}/{moduleName}/{pageName}.html", method = RequestMethod.GET)
	public String to(@PathVariable("namespace") String namespace, @PathVariable("moduleName") String moduleName,
			@PathVariable("pageName") String pageName, ModelMap model, HttpServletRequest request) {
		return to(namespace, moduleName, null, pageName, model, request);
	}

	@RequestMapping(value = "/{namespace}/{moduleName}/{subModuleName}/{pageName}.html", method = RequestMethod.GET)
	public String to(@PathVariable("namespace") String namespace, @PathVariable("moduleName") String moduleName,
			@PathVariable("subModuleName") String subModuleName, @PathVariable("pageName") String pageName,
			ModelMap model, HttpServletRequest request) {
		List<String> pagePaths = addIfNotNull(new ArrayList<String>(), 
				namespace, moduleName, subModuleName, pageName);
		String url = "pages/" + StringUtils.join(pagePaths.toArray(), "/");
		return url;
	}

	private List<String> addIfNotNull(List<String> list, String... strs) {
		for (String str : strs) {
			if (StringUtils.isNotBlank(str)) {
				list.add(str);
			}
		}
		return list;
	}

	public static String getPageVmName(String pageName) {
		if (StringUtils.isNotEmpty(pageName)) {
			int idx = pageName.indexOf('.');
			if (idx > 0) {// 注：“.”在第一个的话就不忽略
				return pageName.substring(0, idx);
			}
		}
		return pageName;
	}

}
