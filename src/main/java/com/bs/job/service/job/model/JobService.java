package com.bs.job.service.job.model;

import java.util.List;

import com.bs.job.service.job.dto.JobDto;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import lingshi.model.ServiceException;

/**
 * 招聘信息业务接口
 * 
 * @author caich
 *
 */
public interface JobService {

	/**
	 * 查询招聘信息列表
	 * 
	 * @param job
	 * @param applicantId
	 * @param page
	 * @param rows
	 * @return
	 * @throws ServiceException
	 */
	PageList<JobDto> list(JobListQuery jobListQuery, int page, int rows) throws ServiceException;

	/**
	 * 查询申请历史
	 * 
	 * @param userId
	 * @param name
	 * @param page
	 * @param rows
	 * @return
	 * @throws ServiceException
	 */
	PageList<JobDto> applyHistory(String userId, String name, int page, int rows) throws ServiceException;

	/**
	 * 查询招聘信息详情
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	JobDto detail(String id, String userId) throws ServiceException;

	/**
	 * 添加
	 * 
	 * @param job
	 * @throws ServiceException
	 */
	void add(Job job) throws ServiceException;

	/**
	 * 修改
	 * 
	 * @param job
	 * @throws ServiceException
	 */
	void update(Job job) throws ServiceException;

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @throws ServiceException
	 */
	void delete(List<String> ids) throws ServiceException;
}
