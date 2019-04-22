package com.bs.job.gateway.base.chat;

public class Chat{
	private String currName;
	
	private String currKey;
	
	private String targetKey; 
	
	private String targetName;
	
	private Integer code;
	
	private String content;
	
	private String errMsg;
	
	public Integer getCode() {
		return code;
	}
	
	public void setCode(Integer code) {
		this.code = code;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getCurrKey() {
		return currKey;
	}
	
	public void setCurrKey(String currKey) {
		this.currKey = currKey;
	}
	
	public String getErrMsg() {
		return errMsg;
	}
	
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	public String getTargetKey() {
		return targetKey;
	}
	
	public void setTargetKey(String targetKey) {
		this.targetKey = targetKey;
	}
	
	public String getCurrName() {
		return currName;
	}
	
	public void setCurrName(String currName) {
		this.currName = currName;
	}
	
	public String getTargetName() {
		return targetName;
	}
	
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
}
