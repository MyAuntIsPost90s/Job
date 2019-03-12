package com.bs.job.service.job.model;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bs.job.service.common.util.UnidUtil;
import com.bs.job.service.job.dao.JobMapper;
import com.bs.job.service.job.dto.JobClassifyDto;
import com.bs.job.service.job.dto.JobDto;
import com.bs.job.service.job.enums.JobEnum.PublishStatus;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import lingshi.model.ServiceException;
import lingshi.valid.ObjectValid;
import lingshi.valid.StringValid;

/**
 * 招聘信息业务接口实现类
 * 
 * @author caich
 *
 */
@Service
public class JobServiceImpl implements JobService {

	@Resource
	private JobMapper jobMapper;
	@Resource
	private JobClassifyService jobClassifyService;

	@Override
	public PageList<JobDto> list(JobListQuery jobListQuery, int page, int rows) throws ServiceException {
		PageList<JobDto> pageList = jobMapper.list(jobListQuery, new PageBounds(page, rows));
		for (JobDto jobDto : pageList) {
			if (StringValid.isNotNullOrEmpty(jobDto.getJobClassifyId())) {
				jobDto.setJobClassifyDto(jobClassifyService.detail(jobDto.getJobClassifyId()));
			}
		}
		return pageList;
	}

	@Override
	public PageList<JobDto> applyHistory(String userId, String name, int page, int rows) throws ServiceException {
		if (StringValid.isNullOrEmpty(userId)) {
			throw new ServiceException("用户id不能为空");
		}
		PageList<JobDto> pageList = jobMapper.applyHistory(userId, name, new PageBounds(page, rows));
		for (JobDto jobDto : pageList) {
			if (StringValid.isNotNullOrEmpty(jobDto.getJobClassifyId())) {
				jobDto.setJobClassifyDto(jobClassifyService.detail(jobDto.getJobClassifyId()));
			}
		}
		return pageList;
	}

	@Override
	public JobDto detail(String id, String userId) throws ServiceException {
		JobListQuery jobListQuery = new JobListQuery();
		jobListQuery.setJobId(id);
		jobListQuery.setApplicantId(userId);
		List<JobDto> list = list(jobListQuery, 1, 1);
		return ObjectValid.isEmpty(list) ? null : list.get(0);
	}

	@Override
	public void add(Job job) throws ServiceException {
		if (StringValid.isNullOrEmpty(job.getCreateUserId())) {
			throw new ServiceException("创建人id不能为空");
		}
		if (StringValid.isNullOrEmpty(job.getName())) {
			throw new ServiceException("名称不能为空");
		}
		if (StringValid.isNullOrEmpty(job.getJobClassifyId())) {
			throw new ServiceException("分类id不能为空");
		}
		if (StringValid.isNullOrEmpty(job.getContent())) {
			throw new ServiceException("工作描述不能为空");
		}
		if (StringValid.isNullOrEmpty(job.getWorkTime())) {
			throw new ServiceException("工作时间不能为空");
		}
		if (StringValid.isNullOrEmpty(job.getAddress())) {
			throw new ServiceException("工作地点不能为空");
		}
		if (ObjectValid.isNull(job.getSettleType())) {
			throw new ServiceException("结算方式不能为空");
		}
		JobClassifyDto jobClassifyDto = jobClassifyService.detail(job.getJobClassifyId());
		if (ObjectValid.isNull(jobClassifyDto)) {
			throw new ServiceException("当前分类不存在");
		}
		Date date = new Date();
		job.setId(UnidUtil.getId());
		job.setType(jobClassifyDto.getType());
		job.setUpdateUserId(job.getCreateUserId());
		job.setUpdateTime(date);
		job.setCreateTime(date);
		job.setPublishStatus(PublishStatus.UNCHECK.value);
		jobMapper.insert(job);
	}

	@Override
	public void update(Job job) throws ServiceException {
		if (StringValid.isNullOrEmpty(job.getId())) {
			throw new ServiceException("id不能为空");
		}
		if (StringValid.isNullOrEmpty(job.getUpdateUserId())) {
			throw new ServiceException("修改人id不能为空");
		}
		if (StringValid.isNotNullOrEmpty(job.getJobClassifyId())) {
			JobClassifyDto jobClassifyDto = jobClassifyService.detail(job.getJobClassifyId());
			if (ObjectValid.isNull(jobClassifyDto)) {
				throw new ServiceException("当前分类不存在");
			}
			job.setType(jobClassifyDto.getType());
		}
		job.setUpdateTime(new Date());
		jobMapper.update(job);
	}

	@Override
	public void delete(List<String> ids) throws ServiceException {
		if (ObjectValid.isEmpty(ids)) {
			return;
		}
		jobMapper.batchDelete(ids);
	}

}
