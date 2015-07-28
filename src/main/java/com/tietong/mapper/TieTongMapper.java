package com.tietong.mapper;

import com.tietong.dao.User;

public interface TieTongMapper {
	/**
	 * 根据用户名获取用户实例.<br>
	 * 
	 * @param username
	 * @return
	 */
	public User getUserByName(String username);
}
