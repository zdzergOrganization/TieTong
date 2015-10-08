package com.tietong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tietong.pojo.UploadTablesStatus;

public interface UploadTablesStatusMapper {
	/**
	 * 上传表状态.<br>
	 * 
	 * @return
	 */
	public List<UploadTablesStatus> getUploadTablesStatus(String uploadMonth);
	

	public void truncate(String tableName);
	
	public void delete(@Param(value="tableName")String tableName,@Param(value="uploadMonth")String uploadMonth);
	
}
