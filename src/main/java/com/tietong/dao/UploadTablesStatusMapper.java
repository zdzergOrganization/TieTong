package com.tietong.dao;

import java.util.List;

import com.tietong.pojo.UploadTablesStatus;

public interface UploadTablesStatusMapper {
	/**
	 * 上传表状态.<br>
	 * 
	 * @return
	 */
	public List<UploadTablesStatus> getUploadTablesStatus(String uploadMonth);
	
}
