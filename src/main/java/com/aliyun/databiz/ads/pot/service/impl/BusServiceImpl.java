package com.aliyun.databiz.ads.pot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyun.databiz.ads.pot.data.mapper.BusMapper;
import com.aliyun.databiz.ads.pot.data.object.BusEntity;
import com.aliyun.databiz.ads.pot.service.BusService;

/**
 * 大巴相关实现类.<br>
 * 
 * @author zhixing.lz
 * @version 1.0
 */
@Service("busService")
public class BusServiceImpl implements BusService {

	@Autowired
	private BusMapper busMapper;

	/* (non-Javadoc)
	 * @see com.aliyun.databiz.ads.common.service.BusService#getBusEntity(java.lang.String)
	 */
	public BusEntity getBusEntity(String id) throws Exception {
		// TODO Auto-generated method stub
		try {
			BusEntity busEntity = busMapper.getPosition(id);
			return busEntity;
		} catch (Exception e) {
			throw e;
		}
	}

	/* (non-Javadoc)
	 * @see com.aliyun.databiz.ads.common.service.BusService#insert(com.aliyun.databiz.ads.pot.data.object.BusEntity)
	 */
	public String insert(BusEntity busEntity) throws Exception {
		// TODO Auto-generated method stub
		try{
			int res = busMapper.updateBusPosition(busEntity);
			return "succ insert !";
		}catch(Exception e){
			throw e;
		}
	}

}
