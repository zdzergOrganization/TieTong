package com.aliyun.databiz.ads.pot.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliyun.databiz.ads.pot.data.object.Person;
import com.aliyun.databiz.ads.pot.service.PersonService;
import com.aliyun.databiz.ads.pot.web.ErrorCode;
import com.aliyun.databiz.ads.pot.web.StandardJsonResult;

/**
 * 
 * @author 李政（至行）
 * @version 1.0
 */
@Controller
@RequestMapping("/api/person")
public class PersonController {
	
	@Autowired
	private PersonService personService;

	@RequestMapping(value="", method=RequestMethod.GET)
	public @ResponseBody StandardJsonResult getPersonById(
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam("id") String id) throws Exception {
		try {
			Person person = personService.getPersonById(id);
			return new StandardJsonResult(person);
		} catch (Exception e) {
			return new StandardJsonResult(ErrorCode.COMMON_UNDEFINED_ERROR, e.getMessage(), null);
		}
		
	}
	
}
