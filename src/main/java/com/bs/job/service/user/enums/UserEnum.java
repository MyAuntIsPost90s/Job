package com.bs.job.service.user.enums;

/**
 * 用户类型枚举
 * 
 * @author caich
 *
 */
public class UserEnum {

	/**
	 * 用户类型
	 **/
	public enum Type {
		ADMIN(-1, "管理员"), NORMAL(1, "普通用户"), COMPANY(2, "公司");

		public Integer value;
		public String valueZh;

		Type(int value, String valueZh) {
			this.value = value;
			this.valueZh = valueZh;
		}
	}
}
