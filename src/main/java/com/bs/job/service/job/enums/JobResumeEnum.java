package com.bs.job.service.job.enums;

/**
 * 招聘信息枚举
 * 
 * @author caich
 *
 */
public class JobResumeEnum {
	
	/**
	 * 审核状态
	 **/
	public enum Status {
		UN_DO(0, "未审核"), DONE(1, "已处理");

		public Integer value;
		public String valueZh;

		Status(int value, String valueZh) {
			this.value = value;
			this.valueZh = valueZh;
		}
	}
}
