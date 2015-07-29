package com.tietong.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tietong.dao.User;
import com.tietong.mapper.TieTongMapper;
import com.tietong.web.conf.Constants;
import com.tietong.web.conf.StandardJsonResult;

@Controller
public class RestController {
	
	@Autowired
	private TieTongMapper tietongmapper;
	/**
	 * 登录
	 * 判断账号密码是否正确.<br>
	 */
	@RequestMapping(value="/login" , method=RequestMethod.POST)
	public @ResponseBody String Login(
			@RequestParam("userName") String userName,
			@RequestParam("passWord") String passWord, 
			ModelMap model,
			HttpSession httpSession){
		User user = tietongmapper.getUserByName(userName);
		
		//如果密码正确
		if(user != null && user.getPassword().equals(passWord)){
			httpSession.setAttribute(Constants.SESSION_USER_NAME, userName);
			httpSession.setAttribute(Constants.SESSION_USER_ID, user.getId());
			return "redirect:/guizhou-pot/pages/oss/data.html";
		}
		
		//密码不对
		model.put("msg", Constants.LOGIN_FAILED_MSG);
		return "pages/login";
	}

}
