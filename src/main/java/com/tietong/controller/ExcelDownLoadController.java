package com.tietong.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tietong.dao.KPIMapper;
import com.tietong.pojo.KPI_GZ;
import com.tietong.web.util.CreateSimpleExcelToFile;
import com.tietong.web.util.FileDownloaderService;

@Controller
@RequestMapping("/excel/download")
public class ExcelDownLoadController {
	@Autowired
	private KPIMapper kpiMapper;
	
	@RequestMapping(value = "/gz", method = RequestMethod.GET)
	public void downLoadGZ(String KPIMonth,HttpServletRequest request,HttpServletResponse response){
		
		//查询数据
		List<KPI_GZ> kpi_gzs = kpiMapper.getKPI_GZ(KPIMonth);
		
		//封装表头
		List<String> list_row1 = new ArrayList<String>();
		list_row1.add(0, "考核月份");
		list_row1.add(1, "片区");
		list_row1.add(2, "区");
		list_row1.add(3, "人员姓名");
		list_row1.add(4, "类型");
		list_row1.add(5, "入职时间");
		list_row1.add(6, "所属网格");
		list_row1.add(7, "实发底薪");
		list_row1.add(8, "集客人员绩效工");
		list_row1.add(9, "个人提成");
		list_row1.add(10, "单项考核");
		list_row1.add(11, "预付卡提成");
		list_row1.add(12, "新业务提成");
		list_row1.add(13, "网格得分考核");
		list_row1.add(14, "总工资");
		
		//表头
		List<ArrayList<String>> list_all = new ArrayList();
		list_all.add(0, (ArrayList<String>) list_row1);
		
		//循环装入数据
		for(KPI_GZ kpi_gz : kpi_gzs ){
			List<String> list_row = new ArrayList<String>();
			list_row.add(0, kpi_gz.getKpiDate());
			list_row.add(1, kpi_gz.getA());
			list_row.add(2, kpi_gz.getB());
			list_row.add(3, kpi_gz.getC());
			list_row.add(4, kpi_gz.getD());
			list_row.add(5, kpi_gz.getE());
			list_row.add(6, kpi_gz.getF());
			list_row.add(7, kpi_gz.getG());
			list_row.add(8, kpi_gz.getH());
			list_row.add(9, kpi_gz.getI());
			list_row.add(10, kpi_gz.getJ());
			list_row.add(11, kpi_gz.getK());
			list_row.add(12, kpi_gz.getL());
			list_row.add(13, kpi_gz.getM());
			list_row.add(14, kpi_gz.getP());
			
			list_all.add((ArrayList<String>) list_row);
		}
		String filePath = request.getSession().getServletContext().getRealPath("") + "\\download\\" + KPIMonth + "-gongzi.xlsx";
		
		try {
			CreateSimpleExcelToFile.exportToFile(list_all, filePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		
		FileDownloaderService fileDownloaderService = new FileDownloaderService();
		fileDownloaderService.download(filePath, request, response);
		
		//return "redirect:/pages/kpi_gz.html?KPIMonth=" + KPIMonth;
		
	}
	
}
