package com.yhjia.me.voice;

public interface OnDataResult {

	public void dataResult(Request req, Object data);
	
	public void faild(Request req);
}