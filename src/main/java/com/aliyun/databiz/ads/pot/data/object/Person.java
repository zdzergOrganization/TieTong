package com.aliyun.databiz.ads.pot.data.object;

import lombok.Data;
import lombok.experimental.Builder;

@Data
@Builder
public class Person {

	private String id;
	private String name;
	private String sex;
	private String bornDate;
	private String age;
	private String origin;
	private String policy;
	private String education;
	private String marry;
	private String address;
	private String phone;
	private String socialSecurity;
	private String paymentBase;
	private String crime;
	private String policeStation;
	private String isSecondChild;
	private String birthHospital;
	
}
