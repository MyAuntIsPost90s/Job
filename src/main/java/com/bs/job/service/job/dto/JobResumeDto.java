package com.bs.job.service.job.dto;

import com.bs.job.service.user.dto.UserDto;

public class JobResumeDto {
	
	private String id;

	private JobDto jobDto;

	private UserDto userDto;

	private Integer status;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public JobDto getJobDto() {
		return jobDto;
	}

	public void setJobDto(JobDto jobDto) {
		this.jobDto = jobDto;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}
}
