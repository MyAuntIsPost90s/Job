package com.bs.job.gateway.sys.job;

import java.util.Arrays;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bs.job.gateway.uimodel.AppPageList;
import com.bs.job.service.common.util.LogUtil;
import com.bs.job.service.job.dto.JobReportDto;
import com.bs.job.service.job.model.JobReportService;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import lingshi.gateway.model.RequestHolder;
import lingshi.model.ServiceException;

/**
 * 简历举报接口
 * 
 * @author caich
 *
 */
@RestController("sysJobReportController")
@RequestMapping("/sys/jobReport")
public class JobReportController {

	@Resource
	private JobReportService jobReportService;

	/**
	 * 查询举报列表
	 * 
	 * @param request
	 * @param response
	 * @param jobName
	 * @param userRealName
	 * @param page
	 * @param rows
	 */
	@RequestMapping("/list")
	public void list(HttpServletRequest request, HttpServletResponse response, String jobName, String userRealName,
			int page, int rows) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			PageList<JobReportDto> pageList = jobReportService.list(null, jobName, userRealName, page, rows);
			requestHolder.success(new AppPageList<>(pageList.getPaginator().getTotalCount(), pageList));
		}catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail("加载举报信息失败");
		}
	}
	
	/**
	 * 查询举报详情
	 * 
	 * @param request
	 * @param response
	 * @param id
	 */
	@RequestMapping("/detail")
	public void detail(HttpServletRequest request, HttpServletResponse response, String id) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			JobReportDto jobReportDto = jobReportService.detail(id);
			requestHolder.success(jobReportDto);
		}catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail("加载举报信息失败");
		}
	}
	
	/**
	 * 删除举报记录
	 * 
	 * @param request
	 * @param response
	 * @param id
	 */
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, String ids) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			jobReportService.delete(Arrays.asList(ids.split(",")));
			requestHolder.success("删除成功");
		}catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail("删除失败");
		}
	}
}
