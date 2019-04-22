package com.bs.job.service.job.model;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bs.job.service.common.util.BeanConvertUtil;
import com.bs.job.service.common.util.UnidUtil;
import com.bs.job.service.job.dao.JobResumeMapper;
import com.bs.job.service.job.dto.JobResumeDto;
import com.bs.job.service.job.enums.JobResumeEnum.Status;
import com.bs.job.service.user.model.UserService;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import lingshi.model.ServiceException;
import lingshi.valid.StringValid;

/**
 * 招聘投递业务接口实现类
 * 
 * @author caich
 *
 */
@Service
public class JobResumeServiceImpl implements JobResumeService {

	@Resource
	private JobResumeMapper jobResumeMapper;
	@Resource
	private JobService jobService;
	@Resource
	private UserService userService;

	@Override
	public PageList<JobResumeDto> applicants(String companyId, int page, int rows) throws ServiceException {
		PageList<JobResume> pageList = jobResumeMapper.companyApplicants(companyId, new PageBounds(page, rows));
		PageList<JobResumeDto> result = new PageList<>(pageList.getPaginator());
		for (JobResume item : pageList) {
			JobResumeDto jobResumeDto = BeanConvertUtil.convert(item, JobResumeDto.class);
			jobResumeDto.setJobDto(jobService.detail(item.getJobId(), null));
			jobResumeDto.setUserDto(userService.detail(item.getUserId()));
			result.add(jobResumeDto);
		}
		return result;
	}

	@Override
	public void add(String jobId, String userId) throws ServiceException {
		if (StringValid.isNullOrEmpty(jobId)) {
			throw new ServiceException("招聘信息id不能为空");
		}
		if (StringValid.isNullOrEmpty(userId)) {
			throw new ServiceException("求职者id不能为空");
		}
		JobResume jobResumeQ = new JobResume();
		jobResumeQ.setJobId(jobId);
		jobResumeQ.setUserId(userId);
		if (jobResumeMapper.count(jobResumeQ) > 0) {
			throw new ServiceException("请勿重复投递");
		}
		JobResume jobResume = new JobResume();
		Date date = new Date();
		jobResume.setId(UnidUtil.getId());
		jobResume.setUserId(userId);
		jobResume.setJobId(jobId);
		jobResume.setUpdateUserId(userId);
		jobResume.setUpdateTime(date);
		jobResume.setCreateTime(date);
		jobResume.setCreateUserId(userId);
		jobResume.setStatus(Status.UN_DO.value);
		jobResumeMapper.insert(jobResume);
	}

	@Override
	public void update(JobResume jobResume) throws ServiceException {
		if (StringValid.isNullOrEmpty(jobResume.getUpdateUserId())) {
			throw new ServiceException("修改人id不能为空");
		}
		if (StringValid.isNullOrEmpty(jobResume.getId())) {
			throw new ServiceException("id不能为空");
		}
		jobResume.setUpdateTime(new Date());
		jobResumeMapper.update(jobResume);
	}

}
