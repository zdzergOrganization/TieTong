package com.tietong.web.util;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CreateSimpleExcelToFile {

	public static void exportToFile(List list,String filePath) throws Exception {

		// 第一步，创建一个webbook，对应一个Excel文件
		XSSFWorkbook wb = new XSSFWorkbook();
		
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		XSSFSheet sheet = wb.createSheet("sheet1");
		
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		XSSFRow row = sheet.createRow((int) 0);
		
		// 第四步，创建单元格，并设置值表头 设置表头居中
		XSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		//第一行为表头
		List list1 = (List) list.get(0);
		
		for(int i = 0;i < list1.size();i++){
			XSSFCell cell = row.createCell((short) i);
			cell.setCellValue(String.valueOf(list1.get(i)));
			cell.setCellStyle(style);
		}

		// 第五步，写入实体数据
		for (int i = 1; i < list.size(); i++) {
			row = sheet.createRow((int) i);
			List list_tmp = (List) list.get(i);
			
			for(int i1= 0;i1 < list_tmp.size();i1++){
				// 第四步，创建单元格，并设置值
				try{
					String s = String.valueOf(list_tmp.get(i1));
					row.createCell((short) i1).setCellValue(s);
				}
				catch(Exception e){
					row.createCell((short) i1).setCellValue("");
				}
				
			}
			
		}
		
		// 第六步，将文件存到指定位置
		try {
			FileOutputStream fout = new FileOutputStream(filePath);
			wb.write(fout);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
