package com.bs.job.service.job.model;

import com.bs.job.service.job.dto.JobResumeDto;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import lingshi.model.ServiceException;

/**
 * 招聘投递业务接口
 * 
 * @author caich
 *
 */
public interface JobResumeService {

	/**
	 * 获取申请人信息
	 * 
	 * @param companyId
	 * @param name
	 * @param status
	 * @param page
	 * @param rows
	 * @return
	 * @throws ServiceException
	 */
	PageList<JobResumeDto> applicants(String companyId, int page, int rows)
			throws ServiceException;

	/**
	 * 添加职位申请
	 * 
	 * @param jobId
	 * @param userId
	 * @throws ServiceException
	 */
	void add(String jobId, String userId) throws ServiceException;

	/**
	 * 修改职位申请
	 * 
	 * @param jobResume
	 * @throws ServiceException
	 */
	void update(JobResume jobResume) throws ServiceException;

}
