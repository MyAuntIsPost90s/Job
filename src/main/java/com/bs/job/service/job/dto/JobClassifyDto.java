package com.bs.job.service.job.dto;

public class JobClassifyDto {

	/**
     * 主键：id
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 类型( 1.兼职 2.全职 )
     */
    private Integer type;
    
    
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
	
	public Integer getType() {
		return type;
	}
	
	public void setType(Integer type) {
		this.type = type;
	}
}
