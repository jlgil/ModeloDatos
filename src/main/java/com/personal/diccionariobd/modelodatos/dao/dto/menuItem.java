package com.personal.diccionariobd.modelodatos.dao.dto;

import java.util.List;

public class menuItem {

	private String name;

	private String url;

	private menuItem parent;

	private List<menuItem> children;

	public menuItem() {}

	public menuItem(String name,List<menuItem> children) {
		this.name = name;
		this.children = children;
	}

	public menuItem(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public menuItem getParent() {
		return parent;
	}

	public List<menuItem> getChildren() {
		return children;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParent(menuItem parent) {
		this.parent = parent;
	}

	public void setChildren(List<menuItem> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "MenuItem [name=" + name + ", url=" + url + ", parent=" + parent
				+ ", children=" + children + "]";
	}
}

