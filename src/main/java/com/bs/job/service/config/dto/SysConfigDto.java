package com.bs.job.service.config.dto;

public class SysConfigDto {

	 /**
     * 主键：id
     */
    private String id;

    /**
     * 介绍图片路径
     */
    private String introducePicUrl;
    
    /**
     * 介绍图片路径(不包含前缀)
     */
    private String introducePicUrlNoPrefix;

    /**
     * 版权
     */
    private String copyright;

    /**
     * logo图片路径
     */
    private String logoPicUrl;
    
    /**
     * logo图片路径（不包含前缀）
     */
    private String logoPicUrlNoPrefix;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIntroducePicUrl() {
        return introducePicUrl;
    }

    public void setIntroducePicUrl(String introducePicUrl) {
        this.introducePicUrl = introducePicUrl;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getLogoPicUrl() {
        return logoPicUrl;
    }

    public void setLogoPicUrl(String logoPicUrl) {
        this.logoPicUrl = logoPicUrl;
    }
	
    public String getIntroducePicUrlNoPrefix() {
		return introducePicUrlNoPrefix;
	}
    
    public void setIntroducePicUrlNoPrefix(String introducePicUrlNoPrefix) {
		this.introducePicUrlNoPrefix = introducePicUrlNoPrefix;
	}
    
    public String getLogoPicUrlNoPrefix() {
		return logoPicUrlNoPrefix;
	}
    
    public void setLogoPicUrlNoPrefix(String logoPicUrlNoPrefix) {
		this.logoPicUrlNoPrefix = logoPicUrlNoPrefix;
	}
}
