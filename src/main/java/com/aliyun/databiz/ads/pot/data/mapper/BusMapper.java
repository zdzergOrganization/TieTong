package com.aliyun.databiz.ads.pot.data.mapper;

import com.aliyun.databiz.ads.pot.data.object.BusEntity;

public interface BusMapper {

	public BusEntity getPosition(String id);
	
	public int updateBusPosition(BusEntity busEntity);

}
