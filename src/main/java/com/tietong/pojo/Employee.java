package com.tietong.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Employee {
	private int id;
	private String employeeName;
	private String type;	
	private String regionPQ;	
	private String regionQ;	
	private String regionWG;	
	private String entryDate;
	private String quitDate;
	private String monthEndDate;
	private String createTime;
}
