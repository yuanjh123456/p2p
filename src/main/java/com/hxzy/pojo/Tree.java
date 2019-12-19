package com.hxzy.pojo;

import java.util.List;

public class Tree {

	private Integer id;
	private String text;
	private boolean checked;
	private String url;
	private String icons;
	private List<Tree> children;
	private Object attributes;
	
	@Override
	public String toString() {
		return "Tree [id=" + id + ", text=" + text + ", checked=" + checked + ", url=" + url + ", icons=" + icons
				+ ", children=" + children + ", attributes=" + attributes + "]";
	}
	public Object getAttributes() {
		return url;
	}
	public void setAttributes(Object attributes) {
		this.attributes = attributes;
	}
	public String getIcon() {
		return icons;
	}
	public void setIcon(String icon) {
		this.icons = icon;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<Tree> getChildren() {
		return children;
	}
	public void setChildren(List<Tree> children) {
		this.children = children;
	}
	
	
}
