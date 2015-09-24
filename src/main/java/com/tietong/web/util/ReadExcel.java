package com.tietong.web.util;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
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
	 
 /*private String getValue(XSSFCell xssfRow) {
     if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
         return String.valueOf(xssfRow.getBooleanCellValue());
     } else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
         return String.valueOf(xssfRow.getNumericCellValue());
     } else {
         return String.valueOf(xssfRow.getStringCellValue());
     }
 }

 private String getValue(HSSFCell hssfCell) {
     if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
         return String.valueOf(hssfCell.getBooleanCellValue());
     } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
         return String.valueOf(hssfCell.getNumericCellValue());
     } else {
         return String.valueOf(hssfCell.getStringCellValue());
     }
 }*/
 
 public String getValue(Cell cell) {
     if (cell.getCellType() == cell.CELL_TYPE_BOOLEAN) {
         return String.valueOf(cell.getBooleanCellValue());
     } else if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
         return String.valueOf(cell.getNumericCellValue());
     } else {
         return cell.getStringCellValue();
     }
 }
	 
}
