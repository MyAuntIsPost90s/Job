package com.bs.job.service.job.dao;

import org.apache.ibatis.annotations.Param;

import com.bs.job.service.job.dto.JobReportDto;
import com.bs.job.service.job.model.JobReport;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import lingshi.mybaties.mapperextend.BaseMapper;

public interface JobReportMapper extends BaseMapper<JobReport> {

	/**
	 * 查询简历举报列表
	 * 
	 * @param id
	 * @param jobName
	 * @param userRealName
	 * @param pageBounds
	 * @return
	 */
	PageList<JobReportDto> list(@Param("id") String id, @Param("jobName") String jobName,
			@Param("userRealName") String userRealName, PageBounds pageBounds);

}