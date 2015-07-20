package com.aliyun.databiz.ads.pot.data.object;

import lombok.Data;

@Data
public class BusEntity {

	/**
	 * 大巴id
	 */
	private String id;
	/**
	 * 大巴位置更新时间
	 */
	private String updateTime;
	/**
	 * 获取数据的服务器时间
	 */
	private String serverTime;
	/**
	 * 大巴位置：经度
	 */
	private float longitude;
	/**
	 * 大巴位置：纬度
	 */
	private float latitude;
	
	/**
	 * 司机证件ID
	 */
	private String driverId;
	/**
	 * 司机姓名
	 */
	private String driverName;
	/**
	 * 车牌号
	 */
	private String licensePlate;
	/**
	 * 大巴注册日期
	 */
	private String registerDate;
	
}
