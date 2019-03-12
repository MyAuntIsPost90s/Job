package com.bs.job.service.job.model;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bs.job.service.common.util.BeanConvertUtil;
import com.bs.job.service.common.util.UnidUtil;
import com.bs.job.service.job.dao.JobClassifyMapper;
import com.bs.job.service.job.dto.JobClassifyDto;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import lingshi.model.ServiceException;
import lingshi.valid.ObjectValid;
import lingshi.valid.StringValid;

/**
 * 招聘分类业务接口实现类
 * 
 * @author cch
 *
 */
@Service
public class JobClassifyServiceImpl implements JobClassifyService {

	@Resource
	private JobClassifyMapper jobClassifyMapper;

	@Override
	public PageList<JobClassifyDto> list(JobClassify jobClassify, int page, int rows) throws ServiceException {
		PageList<JobClassify> pageList = jobClassifyMapper.getListWithPage(jobClassify, new PageBounds(page, rows));
		PageList<JobClassifyDto> result = new PageList<>(pageList.getPaginator());
		for (JobClassify item : pageList) {
			result.add(BeanConvertUtil.convert(item, JobClassifyDto.class));
		}
		return result;
	}

	@Override
	public void add(JobClassify jobClassify) throws ServiceException {
		if (StringValid.isNullOrEmpty(jobClassify.getCreateUserId())) {
			throw new ServiceException("创建人id不能为空");
		}
		if (StringValid.isNullOrEmpty(jobClassify.getName())) {
			throw new ServiceException("名称不能为空");
		}
		if (ObjectValid.isNull(jobClassify.getType())) {
			throw new ServiceException("招聘信息类型不能为空");
		}
		Date date = new Date();
		jobClassify.setId(UnidUtil.getId());
		jobClassify.setCreateTime(date);
		jobClassify.setUpdateUserId(jobClassify.getCreateUserId());
		jobClassify.setUpdateTime(date);
		jobClassifyMapper.insert(jobClassify);
	}

	@Override
	public void update(JobClassify jobClassify) throws ServiceException {
		if (StringValid.isNullOrEmpty(jobClassify.getUpdateUserId())) {
			throw new ServiceException("修改人id不能为空");
		}
		if (StringValid.isNullOrEmpty(jobClassify.getId())) {
			throw new ServiceException("id不能为空");
		}
		jobClassify.setUpdateTime(new Date());
		jobClassifyMapper.update(jobClassify);
	}

	@Override
	public void delete(List<String> ids) throws ServiceException {
		if (ObjectValid.isEmpty(ids)) {
			return;
		}
		jobClassifyMapper.batchDelete(ids);
	}

	@Override
	public JobClassifyDto detail(String id) throws ServiceException {
		if (StringValid.isNullOrEmpty(id)) {
			throw new ServiceException("id不能为空");
		}
		JobClassify jobClassifyQ = new JobClassify();
		List<JobClassifyDto> list = list(jobClassifyQ, 1, 1);
		return ObjectValid.isEmpty(list) ? null : list.get(0);
	}

}
