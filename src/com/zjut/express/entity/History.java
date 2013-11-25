package com.zjut.express.entity;

/**
 * 历史查询记录
 * 
 * @author HuGuojun
 * @date 2013-11-24 下午7:37:35
 * @modify
 * @version 1.0.0
 */
public class History {

	private String date;
	private String time;
	private String order;
	private String code;
	private String name;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
