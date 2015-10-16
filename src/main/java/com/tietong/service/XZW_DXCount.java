package com.tietong.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tietong.dao.KPIMapper;
import com.tietong.dao.UploadTablesStatusMapper;
import com.tietong.pojo.AD_XZ_XF;
import com.tietong.pojo.BZ;
import com.tietong.pojo.EmployeeType;
import com.tietong.pojo.JT_KH;
import com.tietong.pojo.KPI_DX;
import com.tietong.pojo.Y_GH_TD;
import com.tietong.pojo.ZW_YTH_DF;
import com.tietong.web.util.StringNullToInt;

public class XZW_DXCount {
	@Autowired
	private KPIMapper kpiMapper;
	@Autowired
	private UploadTablesStatusMapper uploadTablesStatusMapper;
	
	/**
	 * 底薪计算
	 * @param KPIMonth
	 * @param employee
	 * @param uploadTablesStatusMapper 
	 * @param kpiMapper 
	 * @return
	 */
	public void dxCount(String KPIMonth, EmployeeType employee, KPIMapper kpiMapper, UploadTablesStatusMapper uploadTablesStatusMapper){

		//初始化底薪对象
		KPI_DX kpi_dx = new KPI_DX();
		
		String employeeName = employee.getEmployeeName();
		String employeeType = employee.getEmployeeType();
		
		//根据类型查询基本补贴
		BZ bz = uploadTablesStatusMapper.getBZ(KPIMonth,employeeType);
		if(bz == null){return;}
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
			
			kpi_dx.setL(Math.round(StringNullToInt.transToDouble(bz.getC()) * StringNullToInt.transToDouble(employee.getEmployeeTypeRel())) + "");//标准底薪
			kpi_dx.setM(Math.round(StringNullToInt.transToDouble(bz.getD()) * StringNullToInt.transToDouble(employee.getEmployeeTypeRel())) + "");//住房
			kpi_dx.setN(Math.round(StringNullToInt.transToDouble(bz.getE()) * StringNullToInt.transToDouble(employee.getEmployeeTypeRel())) + "");//通信
			kpi_dx.setO(Math.round(StringNullToInt.transToDouble(bz.getF()) * StringNullToInt.transToDouble(employee.getEmployeeTypeRel())) + "");//交通
		}
		else{
			kpi_dx.setL(bz.getC());//标准底薪
			kpi_dx.setM(bz.getD());//住房
			kpi_dx.setN(bz.getE());//通信
			kpi_dx.setO(bz.getF());//交通
		}
		
		//计算固话奖励考核
		
		//P当月固话装机=固话完成任务+集团客户
		
		//1. 集团客户 U
		int jtkh = 0;
		List<JT_KH> jt_khs = uploadTablesStatusMapper.getJT_KH(KPIMonth,employeeName);
		for(JT_KH jt_kh : jt_khs){
			if(jt_kh.getU() != null)
			{jtkh = jtkh + StringNullToInt.trans(jt_kh.getU());}
		}
		kpi_dx.setU(jtkh+"");//插入集团客户
		
		//2.1 固话完成任务
		List<Y_GH_TD> y_gh_tds = uploadTablesStatusMapper.getY_GH_TD(KPIMonth,employeeName);
		int gh_wc_rw = 0;//固话完成任务-暂时为线数
		for(Y_GH_TD y_gh_td : y_gh_tds){
			if(y_gh_td.getAu() != null)
			{gh_wc_rw = gh_wc_rw + StringNullToInt.trans(y_gh_td.getAu());}
		}
		
		//2.2 汇总 当月固话装机 P
		int dy_gh_zj = gh_wc_rw + jtkh;//当月装机数
		kpi_dx.setP(dy_gh_zj+"");//插入当月装机数
		
		
		//3. 固话总任务 Q
		int gh_zrw = 0;
		if(kpi_dx.getJ() == null){
			gh_zrw = StringNullToInt.trans(bz.getG());
		}
		else{
			gh_zrw = (int) Math.round(StringNullToInt.trans(bz.getG()) * StringNullToInt.transToDouble(kpi_dx.getJ()));
		}
		
		kpi_dx.setQ(gh_zrw+"");//插入固话总任务
		
		//4. 固话需考核线数 R
		int gh_xkh_xs = dy_gh_zj - gh_zrw;
		kpi_dx.setR(gh_xkh_xs+"");//插入固话需考核线数
		
		//5. 固话考核奖励 S
		int gh_kh_jl = 0;
		if(kpi_dx.getJ() != null){
			gh_kh_jl = gh_xkh_xs * 100;
		}
		else{
			gh_kh_jl = gh_xkh_xs * 50;
		}
		
		if(gh_kh_jl < -500){
			gh_kh_jl=0;
		}
		
		gh_kh_jl=0;//销装维无固话奖励
		kpi_dx.setS(gh_kh_jl + "");//插入 固话奖励考核
		
		//ADSL奖励考核
		
		//6. AD装机(不包含固定IP新装线数) V
		List<AD_XZ_XF> ad_xz_xfs = uploadTablesStatusMapper.getAD_XZ_XF(KPIMonth,employeeName);
		int ad_zj = 0;//AD装机
		for(AD_XZ_XF ad_xz_xf : ad_xz_xfs){
			ad_zj = ad_zj + StringNullToInt.trans(ad_xz_xf.getX());
		}
		kpi_dx.setV(ad_zj + "");//插入 AD装机

		//7. AD新装任务(线数) W
		int ads_rws = StringNullToInt.trans(bz.getK());
		kpi_dx.setW(ads_rws + "");//插入 AD新装任务
		
		//8. ADSL奖励考核 X
		int adsl_kh_jl = 0;
		if(kpi_dx.getJ() == null){
			adsl_kh_jl = (ad_zj - ads_rws) * 100;
		}
		else{
			adsl_kh_jl = (ad_zj - ads_rws) * 80;
		}
		//只惩罚不奖励
		if(adsl_kh_jl >0 ){
			adsl_kh_jl = 0;
		}
		kpi_dx.setX(adsl_kh_jl + "");//插入ADSL奖励考核
		
		//9. 考核汇总 Z
		int kh_hz = 0;
		if(employeeType.equals("Y") && (gh_kh_jl+adsl_kh_jl) < -1200){
			kh_hz = -1200;
		}
		else if(!employeeType.equals("Y") && (gh_kh_jl+adsl_kh_jl) < -400){
			kh_hz = -400;
		}
		else{
			kh_hz = gh_kh_jl+adsl_kh_jl;
		}
		kpi_dx.setZ(kh_hz + "");//插入考核汇总
		
		//10. 宽带新增少于9线（销装维少于13线）扣除当月业绩提成
		String kj = "0";
		if("Y".equals(employeeType) && ad_zj < 9){
			kj = "1";
		}
		kpi_dx.setAc(kj);//插入是否扣除当月业绩提成
		
		//11. 实发底薪 AA
		int sf_dx = 0;
		if("1".equals(kj)){
			sf_dx = StringNullToInt.trans(kpi_dx.getM());//不达标只发住房
		}
		else if((StringNullToInt.trans(kpi_dx.getL()) + StringNullToInt.trans(kpi_dx.getN()) + StringNullToInt.trans(kpi_dx.getO()) + StringNullToInt.trans(kpi_dx.getZ())) < 0){
			sf_dx = StringNullToInt.trans(kpi_dx.getM());//扣的负了，不会总的都扣钱，只发住房
		}
		else{
			sf_dx = (StringNullToInt.trans(kpi_dx.getL()) + StringNullToInt.trans(kpi_dx.getM()) + StringNullToInt.trans(kpi_dx.getN()) + StringNullToInt.trans(kpi_dx.getO()) + StringNullToInt.trans(kpi_dx.getZ()));
		}
		kpi_dx.setAa(sf_dx + "");// 插入实发底薪
		
		//12. 插入数据库
		kpiMapper.insertKPI_DX(kpi_dx);
	
}
	
}
