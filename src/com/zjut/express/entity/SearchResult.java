package com.zjut.express.entity;

/**
 * 普通查询API调用返回结果实体
 * 
 * @author HuGuojun
 * @date 2013-11-24 下午12:28:33
 * @modify
 * @version 1.0.0
 */
public class SearchResult {

	private Object time;
	private Object content;
	
	public Object getTime() {
		return time;
	}
	public void setTime(Object time) {
		this.time = time;
	}
	public Object getContent() {
		return content;
	}
	public void setContent(Object content) {
		this.content = content;
	}
	
}
