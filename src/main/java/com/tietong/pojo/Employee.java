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
	private String employee_name;
	private String type;	
	private String region_pq;	
	private String region_q;	
	private String region_wg;	
	private String entry_date;
	private String quit_date;
	private String month_end_date;
	private String create_time;
}
