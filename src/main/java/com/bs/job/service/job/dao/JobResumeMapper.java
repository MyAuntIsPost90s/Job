package com.bs.job.service.job.dao;

import org.apache.ibatis.annotations.Param;

import com.bs.job.service.job.model.JobResume;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import lingshi.mybaties.mapperextend.BaseMapper;

public interface JobResumeMapper extends BaseMapper<JobResume> {

	/**
	 * 获取投递本公司的简历
	 * 
	 * @param companyId
	 * @param pageBounds
	 * @return
	 */
	PageList<JobResume> companyApplicants(@Param("companyId") String companyId, PageBounds pageBounds);

}