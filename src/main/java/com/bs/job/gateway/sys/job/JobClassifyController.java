package com.bs.job.gateway.sys.job;

import java.util.Arrays;

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
import com.bs.job.service.user.dto.UserDto;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import lingshi.gateway.model.RequestHolder;
import lingshi.model.ServiceException;
import lingshi.valid.StringValid;

/**
 * 工作类别接口
 * 
 * @author caich
 *
 */
@RestController("sysJobClassifyController")
@RequestMapping("/sys/jobClassify")
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

	/**
	 * 添加招聘分类
	 * 
	 * @param request
	 * @param response
	 * @param jobClassify
	 */
	@RequestMapping("/add")
	public void add(HttpServletRequest request, HttpServletResponse response, JobClassify jobClassify) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			UserDto userDto = (UserDto) requestHolder.getClientUser();
			jobClassify.setCreateUserId(userDto.getId());
			jobClassifyService.add(jobClassify);
			requestHolder.success("添加招聘分类成功");
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail("添加招聘分类失败");
		}
	}

	/**
	 * 添加招聘分类
	 * 
	 * @param request
	 * @param response
	 * @param jobClassify
	 */
	@RequestMapping("/update")
	public void update(HttpServletRequest request, HttpServletResponse response, JobClassify jobClassify) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			UserDto userDto = (UserDto) requestHolder.getClientUser();
			jobClassify.setUpdateUserId(userDto.getId());
			jobClassifyService.update(jobClassify);
			requestHolder.success("修改招聘分类成功");
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail("修改招聘分类失败");
		}
	}

	/**
	 * 删除招聘信息分类
	 * 
	 * @param request
	 * @param response
	 * @param ids
	 */
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, String ids) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		if (StringValid.isNullOrEmpty(ids)) {
			requestHolder.fail("ids不能为空");
			return;
		}
		try {
			String[] strs = ids.split(",");
			jobClassifyService.delete(Arrays.asList(strs));
			requestHolder.success("修改招聘分类成功");
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail("修改招聘分类失败");
		}
	}
}
