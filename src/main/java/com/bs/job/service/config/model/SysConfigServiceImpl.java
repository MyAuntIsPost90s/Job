package com.bs.job.service.config.model;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bs.job.service.ServiceConstant;
import com.bs.job.service.common.util.BeanConvertUtil;
import com.bs.job.service.common.util.UnidUtil;
import com.bs.job.service.config.dao.SysConfigMapper;
import com.bs.job.service.config.dto.SysConfigDto;

import lingshi.model.ServiceException;
import lingshi.valid.ObjectValid;
import lingshi.valid.StringValid;

/**
 * 系统配置逻辑处理接口实现类
 * 
 * @author caich
 *
 */
@Service
public class SysConfigServiceImpl implements SysConfigService {

	@Resource
	private SysConfigMapper sysConfigMapper;

	@Override
	public SysConfigDto detail() throws ServiceException {
		List<SysConfig> list = sysConfigMapper.getList(new SysConfig());
		if (ObjectValid.isEmpty(list)) {
			return null;
		}
		SysConfigDto sysConfigDto = BeanConvertUtil.convert(list.get(0), SysConfigDto.class);
		sysConfigDto.setIntroducePicUrlNoPrefix(list.get(0).getIntroducePicUrl());
		sysConfigDto.setIntroducePicUrl(ServiceConstant.getUrl(list.get(0).getIntroducePicUrl()));
		sysConfigDto.setLogoPicUrlNoPrefix(list.get(0).getLogoPicUrl());
		sysConfigDto.setLogoPicUrl(ServiceConstant.getUrl(list.get(0).getLogoPicUrl()));
		return sysConfigDto;
	}

	@Override
	public synchronized void edit(SysConfig sysConfig) throws ServiceException {
		if (StringValid.isNullOrEmpty(sysConfig.getUpdateUserId())) {
			throw new ServiceException("修改人id不能为空");
		}
		Date date= new Date();
		List<SysConfig> list = sysConfigMapper.getList(new SysConfig());
		if (ObjectValid.isEmpty(list)) { // 添加
			if (StringValid.isNullOrEmpty(sysConfig.getLogoPicUrl())) {
				throw new ServiceException("logo不能为空");
			}
			if (StringValid.isNullOrEmpty(sysConfig.getIntroducePicUrl())) {
				throw new ServiceException("介绍url不能为空");
			}
			if (StringValid.isNullOrEmpty(sysConfig.getCopyright())) {
				throw new ServiceException("版权信息不能为空");
			}
			sysConfig.setId(UnidUtil.getId());
			sysConfig.setCreateTime(date);
			sysConfig.setCreateUserId(sysConfig.getUpdateUserId());
			sysConfig.setUpdateTime(date);
			sysConfigMapper.insert(sysConfig);
		} else { // 修改
			sysConfig.setId(list.get(0).getId());
			sysConfig.setUpdateTime(date);
			sysConfigMapper.update(sysConfig);
		}
	}

}
