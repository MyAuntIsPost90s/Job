package com.bs.job.service.job.dto;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class JobDto {
	/**
	 * 主键：id
	 */
	private String id;

	/**
	 * 工作名称
	 */
	private String name;

	/**
	 * 创建人id
	 */
	private String createUserId;

	/**
	 * 创建时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	 * 分类id
	 */
	private String jobClassifyId;

	/**
	 * 结算方式 ( 1日结 2 月结 3 季结 4 年结 )
	 */
	private Integer settleType;

	/**
	 * 薪资
	 */
	private String salary;

	/**
	 * 工作类型( 1 全职 2 兼职 )
	 */
	private Integer type;

	/**
	 * 工作地点
	 */
	private String address;

	/**
	 * 招聘人数
	 */
	private Integer peopleCountLimit;

	/**
	 * 发布状态（0 未审核 1审核通过 2审核不通过 3下架）
	 */
	private Integer publishStatus;

	/**
	 * 性别限制（0 女 1男 2不限）
	 */
	private Integer sexLimit;

	/**
	 * 是否投递过
	 */
	private Boolean applyed;

	/**
	 * 工作分类dto
	 */
	private JobClassifyDto jobClassify;

	/**
	 * 报酬单位
	 */
	private String salaryUnit;

	/**
	 * 工作时间
	 */
	private String workTime;

	/**
	 * 工作详情
	 */
	private String content;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getJobClassifyId() {
		return jobClassifyId;
	}

	public void setJobClassifyId(String jobClassifyId) {
		this.jobClassifyId = jobClassifyId;
	}

	public Integer getSettleType() {
		return settleType;
	}

	public void setSettleType(Integer settleType) {
		this.settleType = settleType;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getPeopleCountLimit() {
		return peopleCountLimit;
	}

	public void setPeopleCountLimit(Integer peopleCountLimit) {
		this.peopleCountLimit = peopleCountLimit;
	}

	public Integer getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(Integer publishStatus) {
		this.publishStatus = publishStatus;
	}

	public Integer getSexLimit() {
		return sexLimit;
	}

	public void setSexLimit(Integer sexLimit) {
		this.sexLimit = sexLimit;
	}

	public Boolean getApplyed() {
		return applyed;
	}

	public void setApplyed(Boolean applyed) {
		this.applyed = applyed;
	}

	public JobClassifyDto getJobClassify() {
		return jobClassify;
	}

	public void setJobClassify(JobClassifyDto jobClassify) {
		this.jobClassify = jobClassify;
	}

	public String getSalaryUnit() {
		return salaryUnit;
	}

	public void setSalaryUnit(String salaryUnit) {
		this.salaryUnit = salaryUnit;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWorkTime() {
		return workTime;
	}

	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}
}
