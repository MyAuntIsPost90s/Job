package com.bs.job.gateway.app.job;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bs.job.gateway.uimodel.AppPageList;
import com.bs.job.service.common.util.LogUtil;
import com.bs.job.service.job.dto.JobResumeDto;
import com.bs.job.service.job.model.JobResume;
import com.bs.job.service.job.model.JobResumeService;
import com.bs.job.service.user.dto.UserDto;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import lingshi.gateway.model.RequestHolder;
import lingshi.model.ServiceException;

/**
 * 简历投递接口
 * 
 * @author caich
 */
@RestController("appJobResumeController")
@RequestMapping("/app/JobResume")
public class JobResumeController {

	@Resource
	private JobResumeService jobResumeService;

	/**
	 * 获取投递的简历列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/applicants")
	public void applicants(HttpServletRequest request, HttpServletResponse response, int page, int rows) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			UserDto userDto = (UserDto) requestHolder.getClientUser();
			PageList<JobResumeDto> pageList = jobResumeService.applicants(userDto.getId(), page, rows);
			requestHolder.success(new AppPageList<>(pageList.getPaginator().getTotalCount(), pageList));
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail("加载失败");
		}
	}

	/**
	 * 投递简历
	 * 
	 * @param request
	 * @param response
	 * @param jobId
	 */
	@RequestMapping("/add")
	public void add(HttpServletRequest request, HttpServletResponse response, String jobId) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			UserDto userDto = (UserDto) requestHolder.getClientUser();
			jobResumeService.add(jobId, userDto.getId());
			requestHolder.success("投递成功");
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail("投递失败");
		}
	}

	/**
	 * 修改投递信息
	 * 
	 * @param request
	 * @param response
	 * @param jobResume
	 */
	@RequestMapping("/update")
	public void update(HttpServletRequest request, HttpServletResponse response, JobResume jobResume) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			UserDto userDto = (UserDto) requestHolder.getClientUser();
			jobResume.setUpdateUserId(userDto.getId());
			jobResumeService.update(jobResume);
			requestHolder.success("修改成功");
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail("修改失败");
		}
	}
}
