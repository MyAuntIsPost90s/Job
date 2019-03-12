package com.bs.job.gateway.app.job;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bs.job.gateway.uimodel.AppPageList;
import com.bs.job.service.common.util.LogUtil;
import com.bs.job.service.job.dto.JobClassifyDto;
import com.bs.job.service.job.model.JobClassify;
import com.bs.job.service.job.model.JobClassifyService;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import lingshi.gateway.model.RequestHolder;
import lingshi.model.ServiceException;

/**
 * 工作类别接口
 * 
 * @author caich
 *
 */
@RestController("appJobClassifyController")
@RequestMapping("/app/jobClassify")
public class JobClassifyController {

	@Resource
	private JobClassifyService jobClassifyService;

	/**
	 * 获取分类列表
	 * 
	 * @param request
	 * @param response
	 * @param jobClassify
	 * @param page
	 * @param rows
	 */
	@RequestMapping("/list")
	public void list(HttpServletRequest request, HttpServletResponse response, JobClassify jobClassify, int page,
			int rows) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			PageList<JobClassifyDto> pageList = jobClassifyService.list(jobClassify, page, rows);
			requestHolder.success(new AppPageList<>(pageList.getPaginator().getTotalCount(), pageList));
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail("加载失败");
		}
	}

	/**
	 * 获取招聘分类详情
	 * 
	 * @param request
	 * @param response
	 * @param id
	 */
	@RequestMapping("/detail")
	public void detail(HttpServletRequest request, HttpServletResponse response, String id) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			JobClassifyDto jobClassifyDto = jobClassifyService.detail(id);
			requestHolder.success(jobClassifyDto);
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail("加载失败");
		}
	}
}
