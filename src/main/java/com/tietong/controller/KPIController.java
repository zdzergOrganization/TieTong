package com.tietong.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
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
import com.tietong.pojo.GZXRY_DYWGDF;
import com.tietong.pojo.JTKH_KH;
import com.tietong.pojo.JTKH_ZB;
import com.tietong.pojo.KPI_DX;
import com.tietong.pojo.KPI_TC;
import com.tietong.pojo.Y_GH_TD;
import com.tietong.pojo.ZXLN_ZYYN;
import com.tietong.web.conf.StandardJsonResult;
import com.tietong.web.util.StringNullToInt;

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
	 * 底薪计算
	 * @param KPIMonth
	 * @return
	 */
	@RequestMapping(value="/pages/kpi/kpi_dx" , method=RequestMethod.GET)
	public String kpi_dx(String KPIMonth){
		//先删除计算月份数据
		kpiMapper.deleteMonthDX(KPIMonth);
		
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
			kpi_dx.setKpiDate(KPIMonth);
			kpi_dx.setD(employeeName);
			kpi_dx.setE(employee.getType());
			kpi_dx.setF(employee.getEntryDate());
			kpi_dx.setK(employee.getQuitDate());
			kpi_dx.setI(employeeType);
			//入职时长
			kpi_dx.setJ(employee.getEmployeeTypeRel());
			
			//底薪固定
			if(StringUtils.isNotBlank(employee.getEmployeeTypeRel())){
				kpi_dx.setL((int)(StringNullToInt.transToDouble(bz.getC()) * StringNullToInt.transToDouble(employee.getEmployeeTypeRel())) + "");//标准底薪
				kpi_dx.setM((int)(StringNullToInt.transToDouble(bz.getD()) * StringNullToInt.transToDouble(employee.getEmployeeTypeRel())) + "");//住房
				kpi_dx.setN((int)(StringNullToInt.transToDouble(bz.getE()) * StringNullToInt.transToDouble(employee.getEmployeeTypeRel())) + "");//通信
				kpi_dx.setO((int)(StringNullToInt.transToDouble(bz.getF()) * StringNullToInt.transToDouble(employee.getEmployeeTypeRel())) + "");//交通
			}
			else{
				kpi_dx.setL(bz.getC());//标准底薪
				kpi_dx.setM(bz.getD());//住房
				kpi_dx.setN(bz.getE());//通信
				kpi_dx.setO(bz.getF());//交通
			}
			
			//1.计算固话奖励考核
			
			//1.1固话完成任务
			List<Y_GH_TD> y_gh_tds = uploadTablesStatusMapper.getY_GH_TD(KPIMonth,employeeName);
			int gh_wc_rw = 0;//固话完成任务-暂时为线数
			for(Y_GH_TD y_gh_td : y_gh_tds){
				if(y_gh_td.getAu() != null)
				{gh_wc_rw = gh_wc_rw + StringNullToInt.trans(y_gh_td.getAu());}
			}
			
			//1.2集团客户
			int jtkh = 0;
			
			//1.3汇总
			int dy_gh_zj = gh_wc_rw + jtkh;//当月装机数
			
			//固话总任务
			int gh_zrw = 0;
			if(kpi_dx.getJ() == null){
				gh_zrw = StringNullToInt.trans(bz.getG());
			}
			else{
				gh_zrw = StringNullToInt.trans(bz.getG()) * StringNullToInt.trans(kpi_dx.getJ());
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
			int ads_rws = StringNullToInt.trans(bz.getK());
			//2.2 AD装机
			List<AD_XZ_XF> ad_xz_xfs = uploadTablesStatusMapper.getAD_XZ_XF(KPIMonth,employeeName);
			int ad_zj = 0;//AD装机
			for(AD_XZ_XF ad_xz_xf : ad_xz_xfs){
				ad_zj = ad_zj + StringNullToInt.trans(ad_xz_xf.getX());
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
				sf_dx = StringNullToInt.trans(bz.getD());
			}
			else if((StringNullToInt.trans(bz.getC()) + StringNullToInt.trans(bz.getE()) + StringNullToInt.trans(bz.getF()) + kh_hz) < 0){
				sf_dx = StringNullToInt.trans(bz.getD());
			}
			else{
				sf_dx = (StringNullToInt.trans(bz.getC()) + StringNullToInt.trans(bz.getE()) + StringNullToInt.trans(bz.getF()) + kh_hz);
			}
			
			//4.2 插入实发底薪
			kpi_dx.setAa(sf_dx + "");
			
			//5.插入最终底薪数据
			kpiMapper.insertKPI_DX(kpi_dx);
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
		
		KPI_TC kpi_tc = new KPI_TC();//提成
		
		for(EmployeeType employee : employees){
			String employeeName = employee.getEmployeeName();
			
			//人员基本属性
			kpi_tc.setKpiDate(KPIMonth);
			kpi_tc.setB(employeeName);
			kpi_tc.setC(employee.getType());
			kpi_tc.setD(employee.getRegionWG());
			
			//1.固话提成
			double gh_tc = 0;
			//1.1 固话提成=（预付固话实收+后付固话实收）*0.08 
			//预付固话实收+后付固话实收
			List<ZXLN_ZYYN> zxln_zyyns = uploadTablesStatusMapper.getZXLN_ZYYN(KPIMonth,employeeName);
			for(ZXLN_ZYYN zxln_zyyn : zxln_zyyns){
				gh_tc = gh_tc + StringNullToInt.trans(zxln_zyyn.getI()) + StringNullToInt.trans(zxln_zyyn.getJ());
			}

			gh_tc = Math.ceil((gh_tc*0.8)*100)/100;//两位小数
			//插入
			kpi_tc.setH(gh_tc+"");
			
			//2. 固话未完成任务数一半，当月新增AD提成扣减2%
			double gh_wwc_yb = 0;
			//新增收入
			int xz_sr = 0;
			List<AD_XZ_XF> ad_xz_xfs = uploadTablesStatusMapper.getAD_XZ_XF(KPIMonth,employeeName);
			for(AD_XZ_XF ad_xz_xf : ad_xz_xfs){
				xz_sr = xz_sr + StringNullToInt.trans(ad_xz_xf.getN());
			}
			
			KPI_DX kpi_dx = kpiMapper.getKPI_DXName(KPIMonth,employeeName);
			if("Y".equals(kpi_dx.getI()) && StringNullToInt.trans(kpi_dx.getP()) < StringNullToInt.trans(kpi_dx.getQ())/2){
				gh_wwc_yb = xz_sr * -0.02;
			}
			else{
				gh_wwc_yb = 0;
			}
			//插入
			kpi_tc.setJ(gh_wwc_yb+"");
			
			//3. 普通宽带提成
			double pu_kd_tc = 0;
			
			int pu_kd_sr = 0;//普通宽带收入
			int jz_kd_sr = 0;//价值宽带收入
			double xz_xf_zxs = 0;//新装+续费总线数（不含固定IP）
			double qz_xz = 0;//其中新装(含普通及价值）
			for(AD_XZ_XF ad_xz_xf : ad_xz_xfs){
				pu_kd_sr = pu_kd_sr + StringNullToInt.trans(ad_xz_xf.getAd());
				jz_kd_sr = jz_kd_sr + StringNullToInt.trans(ad_xz_xf.getAe());
				xz_xf_zxs = xz_xf_zxs + StringNullToInt.transToDouble(ad_xz_xf.getW());
				qz_xz = qz_xz + StringNullToInt.transToDouble(ad_xz_xf.getX());
			}
			//普通宽带提成点数
			//价值宽带提成点数
			double pu_kd_tcds = 0;
			double jz_kd_tcds = 0;
			if(xz_xf_zxs>70 && qz_xz>=40){
				pu_kd_tcds = 0.08;
				jz_kd_tcds = 0.12;
			}
			else if(xz_xf_zxs>70 && qz_xz<40){
				pu_kd_tcds = 0.07;
				jz_kd_tcds = 0.10;
			}
			else if(xz_xf_zxs>=25 && qz_xz<40){
				pu_kd_tcds = 0.07;
				jz_kd_tcds = 0.10;
			}
			else{
				pu_kd_tcds = 0.06;
				jz_kd_tcds = 0.09;
			}
			
			//普通宽带提成
			pu_kd_tc = pu_kd_sr * pu_kd_tcds;
			//插入
			kpi_tc.setW(pu_kd_tc+"");
			
			//4. 价值宽带提成
			double jz_kd_tc = 0;
			jz_kd_tc = jz_kd_sr * jz_kd_tcds;
			//插入
			kpi_tc.setX(jz_kd_tc+"");
			
			//5. 超出基本奖
			double cc_jb_j = 0;
			if(xz_xf_zxs>80){
				cc_jb_j = (xz_xf_zxs-40)*15;
			}
			else if(xz_xf_zxs>70 && xz_xf_zxs<=80){
				cc_jb_j = (xz_xf_zxs-40)*12;
			}
			else if(xz_xf_zxs>60 && xz_xf_zxs<=70){
				cc_jb_j = (xz_xf_zxs-40)*10;
			}
			else if(xz_xf_zxs>40 && xz_xf_zxs<=60){
				cc_jb_j = (xz_xf_zxs-40)*6;
			}
			else{
				cc_jb_j = 0;
			}
			//插入
			kpi_tc.setZ(cc_jb_j+"");
			
			//6. 销装维超20线奖励
			int xzw_c_jl = 0;
			//7. 续费考核
			int xfkh = 0;
			//8. 固定IP提成
			int gd_ip_tc = 0;
			
			for(AD_XZ_XF ad_xz_xf : ad_xz_xfs){
				if("销装维".equals(ad_xz_xf.getAo())){
					xzw_c_jl = xzw_c_jl + StringNullToInt.trans(ad_xz_xf.getAm());
				}
				xfkh = xfkh + StringNullToInt.trans(ad_xz_xf.getAu());
				gd_ip_tc = gd_ip_tc + StringNullToInt.trans(ad_xz_xf.getAn());
			}
			//插入
			kpi_tc.setAb(xzw_c_jl+"");
			kpi_tc.setAc(xfkh+"");
			kpi_tc.setAd(gd_ip_tc+"");
			
			//9.固定IP续费率考核
			int gd_id_xfl_kh = 0;
			List<GZXRY_DYWGDF> gzxry_dywgdfs = uploadTablesStatusMapper.getGZXRY_DYWGDF(KPIMonth,employeeName);
			
			for(GZXRY_DYWGDF gzxry_dywgdf : gzxry_dywgdfs){
				gd_id_xfl_kh = gd_id_xfl_kh + StringNullToInt.trans(gzxry_dywgdf.getH());
			}
			kpi_tc.setAe(gd_id_xfl_kh+"");
			
			//10. 集团客户考核
			int jt_kh_kh = 0;
			List<JTKH_KH> jtkh_khs = uploadTablesStatusMapper.getJTKH_KH(KPIMonth,employeeName);
			
			for(JTKH_KH jtkh_kh : jtkh_khs){
				jt_kh_kh = jt_kh_kh + StringNullToInt.trans(jtkh_kh.getH());
			}
			kpi_tc.setAf(jt_kh_kh+"");
			//11. 集团客户提成
			int jt_kh_tc = 0;
			List<JTKH_ZB> jtkh_zbs = uploadTablesStatusMapper.getJTKH_ZB(KPIMonth,employeeName);
			
			for(JTKH_ZB jtkh_zb : jtkh_zbs){
				jt_kh_tc = jt_kh_tc + StringNullToInt.trans(jtkh_zb.getE());
			}
			kpi_tc.setAg(jt_kh_tc+"");
			
			
			//汇总 插入总提成
			double ztc = 0;
			ztc = gh_tc+gh_wwc_yb+pu_kd_tc+jz_kd_tc+cc_jb_j+xzw_c_jl+xfkh+gd_ip_tc+gd_id_xfl_kh+jt_kh_kh+jt_kh_tc;
			kpi_tc.setAi(ztc+"");
			
			
			//插入数据库
			kpiMapper.insertKPI_TC(kpi_tc);
			
		}
	    
	    return "redirect:/pages/kpi_tc.html?KPIMonth=" + KPIMonth;
	}

}
