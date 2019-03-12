package com.bs.job.service.user.model;

import com.bs.job.service.user.dto.UserDto;

import lingshi.model.ServiceException;

/**
 * 用户业务逻辑接口
 * 
 * @author caich
 *
 */
public interface UserService {

	/**
	 * 登陆
	 * 
	 * @param username
	 * @param pwd
	 * @return
	 * @throws ServiceException
	 */
	UserDto login(String username,String pwd) throws ServiceException;

	/**
	 * 修改
	 * 
	 * @param user
	 * @return
	 * @throws ServiceException
	 */
	UserDto update(User user) throws ServiceException;
	
	/**
	 * 注册
	 * 
	 * @param user
	 * @return
	 * @throws ServiceException
	 */
	UserDto register(User user) throws ServiceException;
	
	/**
	 * 详情
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	UserDto detail(String id) throws ServiceException;
}
