package com.tietong.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tietong.dao.TieTongMapper;
import com.tietong.pojo.User;
import com.tietong.web.conf.Constants;

@Controller
public class UserLoginController {
	@Autowired
	private TieTongMapper tieTongMapper;
	
	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	public String login(@RequestParam("userName") String userName, @RequestParam("password") String password,
			ModelMap model, HttpSession httpSession) {
		User user = tieTongMapper.getUserByName(userName);
		if(user != null && user.getPassword().equals(password)){
			httpSession.setAttribute(Constants.SESSION_USER_NAME, userName);
			return "pages/employee";
		}
		return "pages/login";
	}
	
	@RequestMapping(value = "/user/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) throws Exception {
		httpSession.removeAttribute(Constants.SESSION_USER_NAME);
		return "pages/login";
	}
	
}
