package com.tietong.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tietong.dao.KPIMapper;
import com.tietong.dao.UploadTablesStatusMapper;
import com.tietong.pojo.AD_XZ_XF;
import com.tietong.pojo.BZ;
import com.tietong.pojo.EmployeeType;
import com.tietong.pojo.KPI_DX;
import com.tietong.pojo.Y_GH_TD;
import com.tietong.web.conf.StandardJsonResult;
import com.tietong.web.util.IntNullToString;

@Controller
//@RequestMapping("/kpi")
public class KPIController {
	@Autowired
	private KPIMapper kpiMapper;
	@Autowired
	private UploadTablesStatusMapper uploadTablesStatusMapper;
		
	@RequestMapping(value="/kpi/dateset" , method=RequestMethod.POST)
	public String dateSet(String KPIMonth){
		//先删除计算月份数据
		kpiMapper.deleteMonthEmployee(KPIMonth);
		//插入计算月份人员类型
		kpiMapper.insertEmployeeInfoType(KPIMonth);
        
        return "redirect:/pages/employee_type.html?KPIMonth=" + KPIMonth;
	}
	
	@RequestMapping(value="/kpi/employee/getAllEmployeeInfoType")
	public @ResponseBody StandardJsonResult getAllEmployeeInfoType(String KPIMonth){
		//查询当月人员类型
		List<EmployeeType> employees = kpiMapper.getEmployeeInfoType(KPIMonth);
	    
		return new StandardJsonResult(employees);
	}
	
	@RequestMapping(value="/pages/kpi/kpi_dx" , method=RequestMethod.GET)
	public String kpi_dx(String KPIMonth){
		//查询当月人员类型
		List<EmployeeType> employees = kpiMapper.getEmployeeInfoType(KPIMonth);
		
		//查出人员的底薪
		KPI_DX kpi_dx = new KPI_DX();
		for(EmployeeType employee : employees){
			String employeeName = employee.getEmployeeName();
			String employeeType = employee.getEmployeeType();
			//根据类型查询基本补贴
			BZ bz = uploadTablesStatusMapper.getBZ(KPIMonth,employeeType);
			if(bz == null){continue;}
			//插入底薪
			
			//人员基本属性
			kpi_dx.setKpi_date(KPIMonth);
			kpi_dx.setD(employeeName);
			kpi_dx.setE(employee.getType());
			kpi_dx.setF(employee.getEntryDate());
			kpi_dx.setK(employee.getQuitDate());
			kpi_dx.setI(employeeType);
			kpi_dx.setJ(null);//暂时不会算
			
			//底薪固定
			kpi_dx.setL(bz.getC());//标准底薪
			kpi_dx.setM(bz.getD());//住房
			kpi_dx.setN(bz.getE());//通信
			kpi_dx.setO(bz.getF());//交通
			
			//1.计算固话奖励考核
			
			//1.1固话完成任务
			List<Y_GH_TD> y_gh_tds = uploadTablesStatusMapper.getY_GH_TD(KPIMonth,employeeName);
			int gh_wc_rw = 0;//固话完成任务-暂时为线数
			for(Y_GH_TD y_gh_td : y_gh_tds){
				if(y_gh_td.getAu() != null)
				{gh_wc_rw = gh_wc_rw + IntNullToString.trans(y_gh_td.getAu());}
			}
			
			//1.2集团客户
			int jtkh = 0;
			
			//1.3汇总
			int dy_gh_zj = gh_wc_rw + jtkh;//当月装机数
			
			//固话总任务
			int gh_zrw = 0;
			if(kpi_dx.getJ() == null){
				gh_zrw = IntNullToString.trans(bz.getG());
			}
			else{
				gh_zrw = IntNullToString.trans(bz.getG()) * IntNullToString.trans(kpi_dx.getJ());
			}
			
			int gh_xkh_xs = dy_gh_zj - gh_zrw;//固话需考核线数
			
			//固话考核奖励
			int gh_kh_jl = 0;
			if(employeeType.equals('Y')){
				gh_kh_jl = gh_xkh_xs * 100;
			}
			else{
				gh_kh_jl = gh_xkh_xs * 50;
			}
			
			if(gh_kh_jl < -500){
				gh_kh_jl=0;
			}
			//1.4 插入 固话奖励考核
			kpi_dx.setS(gh_kh_jl + "");
			
			//2.ADSL奖励考核
			//2.1 AD新装任务(线数)
			int ads_rws = IntNullToString.trans(bz.getK());
			//2.2 AD装机
			List<AD_XZ_XF> ad_xz_xfs = uploadTablesStatusMapper.getAD_XZ_XF(KPIMonth,employeeName);
			int ad_zj = 0;//AD装机
			for(AD_XZ_XF ad_xz_xf : ad_xz_xfs){
				ad_zj = ad_zj + IntNullToString.trans(ad_xz_xf.getX());
			}
			//2.3汇总
			int adsl_kh_jl = 0;
			if(employeeType.equals('Y')){
				adsl_kh_jl = (ad_zj - ads_rws) * 100;
			}
			else{
				adsl_kh_jl = (ad_zj - ads_rws) * 80;
			}
			//只惩罚不奖励
			if(adsl_kh_jl >0 ){
				adsl_kh_jl = 0;
			}
			//2.4 插入ADSL奖励考核
			kpi_dx.setX(adsl_kh_jl + "");
			
			//3.考核汇总
			int kh_hz = 0;
			if(employeeType.equals('Y') && (gh_kh_jl+adsl_kh_jl) < -1200){
				kh_hz = -1200;
			}
			else if(!employeeType.equals('Y') && (gh_kh_jl+adsl_kh_jl) < -400){
				kh_hz = -400;
			}
			else{
				kh_hz = gh_kh_jl+adsl_kh_jl;
			}
			
			//4.实发底薪
			int sf_dx = 0;
			//4.1计算是否全部扣除，宽带是否少于9线
			if(employeeType.equals('Y') && ad_zj < 9){
				sf_dx = IntNullToString.trans(bz.getD());
			}
			else if((IntNullToString.trans(bz.getC()) + IntNullToString.trans(bz.getE()) + IntNullToString.trans(bz.getF()) + kh_hz) < 0){
				sf_dx = IntNullToString.trans(bz.getD());
			}
			else{
				sf_dx = (IntNullToString.trans(bz.getC()) + IntNullToString.trans(bz.getE()) + IntNullToString.trans(bz.getF()) + kh_hz);
			}
			
			//4.2 插入实发底薪
			kpi_dx.setAa(sf_dx + "");
			
			//5.插入最终底薪数据
			kpiMapper.insertKPI_DX(kpi_dx);
		}
	    
	    return "redirect:/pages/kpi_dx.html?KPIMonth=" + KPIMonth;
	}
	
	

}
