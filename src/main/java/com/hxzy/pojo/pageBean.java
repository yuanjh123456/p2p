package com.hxzy.pojo;

public class pageBean {
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private Integer page;
	private Integer rows;
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public pageBean(String name, Integer page, Integer rows) {
		super();
		this.name = name;
		this.page = page;
		this.rows = rows;
	}
	@Override
	public String toString() {
		return "pageBean [name=" + name + ", page=" + page + ", rows=" + rows + "]";
	}
	public pageBean() {
		super();
	}
	

}
