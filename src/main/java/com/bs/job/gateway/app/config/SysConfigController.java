package com.bs.job.gateway.app.config;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bs.job.service.common.util.LogUtil;
import com.bs.job.service.config.dto.SysConfigDto;
import com.bs.job.service.config.model.SysConfigService;

import lingshi.gateway.model.RequestHolder;
import lingshi.model.ServiceException;

/**
 * 配置接口
 * 
 * @author caich
 */
@RestController("appSysConfigController")
@RequestMapping("/app/sysConfig")
public class SysConfigController {

	@Resource
	private SysConfigService sysConfigService;

	/**
	 * 获取配置详情
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/detail")
	public void detail(HttpServletRequest request, HttpServletResponse response) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			SysConfigDto sysConfigDto = sysConfigService.detail();
			requestHolder.success(sysConfigDto);
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail("加载失败");
		}
	}

}
