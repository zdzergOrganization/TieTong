package com.tietong.web.util;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {
	 public Workbook read(String filePath){  
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
 
 public String getValue(Cell cell) {
     if (cell.getCellType() == cell.CELL_TYPE_BOOLEAN) {
         return subZeroAndDot(String.valueOf(cell.getBooleanCellValue()));
     } else if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
    	 if(DateUtil.isCellDateFormatted(cell)){
    		 	return new DataFormatter().formatRawCellContents(cell.getNumericCellValue(), 0, "yyyy-mm-dd");// 格式化日期
    		 }else{
    			 return subZeroAndDot(String.valueOf(cell.getNumericCellValue()));
    		 }
         
     } else if (cell.getCellType() == cell.CELL_TYPE_STRING) {
         return  cell.getStringCellValue();
     }else if (cell.getCellType() == cell.CELL_TYPE_FORMULA) {         
         try{
        	 return  subZeroAndDot(String.valueOf(cell.getNumericCellValue()));
         }
         catch(Exception e1){
        	 try{
        		 return  cell.getStringCellValue();
        	 }
        	 catch(Exception e2){
        		 return null;
        	 }
        	 
         }
         //return  subZeroAndDot(String.valueOf(df.format(cell.getNumericCellValue())));
     }
     else {
    	 return null;
     }
 }
 
 /** 
  * 使用java正则表达式去掉多余的.与0 
  * @param s 
  * @return  
  */  
 public static String subZeroAndDot(String s){  
     if(s.indexOf(".") > 0){  
         s = s.replaceAll("0+?$", "");//去掉多余的0  
         s = s.replaceAll("[.]$", "");//如最后一位是.则去掉  
     }  
     return s;  
 }  
	 
}
