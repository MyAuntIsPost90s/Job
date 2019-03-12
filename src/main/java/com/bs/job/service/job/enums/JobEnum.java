package com.bs.job.service.job.enums;

/**
 * 招聘信息枚举
 * 
 * @author caich
 *
 */
public class JobEnum {
	
	/**
	 * 审核状态
	 **/
	public enum PublishStatus {
		UNCHECK(0, "未审核"), PASS(1, "通过"), UNPASS(2, "不通过");

		public Integer value;
		public String valueZh;

		PublishStatus(int value, String valueZh) {
			this.value = value;
			this.valueZh = valueZh;
		}
	}
}
