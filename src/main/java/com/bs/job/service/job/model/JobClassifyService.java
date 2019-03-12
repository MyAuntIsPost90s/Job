package com.bs.job.service.job.model;

import java.util.List;

import com.bs.job.service.job.dto.JobClassifyDto;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import lingshi.model.ServiceException;

/**
 * 招聘分类业务接口
 * 
 * @author caich
 *
 */
public interface JobClassifyService {

	/**
	 * 获取招聘信息分类列表
	 * 
	 * @param jobClassify
	 * @param page
	 * @param rows
	 * @return
	 * @throws ServiceException
	 */
	PageList<JobClassifyDto> list(JobClassify jobClassify, int page, int rows) throws ServiceException;
	
	/**
	 * 查询招聘信息分类详情
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	JobClassifyDto detail(String id) throws ServiceException;

	/**
	 * 添加招聘分类信息
	 * 
	 * @param jobClassify
	 * @throws ServiceException
	 */
	void add(JobClassify jobClassify) throws ServiceException;
	
	/**
	 * 修改招聘分类信息
	 * 
	 * @param jobClassify
	 * @throws ServiceException
	 */
	void update(JobClassify jobClassify) throws ServiceException;
	
	/**
	 * 删除
	 * 
	 * @param ids
	 * @throws ServiceException
	 */
	void delete(List<String> ids) throws ServiceException;
}
