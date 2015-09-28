package com.tietong.dao;

import java.util.List;

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
	
	/**
	 * 读取全部员工信息数据
	 * @param employee
	 */
	public List<Employee> getAllEmployeeInfo();
	
	/**
	 * 删除单个员工信息数据
	 * @param employee
	 * @return 
	 */
	public void employeeDel(int Id);
	
	
}
