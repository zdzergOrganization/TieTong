package com.tietong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tietong.pojo.EmployeeType;
import com.tietong.pojo.KPI_DX;
import com.tietong.pojo.KPI_GZ;
import com.tietong.pojo.KPI_TC;

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

	/**
	 * 删除月份底薪数据
	 * @param KPIMonth
	 * @return 
	 */
	public void deleteMonthDX(String KPIMonth);
	
	/**
	 * 删除月份提成数据
	 * @param KPIMonth
	 * @return 
	 */
	public void deleteMonthTC(String KPIMonth);
	
	/**
	 * 删除月份总工资数据
	 * @param KPIMonth
	 * @return 
	 */
	public void deleteMonthGZ(String KPIMonth);
	/**
	 * 插入员工底薪
	 * @param KPIMonth
	 */
	public void insertKPI_DX(KPI_DX kpi_dx);

	public void insertKPI_TC(KPI_TC kpi_tc);

	public void insertKPI_GZ(KPI_GZ kpi_gz);

	public List<KPI_DX> getKPI_DX(String KPIMonth);
	public KPI_DX getKPI_DXName(@Param(value="uploadMonth")String uploadMonth,@Param(value="employeeName")String employeeName);
	
	public List<KPI_TC> getKPI_TC(String KPIMonth);
	public KPI_TC getKPI_TCName(@Param(value="uploadMonth")String uploadMonth,@Param(value="employeeName")String employeeName);
	
	public List<KPI_GZ> getKPI_GZ(String KPIMonth);
	
	
	
	
}
