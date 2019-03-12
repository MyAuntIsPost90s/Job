package com.bs.job.gateway.sys.config;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bs.job.service.common.util.LogUtil;
import com.bs.job.service.config.dto.SysConfigDto;
import com.bs.job.service.config.model.SysConfig;
import com.bs.job.service.config.model.SysConfigService;
import com.bs.job.service.user.dto.UserDto;

import lingshi.gateway.model.RequestHolder;
import lingshi.model.ServiceException;

/**
 * 配置接口
 * 
 * @author caich
 */
@RestController("sysSysConfigController")
@RequestMapping("/sys/sysConfig")
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

	/**
	 * 编辑系统配置
	 * 
	 * @param request
	 * @param response
	 * @param sysConfig
	 */
	@RequestMapping("/edit")
	public void edit(HttpServletRequest request, HttpServletResponse response, SysConfig sysConfig) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			UserDto currUser = (UserDto) requestHolder.getClientUser();
			sysConfig.setUpdateUserId(currUser.getId());
			sysConfigService.edit(sysConfig);
			requestHolder.success("编辑系统配置成功");
		} catch (ServiceException e) {
			requestHolder.success(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.success("编辑系统配置失败");
		}
	}

}
