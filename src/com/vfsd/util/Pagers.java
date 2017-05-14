package com.vfsd.util;

import java.util.List;

import org.nutz.dao.pager.Pager;

public class Pagers<T> extends Pager{
	
	private List<T> rows;
	private int total;
	
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
	

}
