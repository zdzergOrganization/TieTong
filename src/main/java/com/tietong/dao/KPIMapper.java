package com.tietong.dao;

import java.util.List;

import com.tietong.pojo.EmployeeType;
import com.tietong.pojo.KPI_DX;

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
	

	public List<KPI_DX> getKPI_DX(String KPIMonth);
	
	/**
	 * 插入员工底薪
	 * @param KPIMonth
	 */
	public void insertKPI_DX(KPI_DX kpi_dx);
	
	
	
	
}
