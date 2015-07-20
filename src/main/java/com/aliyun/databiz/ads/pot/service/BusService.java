package com.aliyun.databiz.ads.pot.service;

import com.aliyun.databiz.ads.pot.data.object.BusEntity;

/**
 * 大巴相关.<br>
 * 
 * @author zhixing.lz
 * @version 1.0
 */
public interface BusService {

	public BusEntity getBusEntity(String id) throws Exception ;
	
	public String insert(BusEntity busEntity) throws Exception ;
	
}
