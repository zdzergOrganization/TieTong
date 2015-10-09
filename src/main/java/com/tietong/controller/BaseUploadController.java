package com.tietong.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.tietong.dao.TieTongMapper;
import com.tietong.dao.UploadTablesStatusMapper;
import com.tietong.pojo.Employee;
import com.tietong.pojo.BZ;
import com.tietong.web.util.ReadExcel;

@Controller
@RequestMapping("/base/upload")
public class BaseUploadController {

	@Autowired
	private UploadTablesStatusMapper uploadTablesStatusMapper;
	
	@RequestMapping(value="/batchAddCommit" , method=RequestMethod.GET)
	public String baseBatchAddCommit(HttpServletRequest request){
		Map<String,?> map = RequestContextUtils.getInputFlashMap(request);   
		//读取文件路径
		String filePath = (String) map.get("filePath");
		String uploadMonth = (String) map.get("uploadMonth");
		String tableName = (String) map.get("tableName");
		//先删除数据
		uploadTablesStatusMapper.delete(tableName, uploadMonth);
		
		// 读取批量导入的信息
		ReadExcel readExcel = new ReadExcel();
		Workbook wb = readExcel.read(filePath);
		Sheet sheet = wb.getSheetAt(0); // 获得第一个表单
		
		//判断上传什么表
		switch (tableName) {
		case "bz":
			uploadBZ(readExcel,sheet,uploadMonth);
			break;

		default:
			break;
		}
		
		
        
        return "redirect:/pages/upload.html";
        
	}
	
	public void uploadBZ(ReadExcel readExcel,Sheet sheet,String uploadMonth) {

		BZ bz = new BZ();
		// 去掉表头，从第一行取数据
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if(row != null){
				bz.setUpload_month(uploadMonth);
				if(row.getCell(0) != null){bz.setA(readExcel.getValue(row.getCell(0)));  }
				if(row.getCell(1) != null){bz.setB(readExcel.getValue(row.getCell(1)));  }
				if(row.getCell(2) != null){bz.setC(readExcel.getValue(row.getCell(2)));  }
				if(row.getCell(3) != null){bz.setD(readExcel.getValue(row.getCell(3)));  }
				if(row.getCell(4) != null){bz.setE(readExcel.getValue(row.getCell(4)));  }
				if(row.getCell(5) != null){bz.setF(readExcel.getValue(row.getCell(5)));  }
				if(row.getCell(6) != null){bz.setG(readExcel.getValue(row.getCell(6)));  }
				if(row.getCell(7) != null){bz.setH(readExcel.getValue(row.getCell(7)));  }
				if(row.getCell(8) != null){bz.setI(readExcel.getValue(row.getCell(8)));  }
				if(row.getCell(9) != null){bz.setJ(readExcel.getValue(row.getCell(9)));  }
				if(row.getCell(10) != null){bz.setK(readExcel.getValue(row.getCell(10)));}
				if(row.getCell(11) != null){bz.setL(readExcel.getValue(row.getCell(11)));}
			}
			

			// 插入数据库
			uploadTablesStatusMapper.insertBZ(bz);
		}
	}

}
