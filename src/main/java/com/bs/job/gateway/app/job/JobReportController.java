package com.bs.job.gateway.app.job;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bs.job.service.common.util.LogUtil;
import com.bs.job.service.job.model.JobReport;
import com.bs.job.service.job.model.JobReportService;
import com.bs.job.service.user.dto.UserDto;

import lingshi.gateway.model.RequestHolder;
import lingshi.model.ServiceException;

/**
 * 简历举报接口
 * 
 * @author caich
 *
 */
@RestController("appJobReportController")
@RequestMapping("/app/jobReport")
public class JobReportController {

	@Resource
	private JobReportService jobReportService;
	
	/**
	 * 查询举报详情
	 * 
	 * @param request
	 * @param response
	 * @param jobReport
	 */
	@RequestMapping("/detail")
	public void detail(HttpServletRequest request, HttpServletResponse response, JobReport jobReport) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			UserDto userDto = (UserDto)requestHolder.getClientUser();
			jobReport.setCreateUserId(userDto.getId());
			jobReportService.add(jobReport);
			requestHolder.success("举报信息已提交");
		}catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail("举报失败");
		}
	}
}
