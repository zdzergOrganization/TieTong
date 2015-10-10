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
	
	
	
	
	
}
