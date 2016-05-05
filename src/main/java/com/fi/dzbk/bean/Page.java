package com.fi.dzbk.bean;

/**
 * 
 * @author wxx1223
 *
 */
public class Page {
	
	private String url;
	private String contentHtml;
	private String parentUrl;
	private int type;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContentHtml() {
		return contentHtml;
	}
	public void setContentHtml(String contentHtml) {
		this.contentHtml = contentHtml;
	}
	public String getParentUrl() {
		return parentUrl;
	}
	public void setParentUrl(String parentUrl) {
		this.parentUrl = parentUrl;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
}
