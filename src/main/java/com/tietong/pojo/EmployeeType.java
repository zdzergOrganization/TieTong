package com.tietong.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class EmployeeType {
	private int id;
	private String kpiDate;
	private String employeeName;
	private String type;	
	private String regionPQ;	
	private String regionQ;	
	private String regionWG;	
	private String entryDate;
	private String quitDate;
	private String employeeType;
	private String employeeTypeRel;
	private String createTime;
}
