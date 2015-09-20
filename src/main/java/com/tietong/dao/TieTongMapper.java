package com.tietong.dao;

import com.tietong.pojo.Employee;
import com.tietong.pojo.User;

public interface TieTongMapper {
	/**
	 * 根据用户名获取用户实例.<br>
	 * 
	 * @param username
	 * @return
	 */
	public User getUserByName(String username);
	
	/**
	 * 插入员工信息数据
	 * @param employee
	 */
	public void insertEmployeeInfo(Employee employee);
}
