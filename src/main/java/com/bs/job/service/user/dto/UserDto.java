package com.bs.job.service.user.dto;

public class UserDto {

	/**
     * 主键：id
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名 | 公司名称
     */
    private String name;

    /**
     * 用户类型（-1管理员 1普通用户 2 公司账户）
     */
    private Integer type;

    /**
     * 性别(0女 1男)
     */
    private Integer sex;

    /**
     * 头像图片路径
     */
    private String headPicUrl;
    
    /**
     * 头像图片路径不包含前缀
     */
    private String headPicUrlNoPrefix;

    /**
     * 所在学校
     */
    private String school;

    /**
     * 个人简介 | 公司简介
     */
    private String introduce;

    /**
     * 简历过期时间（月）
     */
    private Integer expMonth;

    /**
     * 工作经历
     */
    private String workLive;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getHeadPicUrl() {
        return headPicUrl;
    }

    public void setHeadPicUrl(String headPicUrl) {
        this.headPicUrl = headPicUrl;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public Integer getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(Integer expMonth) {
        this.expMonth = expMonth;
    }

    public String getWorkLive() {
        return workLive;
    }

    public void setWorkLive(String workLive) {
        this.workLive = workLive;
    }
    
    public String getHeadPicUrlNoPrefix() {
		return headPicUrlNoPrefix;
	}
    
    public void setHeadPicUrlNoPrefix(String headPicUrlNoPrefix) {
		this.headPicUrlNoPrefix = headPicUrlNoPrefix;
	}
	
}
