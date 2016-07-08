package com.yhjia.me.photo.take;

import android.text.TextUtils;

import com.yhjia.me.photo.browse.ImageBean;

import java.io.Serializable;
import java.util.List;

public class ImageFloder implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String dir;
	private String  firstImagePath;
	private int count;
	private List<ImageBean> images;
	private String dirName;
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	public String getFirstImagePath() {
		return "file://" + firstImagePath;
	}
	public void setFirstImagePath(String firstImagePath) {
		this.firstImagePath = firstImagePath;
	}
	
	public String getFirstPath() {
		return firstImagePath;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<ImageBean> getImages() {
		return images;
	}
	public void setImages(List<ImageBean> images) {
		this.images = images;
	}
	
	public void setDirName(String dirName) {
		this.dirName = dirName;
	}
	public String getDirName() {
		if (!TextUtils.isEmpty(dir) && dir.contains("/")) {
			int lastIndexOf = dir.lastIndexOf("/");
			return dir.substring(lastIndexOf + 1) + "(" + getCount() + ")";
		}
		return dir + "(" + getCount() + ")";
	}
	
	public String getDirTitle() {
		if (!TextUtils.isEmpty(dir) && dir.contains("/")) {
			int lastIndexOf = dir.lastIndexOf("/");
			return dir.substring(lastIndexOf + 1);
		}
		return dir;
	}
}