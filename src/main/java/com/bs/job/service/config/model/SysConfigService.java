package com.bs.job.service.config.model;

import com.bs.job.service.config.dto.SysConfigDto;

import lingshi.model.ServiceException;

/**
 * 系统配置逻辑处理接口
 * 
 * @author caich
 *
 */
public interface SysConfigService {

	/**
	 * 获取默认配置信息
	 * 
	 * @return
	 * @throws ServiceException
	 */
	SysConfigDto detail() throws ServiceException;
	
	/**
	 * 编辑配置信息
	 * 
	 * @return
	 * @throws ServiceException
	 */
	void edit(SysConfig sysConfig) throws ServiceException;
	
}
