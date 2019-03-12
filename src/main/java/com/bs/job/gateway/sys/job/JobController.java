package com.bs.job.gateway.sys.job;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bs.job.gateway.uimodel.AppPageList;
import com.bs.job.service.common.util.LogUtil;
import com.bs.job.service.job.dto.JobDto;
import com.bs.job.service.job.model.Job;
import com.bs.job.service.job.model.JobListQuery;
import com.bs.job.service.job.model.JobService;
import com.bs.job.service.user.dto.UserDto;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import lingshi.gateway.model.RequestHolder;
import lingshi.model.ServiceException;
import lingshi.valid.ObjectValid;

/**
 * 招聘信息接口
 * 
 * @author caich
 */
@RestController("sysJobController")
@RequestMapping("/sys/job")
public class JobController {

	@Resource
	private JobService jobService;

	/**
	 * 获取招聘信息列表
	 * 
	 * @param request
	 * @param response
	 * @param jobListQuery
	 * @param page
	 * @param rows
	 */
	@RequestMapping("/list")
	public void list(HttpServletRequest request, HttpServletResponse response, JobListQuery jobListQuery, int page,
			int rows) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			PageList<JobDto> pageList = jobService.list(jobListQuery, page, rows);
			requestHolder.success(new AppPageList<>(pageList.getPaginator().getTotalCount(), pageList));
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail("加载失败");
		}
	}

	/**
	 * 获取招聘信息详情
	 * 
	 * @param request
	 * @param response
	 * @param id
	 */
	@RequestMapping("/detail")
	public void detail(HttpServletRequest request, HttpServletResponse response, String id) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			Object obj = requestHolder.getClientUser();
			String userId = null;
			if (ObjectValid.isNotNull(obj)) {
				userId = ((UserDto) obj).getId();
			}
			JobDto jobDto = jobService.detail(id, userId);
			requestHolder.success(jobDto);
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail("加载失败");
		}
	}

	/**
	 * 获取申请历史信息
	 * 
	 * @param request
	 * @param response
	 * @param name
	 * @param page
	 * @param rows
	 */
	@RequestMapping("/applyHistory")
	public void applyHistory(HttpServletRequest request, HttpServletResponse response, String name, int page,
			int rows) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			UserDto userDto = (UserDto) requestHolder.getClientUser();
			PageList<JobDto> pageList = jobService.applyHistory(userDto.getId(), name, page, rows);
			requestHolder.success(new AppPageList<>(pageList.getPaginator().getTotalCount(), pageList));
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail("加载失败");
		}
	}
	
	/**
	 * 修改招聘信息
	 * 
	 * @param request
	 * @param response
	 * @param job
	 */
	@RequestMapping("/update")
	public void update(HttpServletRequest request, HttpServletResponse response, Job job) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			UserDto userDto = (UserDto) requestHolder.getClientUser();
			job.setUpdateUserId(userDto.getId());
			jobService.update(job);
			requestHolder.success("修改招聘信息成功");
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail("修改招聘信息失败");
		}
	}

}
