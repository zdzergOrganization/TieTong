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

import com.tietong.dao.UploadTablesStatusMapper;
import com.tietong.pojo.AD_XZ_XF;
import com.tietong.pojo.BZ;
import com.tietong.pojo.GZXRY_DYWGDF;
import com.tietong.pojo.JTKH_KH;
import com.tietong.pojo.JTKH_ZB;
import com.tietong.pojo.Y_GH_TD;
import com.tietong.pojo.ZXLN_ZYYN;
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
		case "y_gh_td":
			uploadY_GH_TD(readExcel,sheet,uploadMonth);
			break;
		case "ad_xz_xf":
			uploadAD_XZ_XF(readExcel,sheet,uploadMonth);
			break;
		case "zxln_zyyn":
			uploadZXLN_ZYYN(readExcel,sheet,uploadMonth);
			break;
		case "gzxry_dywgdf":
			uploadGZXRY_DYWGDF(readExcel,sheet,uploadMonth);
			break;
		case "jtkh_kh":
			uploadJTKH_KH(readExcel,sheet,uploadMonth);
			break;
		case "jtkh_zb":
			uploadJTKH_ZB(readExcel,sheet,uploadMonth);
			break;

		default:
			break;
		}
		
		
        
        return "redirect:/pages/upload.html" + "?uploadMonth=" + uploadMonth;
        
	}
	
	public void uploadBZ(ReadExcel readExcel,Sheet sheet,String uploadMonth) {

		BZ bz = new BZ();
		// 去掉表头，从第一行取数据
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if(row != null){
				bz.setUploadMonth(uploadMonth);
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
	
	public void uploadY_GH_TD(ReadExcel readExcel,Sheet sheet,String uploadMonth) {

		Y_GH_TD bz = new Y_GH_TD();
		// 去掉表头，从第一行取数据
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if(row != null){
				bz.setUploadMonth(uploadMonth);
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
				if(row.getCell(10) != null){bz.setK(readExcel.getValue(row.getCell(10)));  }
				if(row.getCell(11) != null){bz.setL(readExcel.getValue(row.getCell(11)));  }
				if(row.getCell(12) != null){bz.setM(readExcel.getValue(row.getCell(12)));  }
				if(row.getCell(13) != null){bz.setN(readExcel.getValue(row.getCell(13)));  }
				if(row.getCell(14) != null){bz.setO(readExcel.getValue(row.getCell(14)));  }
				if(row.getCell(15) != null){bz.setP(readExcel.getValue(row.getCell(15)));  }
				if(row.getCell(16) != null){bz.setQ(readExcel.getValue(row.getCell(16)));  }
				if(row.getCell(17) != null){bz.setR(readExcel.getValue(row.getCell(17)));  }
				if(row.getCell(18) != null){bz.setS(readExcel.getValue(row.getCell(18)));  }
				if(row.getCell(19) != null){bz.setT(readExcel.getValue(row.getCell(19)));  }
				if(row.getCell(20) != null){bz.setU(readExcel.getValue(row.getCell(20)));  }
				if(row.getCell(21) != null){bz.setV(readExcel.getValue(row.getCell(21)));  }
				if(row.getCell(22) != null){bz.setW(readExcel.getValue(row.getCell(22)));  }
				if(row.getCell(23) != null){bz.setX(readExcel.getValue(row.getCell(23)));  }
				if(row.getCell(24) != null){bz.setY(readExcel.getValue(row.getCell(24)));  }
				if(row.getCell(25) != null){bz.setZ(readExcel.getValue(row.getCell(25)));  }
				if(row.getCell(26) != null){bz.setAa(readExcel.getValue(row.getCell(26)));  }
				if(row.getCell(27) != null){bz.setAb(readExcel.getValue(row.getCell(27)));  }
				if(row.getCell(28) != null){bz.setAc(readExcel.getValue(row.getCell(28)));  }
				if(row.getCell(29) != null){bz.setAd(readExcel.getValue(row.getCell(29)));  }
				if(row.getCell(30) != null){bz.setAe(readExcel.getValue(row.getCell(30)));  }
				if(row.getCell(31) != null){bz.setAf(readExcel.getValue(row.getCell(31)));  }
				if(row.getCell(32) != null){bz.setAg(readExcel.getValue(row.getCell(32)));  }
				if(row.getCell(33) != null){bz.setAh(readExcel.getValue(row.getCell(33)));  }
				if(row.getCell(34) != null){bz.setAi(readExcel.getValue(row.getCell(34)));  }
				if(row.getCell(35) != null){bz.setAj(readExcel.getValue(row.getCell(35)));  }
				if(row.getCell(36) != null){bz.setAk(readExcel.getValue(row.getCell(36)));  }
				if(row.getCell(37) != null){bz.setAl(readExcel.getValue(row.getCell(37)));  }
				if(row.getCell(38) != null){bz.setAm(readExcel.getValue(row.getCell(38)));  }
				if(row.getCell(39) != null){bz.setAn(readExcel.getValue(row.getCell(39)));  }
				if(row.getCell(40) != null){bz.setAo(readExcel.getValue(row.getCell(40)));  }
				if(row.getCell(41) != null){bz.setAp(readExcel.getValue(row.getCell(41)));  }
				if(row.getCell(42) != null){bz.setAq(readExcel.getValue(row.getCell(42)));  }
				if(row.getCell(43) != null){bz.setAr(readExcel.getValue(row.getCell(43)));  }
				if(row.getCell(44) != null){bz.setAs(readExcel.getValue(row.getCell(44)));  }
				if(row.getCell(45) != null){bz.setAt(readExcel.getValue(row.getCell(45)));  }
				if(row.getCell(46) != null){bz.setAu(readExcel.getValue(row.getCell(46)));  }
				if(row.getCell(47) != null){bz.setAv(readExcel.getValue(row.getCell(47)));  }
				if(row.getCell(48) != null){bz.setAw(readExcel.getValue(row.getCell(48)));  }
				if(row.getCell(49) != null){bz.setAx(readExcel.getValue(row.getCell(49)));  }
				if(row.getCell(50) != null){bz.setAy(readExcel.getValue(row.getCell(50)));  }
				if(row.getCell(51) != null){bz.setAz(readExcel.getValue(row.getCell(51)));  }
				if(row.getCell(52) != null){bz.setBa(readExcel.getValue(row.getCell(52)));  }
				if(row.getCell(53) != null){bz.setBb(readExcel.getValue(row.getCell(53)));  }
				if(row.getCell(54) != null){bz.setBc(readExcel.getValue(row.getCell(54)));  }
			}
			

			// 插入数据库
			uploadTablesStatusMapper.insertY_GH_TD(bz);
		}
	}
	
	public void uploadAD_XZ_XF(ReadExcel readExcel,Sheet sheet,String uploadMonth) {

		AD_XZ_XF bz = new AD_XZ_XF();
		// 去掉表头，从第一行取数据
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if(row != null){
				bz.setUploadMonth(uploadMonth);
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
				if(row.getCell(10) != null){bz.setK(readExcel.getValue(row.getCell(10)));  }
				if(row.getCell(11) != null){bz.setL(readExcel.getValue(row.getCell(11)));  }
				if(row.getCell(12) != null){bz.setM(readExcel.getValue(row.getCell(12)));  }
				if(row.getCell(13) != null){bz.setN(readExcel.getValue(row.getCell(13)));  }
				if(row.getCell(14) != null){bz.setO(readExcel.getValue(row.getCell(14)));  }
				if(row.getCell(15) != null){bz.setP(readExcel.getValue(row.getCell(15)));  }
				if(row.getCell(16) != null){bz.setQ(readExcel.getValue(row.getCell(16)));  }
				if(row.getCell(17) != null){bz.setR(readExcel.getValue(row.getCell(17)));  }
				if(row.getCell(18) != null){bz.setS(readExcel.getValue(row.getCell(18)));  }
				if(row.getCell(19) != null){bz.setT(readExcel.getValue(row.getCell(19)));  }
				if(row.getCell(20) != null){bz.setU(readExcel.getValue(row.getCell(20)));  }
				if(row.getCell(21) != null){bz.setV(readExcel.getValue(row.getCell(21)));  }
				if(row.getCell(22) != null){bz.setW(readExcel.getValue(row.getCell(22)));  }
				if(row.getCell(23) != null){bz.setX(readExcel.getValue(row.getCell(23)));  }
				if(row.getCell(24) != null){bz.setY(readExcel.getValue(row.getCell(24)));  }
				if(row.getCell(25) != null){bz.setZ(readExcel.getValue(row.getCell(25)));  }
				if(row.getCell(26) != null){bz.setAa(readExcel.getValue(row.getCell(26)));  }
				if(row.getCell(27) != null){bz.setAb(readExcel.getValue(row.getCell(27)));  }
				if(row.getCell(28) != null){bz.setAc(readExcel.getValue(row.getCell(28)));  }
				if(row.getCell(29) != null){bz.setAd(readExcel.getValue(row.getCell(29)));  }
				if(row.getCell(30) != null){bz.setAe(readExcel.getValue(row.getCell(30)));  }
				if(row.getCell(31) != null){bz.setAf(readExcel.getValue(row.getCell(31)));  }
				if(row.getCell(32) != null){bz.setAg(readExcel.getValue(row.getCell(32)));  }
				if(row.getCell(33) != null){bz.setAh(readExcel.getValue(row.getCell(33)));  }
				if(row.getCell(34) != null){bz.setAi(readExcel.getValue(row.getCell(34)));  }
				if(row.getCell(35) != null){bz.setAj(readExcel.getValue(row.getCell(35)));  }
				if(row.getCell(36) != null){bz.setAk(readExcel.getValue(row.getCell(36)));  }
				if(row.getCell(37) != null){bz.setAl(readExcel.getValue(row.getCell(37)));  }
				if(row.getCell(38) != null){bz.setAm(readExcel.getValue(row.getCell(38)));  }
				if(row.getCell(39) != null){bz.setAn(readExcel.getValue(row.getCell(39)));  }
				if(row.getCell(40) != null){bz.setAo(readExcel.getValue(row.getCell(40)));  }
				if(row.getCell(41) != null){bz.setAp(readExcel.getValue(row.getCell(41)));  }
				if(row.getCell(42) != null){bz.setAq(readExcel.getValue(row.getCell(42)));  }
				if(row.getCell(43) != null){bz.setAr(readExcel.getValue(row.getCell(43)));  }
				if(row.getCell(44) != null){bz.setAs(readExcel.getValue(row.getCell(44)));  }
				if(row.getCell(45) != null){bz.setAt(readExcel.getValue(row.getCell(45)));  }
				if(row.getCell(46) != null){bz.setAu(readExcel.getValue(row.getCell(46)));  }
			}
			

			// 插入数据库
			uploadTablesStatusMapper.insertAD_XZ_XF(bz);
		}
	}
	
	public void uploadZXLN_ZYYN(ReadExcel readExcel,Sheet sheet,String uploadMonth) {

		ZXLN_ZYYN bz = new ZXLN_ZYYN();
		// 去掉表头，从第一行取数据
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if(row != null){
				bz.setUploadMonth(uploadMonth);
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
				if(row.getCell(10) != null){bz.setK(readExcel.getValue(row.getCell(10)));  }
				if(row.getCell(11) != null){bz.setL(readExcel.getValue(row.getCell(11)));  }
				if(row.getCell(12) != null){bz.setM(readExcel.getValue(row.getCell(12)));  }
				if(row.getCell(13) != null){bz.setN(readExcel.getValue(row.getCell(13)));  }
				if(row.getCell(14) != null){bz.setO(readExcel.getValue(row.getCell(14)));  }
				if(row.getCell(15) != null){bz.setP(readExcel.getValue(row.getCell(15)));  }
				if(row.getCell(16) != null){bz.setQ(readExcel.getValue(row.getCell(16)));  }
				if(row.getCell(17) != null){bz.setR(readExcel.getValue(row.getCell(17)));  }
				if(row.getCell(18) != null){bz.setS(readExcel.getValue(row.getCell(18)));  }
				if(row.getCell(19) != null){bz.setT(readExcel.getValue(row.getCell(19)));  }
				if(row.getCell(20) != null){bz.setU(readExcel.getValue(row.getCell(20)));  }
				if(row.getCell(21) != null){bz.setV(readExcel.getValue(row.getCell(21)));  }
				if(row.getCell(22) != null){bz.setW(readExcel.getValue(row.getCell(22)));  }
			}
			

			// 插入数据库
			uploadTablesStatusMapper.insertZXLN_ZYYN(bz);
		}
	}
	
	public void uploadGZXRY_DYWGDF(ReadExcel readExcel,Sheet sheet,String uploadMonth) {

		GZXRY_DYWGDF bz = new GZXRY_DYWGDF();
		// 去掉表头，从第一行取数据
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if(row != null){
				bz.setUploadMonth(uploadMonth);
				if(row.getCell(0) != null){bz.setA(readExcel.getValue(row.getCell(0)));  }   else{bz.setA(null);}
				if(row.getCell(1) != null){bz.setB(readExcel.getValue(row.getCell(1)));  }   else{bz.setB(null);}
				if(row.getCell(2) != null){bz.setC(readExcel.getValue(row.getCell(2)));  }   else{bz.setC(null);}
				if(row.getCell(3) != null){bz.setD(readExcel.getValue(row.getCell(3)));  }   else{bz.setD(null);}
				if(row.getCell(4) != null){bz.setE(readExcel.getValue(row.getCell(4)));  }   else{bz.setE(null);}
				if(row.getCell(5) != null){bz.setF(readExcel.getValue(row.getCell(5)));  }   else{bz.setF(null);}
				if(row.getCell(6) != null){bz.setG(readExcel.getValue(row.getCell(6)));  }   else{bz.setG(null);}
				if(row.getCell(7) != null){bz.setH(readExcel.getValue(row.getCell(7)));  }   else{bz.setH(null);}
				if(row.getCell(8) != null){bz.setI(readExcel.getValue(row.getCell(8)));  }   else{bz.setI(null);}
				if(row.getCell(9) != null){bz.setJ(readExcel.getValue(row.getCell(9)));  }   else{bz.setJ(null);}
				if(row.getCell(10) != null){bz.setK(readExcel.getValue(row.getCell(10)));  } else{bz.setK(null);}
				if(row.getCell(11) != null){bz.setL(readExcel.getValue(row.getCell(11)));  } else{bz.setL(null);}
				if(row.getCell(12) != null){bz.setM(readExcel.getValue(row.getCell(12)));  } else{bz.setM(null);}
			}
			

			// 插入数据库
			uploadTablesStatusMapper.insertGZXRY_DYWGDF(bz);
		}
	}
	
	public void uploadJTKH_KH(ReadExcel readExcel,Sheet sheet,String uploadMonth) {

		JTKH_KH bz = new JTKH_KH();
		// 去掉表头，从第3行取数据
		for (int i = 2; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if(row != null){
				bz.setUploadMonth(uploadMonth);
				if(row.getCell(0) != null){bz.setA(readExcel.getValue(row.getCell(0)));  }
				if(row.getCell(1) != null){bz.setB(readExcel.getValue(row.getCell(1)));  }
				if(row.getCell(2) != null){bz.setC(readExcel.getValue(row.getCell(2)));  }
				if(row.getCell(3) != null){bz.setD(readExcel.getValue(row.getCell(3)));  }
				if(row.getCell(4) != null){bz.setE(readExcel.getValue(row.getCell(4)));  }
				if(row.getCell(5) != null){bz.setF(readExcel.getValue(row.getCell(5)));  }
				if(row.getCell(6) != null){bz.setG(readExcel.getValue(row.getCell(6)));  }
				if(row.getCell(7) != null){bz.setH(readExcel.getValue(row.getCell(7)));  }
				if(row.getCell(8) != null){bz.setI(readExcel.getValue(row.getCell(8)));  }
			}
			

			// 插入数据库
			uploadTablesStatusMapper.insertJTKH_KH(bz);
		}
	}
	
	public void uploadJTKH_ZB(ReadExcel readExcel,Sheet sheet,String uploadMonth) {

		JTKH_ZB bz = new JTKH_ZB();
		// 去掉表头，从第3行取数据
		for (int i = 2; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if(row != null){
				bz.setUploadMonth(uploadMonth);
				if(row.getCell(0) != null){bz.setA(readExcel.getValue(row.getCell(0)));  }else{bz.setA(null);}
				if(row.getCell(1) != null){bz.setB(readExcel.getValue(row.getCell(1)));  }else{bz.setB(null);}
				if(row.getCell(2) != null){bz.setC(readExcel.getValue(row.getCell(2)));  }else{bz.setC(null);}
				if(row.getCell(3) != null){bz.setD(readExcel.getValue(row.getCell(3)));  }else{bz.setD(null);}
				if(row.getCell(4) != null){bz.setE(readExcel.getValue(row.getCell(4)));  }else{bz.setE(null);}
				if(row.getCell(5) != null){bz.setF(readExcel.getValue(row.getCell(5)));  }else{bz.setF(null);}
			}
			

			// 插入数据库
			uploadTablesStatusMapper.insertJTKH_ZB(bz);
		}
	}

}
