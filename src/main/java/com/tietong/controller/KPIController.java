package com.tietong.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tietong.dao.KPIMapper;
import com.tietong.dao.UploadTablesStatusMapper;
import com.tietong.pojo.EmployeeType;
import com.tietong.pojo.KPI_DX;
import com.tietong.pojo.KPI_GZ;
import com.tietong.pojo.KPI_TC;
import com.tietong.service.JK_DXCount;
import com.tietong.service.XZW_DXCount;
import com.tietong.service.YTH_DXCount;
import com.tietong.service.ZX_DXCount;
import com.tietong.service.ZX_GZCount;
import com.tietong.service.ZX_TCCount;
import com.tietong.web.conf.StandardJsonResult;

@Controller
//@RequestMapping("/kpi")
public class KPIController {
	@Autowired
	private KPIMapper kpiMapper;
	@Autowired
	private UploadTablesStatusMapper uploadTablesStatusMapper;
	
	/**
	 * 算出当月人员入职时长类型
	 * @param KPIMonth
	 * @return
	 */
	@RequestMapping(value="/kpi/dateset" , method=RequestMethod.POST)
	public String dateSet(String KPIMonth){
		//先删除计算月份数据
		kpiMapper.deleteMonthEmployee(KPIMonth);
		//插入计算月份人员类型
		kpiMapper.insertEmployeeInfoType(KPIMonth);
        
        return "redirect:/pages/employee_type.html?KPIMonth=" + KPIMonth;
	}
	
	/**
	 * 查询人员入职时长类型
	 * @param KPIMonth
	 * @return
	 */
	@RequestMapping(value="/kpi/employee/getAllEmployeeInfoType")
	public @ResponseBody StandardJsonResult getAllEmployeeInfoType(String KPIMonth){
		//查询当月人员类型
		List<EmployeeType> employees = kpiMapper.getEmployeeInfoType(KPIMonth);
	    
		return new StandardJsonResult(employees);
	}
	
	/**
	 * 查询当月计算的底薪
	 * @param KPIMonth
	 * @return
	 */
	@RequestMapping(value="/kpi/getDX")
	public @ResponseBody StandardJsonResult getDX(String KPIMonth){
		//查询当月人员底薪
		List<KPI_DX> kpi_dxs = kpiMapper.getKPI_DX(KPIMonth);
	    
		return new StandardJsonResult(kpi_dxs);
	}
	
	/**
	 * 查询当月计算的提成
	 * @param KPIMonth
	 * @return
	 */
	@RequestMapping(value="/kpi/getTC")
	public @ResponseBody StandardJsonResult getTC(String KPIMonth){
		//查询当月人员提成
		List<KPI_TC> kpi_tcs = kpiMapper.getKPI_TC(KPIMonth);
	    
		return new StandardJsonResult(kpi_tcs);
	}
	
	/**
	 * 查询当月计算的总工资
	 * @param KPIMonth
	 * @return
	 */
	@RequestMapping(value="/kpi/getGZ")
	public @ResponseBody StandardJsonResult getGZ(String KPIMonth){
		//查询当月人员提成
		List<KPI_GZ> kpi_gzs = kpiMapper.getKPI_GZ(KPIMonth);
	    
		return new StandardJsonResult(kpi_gzs);
	}
	
	/**
	 * 底薪计算
	 * @param KPIMonth
	 * @return
	 */
	@RequestMapping(value="/pages/kpi/kpi_dx" , method=RequestMethod.GET)
	public String kpi_dx(String KPIMonth) {
		
		// 先删除计算月份数据
		kpiMapper.deleteMonthDX(KPIMonth);

		// 查出所有人员查询当月人员类型
		List<EmployeeType> employees = kpiMapper.getEmployeeInfoType(KPIMonth);

		for (EmployeeType employee : employees) {
			//取出类型
			String type = employee.getType();
			// 分类型算底薪
			if ("直销".equals(type)) {
				new ZX_DXCount().dxCount(KPIMonth, employee,kpiMapper,uploadTablesStatusMapper);
			}
			else if("一体化".equals(type)){
				new YTH_DXCount().dxCount(KPIMonth, employee,kpiMapper,uploadTablesStatusMapper);
			}
			else if("销装维".equals(type)){
				new XZW_DXCount().dxCount(KPIMonth, employee,kpiMapper,uploadTablesStatusMapper);
			}
			else if("集客经理".equals(type)){
				new JK_DXCount().dxCount(KPIMonth, employee,kpiMapper,uploadTablesStatusMapper);
			}
			else if("集客专员".equals(type)){
				new JK_DXCount().dxCount(KPIMonth, employee,kpiMapper,uploadTablesStatusMapper);
			}
		}

		return "redirect:/pages/kpi_dx.html?KPIMonth=" + KPIMonth;
	}
	
	/**
	 * 提成计算
	 * @param KPIMonth
	 * @return
	 */
	@RequestMapping(value="/pages/kpi/kpi_tc" , method=RequestMethod.GET)
	public String kpi_tc(String KPIMonth){
		//先删除计算月份数据
		kpiMapper.deleteMonthTC(KPIMonth);
		//查询当月人员类型
		List<EmployeeType> employees = kpiMapper.getEmployeeInfoType(KPIMonth);
		
		for(EmployeeType employee : employees){
			/*String type = employee.getType();
			// 分类型算底薪
			if ("直销".equals(type)) {
				new ZX_TCCount().tcCount(KPIMonth, employee,kpiMapper,uploadTablesStatusMapper);
			}
			else if("一体化".equals(type)){
				new YTH_DXCount().dxCount(KPIMonth, employee,kpiMapper,uploadTablesStatusMapper);
			}
			else if("销装维".equals(type)){
				new XZW_DXCount().dxCount(KPIMonth, employee,kpiMapper,uploadTablesStatusMapper);
			}
			else if("集客经理".equals(type)){
				new JK_DXCount().dxCount(KPIMonth, employee,kpiMapper,uploadTablesStatusMapper);
			}
			else if("集客专员".equals(type)){
				new JK_DXCount().dxCount(KPIMonth, employee,kpiMapper,uploadTablesStatusMapper);
			}*/
			//提成计算都一样
			new ZX_TCCount().tcCount(KPIMonth, employee,kpiMapper,uploadTablesStatusMapper);
		}
	    
	    return "redirect:/pages/kpi_tc.html?KPIMonth=" + KPIMonth;
	}
	

	
	/**
	 * 工资计算
	 * @param KPIMonth
	 * @return
	 */
	@RequestMapping(value="/pages/kpi/kpi_gz" , method=RequestMethod.GET)
	public String kpi_gz(String KPIMonth){
		//先删除计算月份工资数据
		kpiMapper.deleteMonthGZ(KPIMonth);
		
		//查询当月人员类型
		List<EmployeeType> employees = kpiMapper.getEmployeeInfoType(KPIMonth);
		
		for(EmployeeType employee : employees){
			new ZX_GZCount().tcCount(KPIMonth, employee,kpiMapper,uploadTablesStatusMapper);
		}
		
		return "redirect:/pages/kpi_gz.html?KPIMonth=" + KPIMonth;
	}

}
