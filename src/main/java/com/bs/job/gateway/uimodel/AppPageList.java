package com.bs.job.gateway.uimodel;

import java.util.List;

public class AppPageList<T> {

	private Integer total;

	private List<T> rows;

	public AppPageList(Integer total, List<T> rows) {
		this.total = total;
		this.rows = rows;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
}
