package com.tietong.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UploadTablesStatus {
	private int id;
	private String uploadMonth;
	private String tablesName;	
	private String tablesDesc;	
	private int cnt;	
}
