package com.tietong.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.tietong.dao.TieTongMapper;
import com.tietong.pojo.Employee;
import com.tietong.web.conf.StandardJsonResult;
import com.tietong.web.util.ReadExcel;

@Controller
@RequestMapping("/rest")
public class RestController {
	@Autowired
	private TieTongMapper tieTongMapper;
	
	//private Employee employee = new Employee();
	
	@RequestMapping(value="/employee/batchAddCommit" , method=RequestMethod.GET)
	public String batchAddCommit(HttpServletRequest request){
		Map<String,?> map = RequestContextUtils.getInputFlashMap(request);   
		//读取文件路径
		String filePath = (String) map.get("filePath");
		//读取批量导入的员工信息
		ReadExcel readExcel = new ReadExcel();
		Workbook wb  = readExcel.read(filePath);
        Sheet sheet = wb.getSheetAt(0);     //获得第一个表单  

    	Employee employee = new Employee();
        //去掉表头，从第一行取数据
        for(int i=1;i<=sheet.getLastRowNum();i++){
        	//Employee employee = new Employee();
            Row row = sheet.getRow(i);
            String s = readExcel.getValue(row.getCell(0));
            employee.setEmployeeName(s);
            employee.setType(readExcel.getValue(row.getCell(1)));
            employee.setRegionPQ(readExcel.getValue(row.getCell(2)));
            employee.setRegionQ(readExcel.getValue(row.getCell(3)));
            employee.setRegionWG(readExcel.getValue(row.getCell(4)));
            employee.setEntryDate(readExcel.getValue(row.getCell(5)));
            employee.setQuitDate(readExcel.getValue(row.getCell(6)));
            
            //先不输入计算月份
            //employee.setMonth_end_date(readExcel.getValue(row.getCell(7)));
            
            //插入数据库
            tieTongMapper.insertEmployeeInfo(employee);
        }
        
        return "redirect:/pages/employee.html";
        
	}
	
	@RequestMapping(value="/employee/getAllEmployeeInfo" , method=RequestMethod.GET)
	public @ResponseBody StandardJsonResult getAllEmployeeInfo(){
		List<Employee> employees = tieTongMapper.getAllEmployeeInfo();
		return new StandardJsonResult(employees);
	}
	
	@RequestMapping(value="/employee/employeeDel" , method=RequestMethod.GET)
	public @ResponseBody StandardJsonResult employeeDel(@RequestParam(value = "employeeDelId") int id){
		tieTongMapper.employeeDel(id);
		return new StandardJsonResult("succ");
	}

}
