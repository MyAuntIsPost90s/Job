package com.bs.job.service.job.dao;

import org.apache.ibatis.annotations.Param;

import com.bs.job.service.job.dto.JobDto;
import com.bs.job.service.job.model.Job;
import com.bs.job.service.job.model.JobListQuery;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import lingshi.mybaties.mapperextend.BaseMapper;

public interface JobMapper extends BaseMapper<Job> {

	/**
	 * 招聘信息列表
	 * 
	 * @param job
	 * @param applicantId
	 * @param orderType
	 * @param pageBounds
	 * @return
	 */
	PageList<JobDto> list(JobListQuery jobListQuery, PageBounds pageBounds);

	/**
	 * 我投递过的招聘信息
	 * 
	 * @param userId
	 * @param name
	 * @param pageBounds
	 * @return
	 */
	PageList<JobDto> applyHistory(@Param("userId") String userId, @Param("name") String name, PageBounds pageBounds);
}