package com.bs.job.service.user.model;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bs.job.service.ServiceConstant;
import com.bs.job.service.common.util.BeanConvertUtil;
import com.bs.job.service.common.util.UnidUtil;
import com.bs.job.service.user.UserConstant;
import com.bs.job.service.user.dao.UserMapper;
import com.bs.job.service.user.dto.UserDto;
import com.bs.job.service.user.enums.UserEnum;

import lingshi.model.ServiceException;
import lingshi.valid.ObjectValid;
import lingshi.valid.StringValid;

/**
 * 用户业务逻辑接口实现类
 * 
 * @author caich
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;

	@Override
	public UserDto login(String username, String pwd) throws ServiceException {
		if (StringValid.isNullOrEmpty(username)) {
			throw new ServiceException("用户名不能为空");
		}
		if (StringValid.isNullOrEmpty(pwd)) {
			throw new ServiceException("密码不能为空");
		}
		User userQ = new User();
		userQ.setUsername(username);
		List<User> list = userMapper.getList(userQ);
		if (ObjectValid.isEmpty(list)) {
			throw new ServiceException("当前用户不存在");
		}
		if (!pwd.equalsIgnoreCase(list.get(0).getPassword())) {
			throw new ServiceException("用户名或密码错误");
		}
		UserDto result = BeanConvertUtil.convert(list.get(0), UserDto.class);
		if (StringValid.isNotNullOrEmpty(list.get(0).getHeadPicUrl())) {
			result.setHeadPicUrlNoPrefix(list.get(0).getHeadPicUrl());
			result.setHeadPicUrl(ServiceConstant.getUrl(list.get(0).getHeadPicUrl()));
		}
		return result;
	}

	@Override
	public UserDto update(User user) throws ServiceException {
		if (StringValid.isNullOrEmpty(user.getId())) {
			throw new ServiceException("用户id不能为空");
		}
		if (StringValid.isNullOrEmpty(user.getUpdateUserId())) {
			throw new ServiceException("修改人id不能为空");
		}
		user.setUpdateTime(new Date());
		userMapper.update(user);
		user = userMapper.getSingle(user.getId());
		if (ObjectValid.isNull(user)) {
			throw new ServiceException("修改的用户不存在");
		}
		UserDto result = BeanConvertUtil.convert(user, UserDto.class);
		if (StringValid.isNotNullOrEmpty(user.getHeadPicUrl())) {
			result.setHeadPicUrlNoPrefix(user.getHeadPicUrl());
			result.setHeadPicUrl(ServiceConstant.getUrl(user.getHeadPicUrl()));
		}
		return result;
	}

	@Override
	public UserDto register(User user) throws ServiceException {
		if (StringValid.isNullOrEmpty(user.getUsername())) {
			throw new ServiceException("用户名不能为空");
		}
		if (StringValid.isNullOrEmpty(user.getPassword())) {
			throw new ServiceException("密码不能为空");
		}
		if (ObjectValid.isNull(user.getType())) {
			throw new ServiceException("用户类型不能为空");
		}
		User userQ = new User();
		userQ.setUsername(user.getUsername());
		if (userMapper.count(userQ) > 0) {
			throw new ServiceException("该用户名已被注册");
		}
		if(user.getType().equals(UserEnum.Type.COMPANY.value)){
			user.setExpMonth(UserConstant.EXP_MONTH);
		}
		Date date = new Date();
		user.setId(UnidUtil.getId());
		user.setCreateTime(date);
		user.setCreateUserId(user.getId());
		user.setUpdateTime(date);
		user.setUpdateUserId(user.getId());
		userMapper.insert(user);
		UserDto result = BeanConvertUtil.convert(user, UserDto.class);
		if (StringValid.isNotNullOrEmpty(user.getHeadPicUrl())) {
			result.setHeadPicUrlNoPrefix(user.getHeadPicUrl());
			result.setHeadPicUrl(ServiceConstant.getUrl(user.getHeadPicUrl()));
		}
		return result;
	}

	@Override
	public UserDto detail(String id) throws ServiceException {
		if(StringValid.isNullOrEmpty(id)) {
			throw new ServiceException("id不能为空");
		}
		User user = userMapper.getSingle(id);
		UserDto result = BeanConvertUtil.convert(user, UserDto.class);
		if (StringValid.isNotNullOrEmpty(user.getHeadPicUrl())) {
			result.setHeadPicUrlNoPrefix(user.getHeadPicUrl());
			result.setHeadPicUrl(ServiceConstant.getUrl(user.getHeadPicUrl()));
		}
		return result;
	}

}
