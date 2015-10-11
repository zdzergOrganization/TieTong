package com.tietong.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tietong.dao.KPIMapper;
import com.tietong.pojo.EmployeeType;
import com.tietong.web.conf.StandardJsonResult;

@Controller
@RequestMapping("/kpi")
public class KPIController {
	@Autowired
	private KPIMapper kpiMapper;
		
	@RequestMapping(value="/dateset" , method=RequestMethod.POST)
	public String dateSet(String KPIMonth){
		//先删除计算月份数据
		kpiMapper.deleteMonthEmployee(KPIMonth);
		//插入计算月份人员类型
		kpiMapper.insertEmployeeInfoType(KPIMonth);
        
        return "redirect:/pages/employee_type.html?KPIMonth=" + KPIMonth;
	}
	
	@RequestMapping(value="/employee/getAllEmployeeInfoType")
	public @ResponseBody StandardJsonResult getAllEmployeeInfoType(String KPIMonth){
		//查询当月人员类型
		List<EmployeeType> employees = kpiMapper.getEmployeeInfoType(KPIMonth);
	    
		return new StandardJsonResult(employees);
	}
	
	

}
