package com.yhjia.me.voice;

import java.util.HashMap;

public class Request {

	public static enum ReqStyle {
		REQ_DELETE, REQ_POST, REQ_GET, REQ_CACHE_GET, REQ_CACHE_POST, REQ_DEBUG
	}

	private ReqStyle reqStyle = ReqStyle.REQ_GET;
	private HashMap<String, String> heads;
	private Object params;
	private String url;
	private String mark;
	private OnDataResult result;

	public HashMap<String, String> getHeads() {
		return heads;
	}

	public Object getParams() {
		return params;
	}

	public ReqStyle getReqStyle() {
		return reqStyle;
	}

	public String getUrl() {
		return url;
	}

	public OnDataResult getResult() {
		return result;
	}

	public void setReqStyle(ReqStyle reqStyle) {
		this.reqStyle = reqStyle;
	}

	public void setHeads(HashMap<String, String> heads) {
		this.heads = heads;
	}

	public void setParams(Object params) {
		this.params = params;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setResult(OnDataResult result) {
		this.result = result;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

}