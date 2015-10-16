package com.tietong.service;

import java.util.List;

import com.tietong.dao.KPIMapper;
import com.tietong.dao.UploadTablesStatusMapper;
import com.tietong.pojo.AD_XZ_XF;
import com.tietong.pojo.EmployeeType;
import com.tietong.pojo.GZXRY_DYWGDF;
import com.tietong.pojo.JTKH_KH;
import com.tietong.pojo.JTKH_ZB;
import com.tietong.pojo.KPI_DX;
import com.tietong.pojo.KPI_TC;
import com.tietong.pojo.ZXLN_ZYYN;
import com.tietong.web.util.StringNullToInt;

public class ZX_TCCount {
	/*@Autowired
	private KPIMapper kpiMapper;
	@Autowired
	private UploadTablesStatusMapper uploadTablesStatusMapper;*/
	
	/**
	 * 提成计算
	 * @param KPIMonth
	 * @param employee
	 * @return
	 */
	public void tcCount(String KPIMonth, EmployeeType employee,KPIMapper kpiMapper,UploadTablesStatusMapper uploadTablesStatusMapper){
		KPI_TC kpi_tc = new KPI_TC();//提成
		String employeeName = employee.getEmployeeName();
		
		//人员基本属性
		kpi_tc.setKpiDate(KPIMonth);
		kpi_tc.setB(employeeName);
		kpi_tc.setC(employee.getType());
		kpi_tc.setD(employee.getRegionWG());
		
		//1.2. 后付固话实收+预付固话实收
		double hf_gh_ss = 0;
		double yf_gh_ss = 0;
		List<ZXLN_ZYYN> zxln_zyyns = uploadTablesStatusMapper.getZXLN_ZYYN(KPIMonth,employeeName);
		for(ZXLN_ZYYN zxln_zyyn : zxln_zyyns){
			hf_gh_ss = hf_gh_ss + StringNullToInt.transToDouble(zxln_zyyn.getJ());
			yf_gh_ss = yf_gh_ss + StringNullToInt.transToDouble(zxln_zyyn.getI());
		}
		kpi_tc.setE(hf_gh_ss+"");//插入后付固话实收
		kpi_tc.setF(yf_gh_ss+"");//插入预付固话实收
		
		//3. 固话总实收 = 预付固话实收+后付固话实收
		double gh_tc = hf_gh_ss + yf_gh_ss;
		kpi_tc.setG(gh_tc+"");//插入固话总实收
		
		//4. 固话提成=（预付固话实收+后付固话实收）*0.08 
		gh_tc = Math.ceil((gh_tc*0.08)*100)/100;//两位小数
		kpi_tc.setH(gh_tc+"");//插入固话提成
		
		//5. 固话考核是否达标
		String gh_kh_db = null;
		KPI_DX kpi_dx = kpiMapper.getKPI_DXName(KPIMonth,employeeName);
		gh_kh_db = kpi_dx.getAb();
		kpi_tc.setI(gh_kh_db+"");//插入固话考核是否达标 1为不达标

		//6. 新装+续费总收入  新增收入
		int xz_xf_zsr = 0;
		int xz_sr = 0;
		List<AD_XZ_XF> ad_xz_xfs = uploadTablesStatusMapper.getAD_XZ_XF(KPIMonth,employeeName);
		for(AD_XZ_XF ad_xz_xf : ad_xz_xfs){
			xz_xf_zsr = xz_xf_zsr + StringNullToInt.trans(ad_xz_xf.getAf());
			xz_sr = xz_sr + StringNullToInt.trans(ad_xz_xf.getAg());
		}
		kpi_tc.setK(xz_xf_zsr+"");//插入新增收入
		kpi_tc.setL(xz_sr+"");//插入新增收入
		
		//7. 固话未完成任务数一半，当月新增AD提成扣减2%
		double gh_wwc_yb = 0;
		
		if("1".equals(gh_kh_db)){
			gh_wwc_yb = xz_sr * -0.02;
		}
		else{
			gh_wwc_yb = 0;
		}
		kpi_tc.setJ(gh_wwc_yb+"");//插入 固话未完成任务数一半，当月新增AD提成扣减2%
		
		
		//8. 普通宽带收入 M
		int pu_kd_sr = 0;
		//9. 价值宽带收入 N
		int jz_kd_sr = 0;
		//10. 新装+续费总线数（不含固定IP）O
		double xz_xf_zxs = 0;
		//11. 其中新装(含普通及价值） P
		double qz_xz = 0;
		for(AD_XZ_XF ad_xz_xf : ad_xz_xfs){
			pu_kd_sr = pu_kd_sr + StringNullToInt.trans(ad_xz_xf.getAd());
			jz_kd_sr = jz_kd_sr + StringNullToInt.trans(ad_xz_xf.getAe());
			xz_xf_zxs = xz_xf_zxs + StringNullToInt.transToDouble(ad_xz_xf.getW());
			qz_xz = qz_xz + StringNullToInt.transToDouble(ad_xz_xf.getX());
		}
		kpi_tc.setM(pu_kd_sr+"");//插入 普通宽带收入 M
		kpi_tc.setN(jz_kd_sr+"");//插入 价值宽带收入 N
		kpi_tc.setO(xz_xf_zxs+"");//插入 新装+续费总线数（不含固定IP）O
		kpi_tc.setP(qz_xz+"");//插入 其中新装(含普通及价值） P
		
		//12. 普通宽带提成点数
		//13. 价值宽带提成点数
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
		else if(qz_xz>=25 && qz_xz<40){
			pu_kd_tcds = 0.07;
			jz_kd_tcds = 0.10;
		}
		else{
			pu_kd_tcds = 0.06;
			jz_kd_tcds = 0.09;
		}
		
		//增加集客的判断
		if(employee.getType().startsWith("集客")){
			pu_kd_tcds = 0.07;
			jz_kd_tcds = 0.10;
		}
		kpi_tc.setS(pu_kd_tcds+"");//插入 普通宽带提成点数S
		kpi_tc.setT(jz_kd_tcds+"");//插入 价值宽带提成点数 T
		
		//14. 普通宽带提成
		double pu_kd_tc = pu_kd_sr * pu_kd_tcds;
		kpi_tc.setW(pu_kd_tc+"");//插入普通宽带提成
		
		//15. 价值宽带提成
		double jz_kd_tc = jz_kd_sr * jz_kd_tcds;
		kpi_tc.setX(jz_kd_tc+"");//插入价值宽带提成
		
		//16. 超出基本奖
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
		kpi_tc.setZ(cc_jb_j+"");//插入超出基本奖
		
		//17. 销装维超20线奖励
		int xzw_c_jl = 0;
		//18. 续费考核
		int xfkh = 0;
		//19. 固定IP提成
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
		
		//20.固定IP续费率考核
		int gd_id_xfl_kh = 0;
		List<GZXRY_DYWGDF> gzxry_dywgdfs = uploadTablesStatusMapper.getGZXRY_DYWGDF(KPIMonth,employeeName);
		
		for(GZXRY_DYWGDF gzxry_dywgdf : gzxry_dywgdfs){
			gd_id_xfl_kh = gd_id_xfl_kh + StringNullToInt.trans(gzxry_dywgdf.getH());
		}
		kpi_tc.setAe(gd_id_xfl_kh+"");
		
		//21. 集团客户考核
		int jt_kh_kh = 0;
		List<JTKH_KH> jtkh_khs = uploadTablesStatusMapper.getJTKH_KH(KPIMonth,employeeName);
		
		for(JTKH_KH jtkh_kh : jtkh_khs){
			jt_kh_kh = jt_kh_kh + StringNullToInt.trans(jtkh_kh.getH());
		}
		kpi_tc.setAf(jt_kh_kh+"");
		//22. 集团客户提成
		int jt_kh_tc = 0;
		List<JTKH_ZB> jtkh_zbs = uploadTablesStatusMapper.getJTKH_ZB(KPIMonth,employeeName);
		
		for(JTKH_ZB jtkh_zb : jtkh_zbs){
			jt_kh_tc = jt_kh_tc + StringNullToInt.trans(jtkh_zb.getE());
		}
		kpi_tc.setAg(jt_kh_tc+"");
		
		
		//23. 汇总 插入总提成
		double ztc = 0;
		ztc = gh_tc+gh_wwc_yb+pu_kd_tc+jz_kd_tc+cc_jb_j+xzw_c_jl+xfkh+gd_ip_tc+gd_id_xfl_kh+jt_kh_kh+jt_kh_tc;
		kpi_tc.setAi(ztc+"");
		
		
		//插入数据库
		kpiMapper.insertKPI_TC(kpi_tc);
	}
	
}
