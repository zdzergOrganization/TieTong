package com.tietong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tietong.pojo.AD_XZ_XF;
import com.tietong.pojo.BZ;
import com.tietong.pojo.GZXRY_DYWGDF;
import com.tietong.pojo.JTKH_KH;
import com.tietong.pojo.JTKH_ZB;
import com.tietong.pojo.UploadTablesStatus;
import com.tietong.pojo.Y_GH_TD;
import com.tietong.pojo.ZXLN_ZYYN;

public interface UploadTablesStatusMapper {
	/**
	 * 上传表状态.<br>
	 * 
	 * @return
	 */
	public List<UploadTablesStatus> getUploadTablesStatus(String uploadMonth);
	

	public void truncate(String tableName);
	
	public void delete(@Param(value="tableName")String tableName,@Param(value="uploadMonth")String uploadMonth);
	
	public void insertBZ(BZ bz);
	
	public void insertY_GH_TD(Y_GH_TD bz);
	
	public void insertAD_XZ_XF(AD_XZ_XF bz);
	
	public void insertZXLN_ZYYN(ZXLN_ZYYN bz);
	
	public void insertGZXRY_DYWGDF(GZXRY_DYWGDF bz);
	
	public void insertJTKH_KH(JTKH_KH bz);
	
	public void insertJTKH_ZB(JTKH_ZB bz);
	
	//查询
	public BZ getBZ(@Param(value="uploadMonth")String uploadMonth,@Param(value="employeeType")String employeeType);
	
	public List<Y_GH_TD> getY_GH_TD(@Param(value="uploadMonth")String uploadMonth,@Param(value="employeeName")String employeeName);
	
	public List<AD_XZ_XF> getAD_XZ_XF(@Param(value="uploadMonth")String uploadMonth,@Param(value="employeeName")String employeeName);

	public List<ZXLN_ZYYN> getZXLN_ZYYN(@Param(value="uploadMonth")String uploadMonth,@Param(value="employeeName")String employeeName);

	public List<GZXRY_DYWGDF> getGZXRY_DYWGDF(@Param(value="uploadMonth")String uploadMonth,@Param(value="employeeName")String employeeName);

	public List<JTKH_KH> getJTKH_KH(@Param(value="uploadMonth")String uploadMonth,@Param(value="employeeName")String employeeName);

	public List<JTKH_ZB> getJTKH_ZB(@Param(value="uploadMonth")String uploadMonth,@Param(value="employeeName")String employeeName);
	
	
	
}
