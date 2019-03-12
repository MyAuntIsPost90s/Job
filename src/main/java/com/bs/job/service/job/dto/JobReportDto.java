package com.bs.job.service.job.dto;

public class JobReportDto {
	/**
     * 主键：id
     */
    private String id;

    /**
     * 工作名称
     */
    private String jobId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 备注
     */
    private String note;

    /**
     * 工作名称
     */
    private String jobName;

    /**
     * 公司名称
     */
    private String companyName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
