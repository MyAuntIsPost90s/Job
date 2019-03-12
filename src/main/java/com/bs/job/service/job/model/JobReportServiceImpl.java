package com.bs.job.service.job.model;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bs.job.service.common.util.UnidUtil;
import com.bs.job.service.job.dao.JobReportMapper;
import com.bs.job.service.job.dto.JobDto;
import com.bs.job.service.job.dto.JobReportDto;
import com.bs.job.service.user.dto.UserDto;
import com.bs.job.service.user.model.UserService;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import lingshi.model.ServiceException;
import lingshi.valid.ObjectValid;
import lingshi.valid.StringValid;

/**
 * 举报业务逻辑
 * 
 * @author caich
 *
 */
@Service
public class JobReportServiceImpl implements JobReportService {

	@Resource
	private JobReportMapper jobReportMapper;
	@Resource
	private JobService jobService;
	@Resource
	private UserService userService;

	@Override
	public void add(JobReport jobReport) throws ServiceException {
		if (StringValid.isNullOrEmpty(jobReport.getCreateUserId())) {
			throw new ServiceException("创建人id不能为空");
		}
		if (StringValid.isNullOrEmpty(jobReport.getJobId())) {
			throw new ServiceException("工作id不能为空");
		}
		if (StringValid.isNullOrEmpty(jobReport.getNote())) {
			throw new ServiceException("举报信息不能为空");
		}
		JobDto jobDto = jobService.detail(jobReport.getJobId(), null);
		if (ObjectValid.isNull(jobDto)) {
			throw new ServiceException("该招聘信息不存在");
		}
		UserDto userDto = userService.detail(jobDto.getCreateUserId());
		if (ObjectValid.isNull(userDto)) {
			throw new ServiceException("找不到招聘信息对应的公司");
		}
		Date date = new Date();
		jobReport.setId(UnidUtil.getId());
		jobReport.setUpdateUserId(jobReport.getCreateUserId());
		jobReport.setUpdateTime(date);
		jobReport.setCreateTime(date);
		jobReport.setUserId(jobReport.getCreateUserId());
		jobReport.setJobName(jobDto.getName());
		jobReport.setCompanyName(userDto.getName());
		jobReportMapper.insert(jobReport);
	}

	@Override
	public PageList<JobReportDto> list(String id, String jobName, String userRealName, int page, int rows)
			throws ServiceException {
		return jobReportMapper.list(id, jobName, userRealName, new PageBounds(page, rows));
	}

	@Override
	public JobReportDto detail(String id) throws ServiceException {
		if (StringValid.isNullOrEmpty(id)) {
			throw new ServiceException("id不能为空");
		}
		List<JobReportDto> list = list(id, null, null, 1, 1);
		return ObjectValid.isEmpty(list) ? null : list.get(0);
	}

	@Override
	public void delete(List<String> ids) throws ServiceException {
		if (ObjectValid.isEmpty(ids)) {
			return;
		}
		jobReportMapper.batchDelete(ids);
	}

}
