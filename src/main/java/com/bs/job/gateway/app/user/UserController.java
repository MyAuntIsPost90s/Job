package com.bs.job.gateway.app.user;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bs.job.service.common.util.LogUtil;
import com.bs.job.service.user.dto.UserDto;
import com.bs.job.service.user.model.User;
import com.bs.job.service.user.model.UserService;

import lingshi.gateway.model.RequestHolder;
import lingshi.model.ServiceException;

/**
 * 用户接口
 * 
 * @author caich
 */
@RestController("appUserController")
@RequestMapping("/app/user")
public class UserController {

	@Resource
	private UserService userService;

	/**
	 * 登陆
	 * 
	 * @param request
	 * @param response
	 * @param username
	 * @param pwd
	 */
	@RequestMapping("/login")
	public void login(HttpServletRequest request, HttpServletResponse response, String username, String pwd) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			UserDto userDto = userService.login(username, pwd);
			String accessToken = requestHolder.addClientUser(userDto);
			requestHolder.success("登陆成功", accessToken);
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail("登陆失败");
		}
	}

	/**
	 * 获取当前用户登陆信息
	 * 
	 * @param request
	 * @param response
	 * @param username
	 * @param pwd
	 */
	@RequestMapping("/currUser")
	public void currUser(HttpServletRequest request, HttpServletResponse response) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			UserDto userDto = (UserDto) requestHolder.getClientUser();
			requestHolder.success(userDto);
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail("获取当前登陆用户失败");
		}
	}

	/**
	 * 修改用户信息
	 * 
	 * @param request
	 * @param response
	 * @param user
	 */
	@RequestMapping("/update")
	public void update(HttpServletRequest request, HttpServletResponse response, User user) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			UserDto currUser = (UserDto) requestHolder.getClientUser();
			user.setUpdateUserId(currUser.getId());
			user.setId(currUser.getId());
			currUser = userService.update(user);
			requestHolder.updateClientUser(currUser);
			requestHolder.success("修改成功");
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail("修改失败");
		}
	}

	/**
	 * 注册
	 * 
	 * @param request
	 * @param response
	 * @param user
	 */
	@RequestMapping("/register")
	public void register(HttpServletRequest request, HttpServletResponse response, User user) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			userService.register(user);
			requestHolder.success("注册成功");
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail("注册失败");
		}
	}

	/**
	 * 获取用户信息详情
	 * 
	 * @param request
	 * @param response
	 * @param id
	 */
	@RequestMapping("/detail")
	public void detail(HttpServletRequest request, HttpServletResponse response, String id) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			UserDto userDto = userService.detail(id);
			requestHolder.success(userDto);
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail("加载失败");
		}
	}
}
