package com.tietong.controller;

import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tietong.dao.TieTongMapper;
import com.tietong.pojo.Employee;
import com.tietong.web.util.ReadExcel;

@Controller
@RequestMapping("/rest")
public class RestController {
	@Autowired
	private TieTongMapper tieTongMapper;
	
	@RequestMapping(value="/employee/batchAddCommit" , method=RequestMethod.GET)
	public @ResponseBody void batchAddCommit(String filePath){
		
		//读取批量导入的员工信息
		ReadExcel readExcel = new ReadExcel();
		Workbook wb  = readExcel.read(filePath);
        Sheet sheet = wb.getSheetAt(0);     //获得第一个表单  
        
        //去掉表头，从第一行取数据
        for(int i=1;i<sheet.getLastRowNum();i++){
        	Employee employee = null;
            Row row = sheet.getRow(i);
            employee.setEmployee_name(row.getCell(0).toString());
            employee.setType(row.getCell(1).toString());
            employee.setRegion_pq(row.getCell(2).toString());
            employee.setRegion_q(row.getCell(3).toString());
            employee.setRegion_wg(row.getCell(4).toString());
            employee.setEntry_date(row.getCell(5).toString());
            employee.setQuit_date(row.getCell(6).toString());
            
            //先不输入计算月份
            //employee.setMonth_end_date(row.getCell(7).toString());
            
            //插入数据库
            tieTongMapper.insertEmployeeInfo(employee);
        }
        
	}

}
