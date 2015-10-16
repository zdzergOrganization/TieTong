package com.tietong.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.tietong.dao.KPIMapper;
import com.tietong.dao.UploadTablesStatusMapper;
import com.tietong.pojo.EmployeeType;
import com.tietong.pojo.GZXRY_DYWGDF;
import com.tietong.pojo.JK_WCL;
import com.tietong.pojo.KPI_DX;
import com.tietong.pojo.KPI_GZ;
import com.tietong.pojo.KPI_TC;
import com.tietong.pojo.XYY_TC_HZB;
import com.tietong.pojo.YFF_K;
import com.tietong.web.util.StringNullToInt;

public class ZX_GZCount {
	/*@Autowired
	private KPIMapper kpiMapper;
	@Autowired
	private UploadTablesStatusMapper uploadTablesStatusMapper;*/
	
	/**
	 * 工资计算
	 * @param KPIMonth
	 * @param employee
	 * @return
	 */
	public void tcCount(String KPIMonth, EmployeeType employee,KPIMapper kpiMapper,UploadTablesStatusMapper uploadTablesStatusMapper){
		
		//查出人员的工资
		KPI_GZ kpi_gz = new KPI_GZ();
		
		//取出人员姓名
		String employeeName = employee.getEmployeeName();
		
		//人员基本属性
		kpi_gz.setKpiDate(KPIMonth);
		kpi_gz.setA(employee.getRegionPQ());
		kpi_gz.setB(employee.getRegionQ());
		kpi_gz.setC(employeeName);
		kpi_gz.setD(employee.getType());
		kpi_gz.setE(employee.getEntryDate());
		kpi_gz.setF(employee.getRegionWG());
		
		//1. 实发底薪
		double sf_dx = 0;
		KPI_DX kpi_dx = kpiMapper.getKPI_DXName(KPIMonth, employeeName);
		if(StringUtils.isNotBlank(kpi_dx.getAa())){
			sf_dx = StringNullToInt.transToDouble(kpi_dx.getAa());
		}
		kpi_gz.setG(sf_dx + "");
		
		//2. 个人提成
		double gr_tc = 0;
		KPI_TC kpi_tc = kpiMapper.getKPI_TCName(KPIMonth, employeeName);
		if(StringUtils.isNotBlank(kpi_tc.getAi())){
			gr_tc = StringNullToInt.transToDouble(kpi_tc.getAi());
		}
		kpi_gz.setI(gr_tc + "");
		
		//3.4. 集客人员绩效工资+单项考核
		double jkry_jxgz = 0;
		double dx_ke = 0;

		List<JK_WCL> jk_wcls = uploadTablesStatusMapper.getJK_WCL(KPIMonth,employeeName);
		for(JK_WCL jk_wcl : jk_wcls){
			
			if (StringUtils.isNotBlank(kpi_dx.getG())) {
				jkry_jxgz = jkry_jxgz + StringNullToInt.transToDouble(jk_wcl.getG());
			}
			
			if (StringUtils.isNotBlank(kpi_dx.getI())) {
				dx_ke = dx_ke + StringNullToInt.transToDouble(jk_wcl.getI());
			}
		}

		kpi_gz.setH(jkry_jxgz + "");
		kpi_gz.setJ(dx_ke + "");
		
		//5. 预付卡提成
		double yyf_tc = 0;
		List<YFF_K> yyk_ks = uploadTablesStatusMapper.getYFF_K(KPIMonth,employeeName);
		for(YFF_K yyk_k : yyk_ks){
			if (StringUtils.isNotBlank(yyk_k.getJ())) {
				yyf_tc = yyf_tc + StringNullToInt.transToDouble(yyk_k.getJ());
			}
		}
		kpi_gz.setK(yyf_tc + "");
		
		//6. 新业务提成
		double xyw_tc = 0;
		List<XYY_TC_HZB> xyy_tc_hzbs = uploadTablesStatusMapper.getXYY_TC_HZB(KPIMonth,employeeName);
		for(XYY_TC_HZB xyy_tc_hzb : xyy_tc_hzbs){
			if (StringUtils.isNotBlank(xyy_tc_hzb.getJ())) {
				xyw_tc = xyw_tc + StringNullToInt.transToDouble(xyy_tc_hzb.getJ());
			}
		}
		kpi_gz.setK(xyw_tc + "");
		
		//7. 网格得分考核
		double wg_df_kh = 0;
		//网格得分/集客完成率
		double wg_df = 0;
		List<GZXRY_DYWGDF> gzxry_dywgdfs = uploadTablesStatusMapper.getGZXRY_DYWGDF(KPIMonth,employeeName);
		for(GZXRY_DYWGDF gzxry_dywgdf : gzxry_dywgdfs){
			if (StringUtils.isNotBlank(gzxry_dywgdf.getF())) {
				wg_df = StringNullToInt.transToDouble(gzxry_dywgdf.getF());
			}
		}
		
		if("直销".equals(employee.getType())){
			wg_df_kh = (sf_dx + gr_tc) * (wg_df-1);
		}
		else{
			wg_df_kh = 0;
		}
		kpi_gz.setM(wg_df_kh + "");
		
		//8. 总工资
		double zgz = 0;
		
		if((sf_dx + gr_tc + jkry_jxgz + dx_ke + yyf_tc + xyw_tc + wg_df_kh) > 0){
			zgz = sf_dx + gr_tc + jkry_jxgz + dx_ke + yyf_tc + xyw_tc + wg_df_kh;
		}
		else{
			zgz = 0;
		}
		kpi_gz.setP(zgz + "");
		
		//插入数据库
		kpiMapper.insertKPI_GZ(kpi_gz);
		
	}
	
}
