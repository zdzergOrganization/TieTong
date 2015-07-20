package com.aliyun.databiz.ads.pot.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliyun.databiz.ads.pot.data.object.BusEntity;
import com.aliyun.databiz.ads.pot.service.BusService;
import com.aliyun.databiz.ads.pot.web.ErrorCode;
import com.aliyun.databiz.ads.pot.web.StandardJsonResult;

/**
 * 
 * @author 李政（至行）
 * @version 1.0
 */
@Controller
@RequestMapping("/api/bus")
public class BusController {
	
	@Autowired
	private BusService busService;

	/**
	 * 获取大巴的当前位置.<br>
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @return {@link BusEntity}
	 * @throws Exception
	 */
	@RequestMapping(value="/position", method = RequestMethod.GET)
	public @ResponseBody StandardJsonResult getBusEntityById (
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam("id") String id) throws Exception {
		try {
			BusEntity busEntity = busService.getBusEntity(id);
			if (busEntity == null)
				throw new Exception("No record for id: " + id);
			return new StandardJsonResult(busEntity);
		} catch (Exception e) {
			return new StandardJsonResult(ErrorCode.COMMON_UNDEFINED_ERROR, e.getMessage(), null);
		}
		
	}
	
	/**
	 * GET方法更新大巴位置数据.<br>
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @param updateTime
	 * @param longitude
	 * @param latitude
	 */
	@RequestMapping(value="/position/insert", method = RequestMethod.GET)
	public @ResponseBody StandardJsonResult insertBusPosition (
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam("id") String id,
			@RequestParam("time") String time,
			@RequestParam("longitude") String longitudeStr,
			@RequestParam("latitude") String latitudeStr) throws Exception {
		try {
			float longitude = Float.parseFloat(longitudeStr);
			float latitude = Float.parseFloat(latitudeStr);
			
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			formater.parse(time);
			
			String servertime = formater.format(new Date());
			
			BusEntity busEntity = new BusEntity();
			busEntity.setId(id);
			busEntity.setUpdateTime(time);
			busEntity.setServerTime(servertime);
			busEntity.setLongitude(longitude);
			busEntity.setLatitude(latitude);
			String res = busService.insert(busEntity);
			return new StandardJsonResult(res);
		} catch (NumberFormatException e1) {
			return new StandardJsonResult(ErrorCode.COMMON_NUMBER_FORMAT_ERROR, e1.getMessage(), null);
		} catch (ParseException e2) {
			return new StandardJsonResult(ErrorCode.COMMON_NUMBER_FORMAT_ERROR, e2.getMessage(), null);
		} catch (Exception e) {
			return new StandardJsonResult(ErrorCode.COMMON_UNDEFINED_ERROR, e.getMessage(), null);
		}
	}
	
	/**
	 * POST方法更新大巴位置数据.<br>
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @param updateTime
	 * @param longitude
	 * @param latitude
	 */
	@RequestMapping(value="/position", method = RequestMethod.POST)
	public @ResponseBody StandardJsonResult postBusPosition (
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam("id") String id,
			@RequestParam("time") String time,
			@RequestParam("longitude") String longitudeStr,
			@RequestParam("latitude") String latitudeStr) throws Exception {
		try {
			float longitude = Float.parseFloat(longitudeStr);
			float latitude = Float.parseFloat(latitudeStr);
			
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			formater.parse(time);
			
			BusEntity busEntity = new BusEntity();
			busEntity.setId(id);
			busEntity.setUpdateTime(time);
			busEntity.setLongitude(longitude);
			busEntity.setLatitude(latitude);
			busService.insert(busEntity);
			return new StandardJsonResult();
		} catch (NumberFormatException e1) {
			return new StandardJsonResult(ErrorCode.COMMON_NUMBER_FORMAT_ERROR, e1.getMessage(), null);
		} catch (ParseException e2) {
			return new StandardJsonResult(ErrorCode.COMMON_NUMBER_FORMAT_ERROR, e2.getMessage(), null);
		} catch (Exception e) {
			return new StandardJsonResult(ErrorCode.COMMON_UNDEFINED_ERROR, e.getMessage(), null);
		}
	}
	
}
