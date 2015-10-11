package com.tietong.dao;

import java.util.List;

import com.tietong.pojo.EmployeeType;

public interface KPIMapper {

	/**
	 * 插入员工信息数据
	 * @param KPIMonth
	 */
	public void insertEmployeeInfoType(String KPIMonth);
	
	/**
	 * 删除月份员工类型数据
	 * @param KPIMonth
	 * @return 
	 */
	public void deleteMonthEmployee(String KPIMonth);
	
	/**
	 * 查询月份员工类型数据
	 * @param KPIMonth
	 * @return 
	 */
	public List<EmployeeType> getEmployeeInfoType(String KPIMonth);
	
	
	
	
}
