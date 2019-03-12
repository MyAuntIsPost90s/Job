package com.bs.job.service.job.model;

import java.util.List;

import com.bs.job.service.job.dto.JobReportDto;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import lingshi.model.ServiceException;

/**
 * 举报业务逻辑
 * 
 * @author caich
 *
 */
public interface JobReportService {

	/**
	 * 添加举报
	 * 
	 * @param jobReport
	 * @throws ServiceException
	 */
	void add(JobReport jobReport) throws ServiceException;

	/**
	 * 查询举报列表
	 * 
	 * @param id
	 * @param jobName
	 * @param userRealName
	 * @param page
	 * @param rows
	 * @return
	 * @throws ServiceException
	 */
	PageList<JobReportDto> list(String id, String jobName, String userRealName, int page, int rows)
			throws ServiceException;

	/**
	 * 获取举报详情
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	JobReportDto detail(String id) throws ServiceException;

	/**
	 * 删除举报信息
	 * 
	 * @param ids
	 * @throws ServiceException
	 */
	void delete(List<String> ids) throws ServiceException;
}
