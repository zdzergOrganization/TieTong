package com.tietong.web.conf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Web资源搜索控制器<br>
 * 本控制器用于向前端输出经过VM渲染的js、tpl等资源文件，目的为了将后端的参数带到前端。<br>
 * 注意本Controller的目录被命名为resuource而不是resources，是因为本类里的各个方法需要通过各个过滤器，如SSO等
 * 
 * @version 1.0
 */
@Controller
@RequestMapping("/resources")
public class ResourceController {

	@RequestMapping(value = "/js/biz-init.js", method = RequestMethod.GET)
	public String jsBizInit(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("lng", request.getLocale());
		response.setContentType("application/x-javascript;charset=utf-8");
		return "resource/biz-init";
	}
}
