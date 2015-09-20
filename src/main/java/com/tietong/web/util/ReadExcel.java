package com.tietong.web.util;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {
	 public static Workbook read(String filePath){  
	        boolean isE2007 = false;    //判断是否是excel2007格式  
	        if(filePath.endsWith("xlsx"))  
	            isE2007 = true;  
	        try {  
	            InputStream input = new FileInputStream(filePath);  //建立输入流  
	            Workbook wb  = null;  
	            //根据文件格式(2003或者2007)来初始化  
	            if(isE2007)  
	                wb = new XSSFWorkbook(input);  
	            else  
	                wb = new HSSFWorkbook(input); 
	            
	            return wb;
	            
	        } catch (IOException ex) {  
	            ex.printStackTrace();  
	        }
			return null;  
	    }  
	 
}
