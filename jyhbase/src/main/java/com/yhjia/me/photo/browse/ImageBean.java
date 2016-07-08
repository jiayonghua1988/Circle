package com.yhjia.me.photo.browse;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.List;

public class ImageBean implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 图片名称
	 */
	private String name;
	/**
	 * 图片的路径
	 */
	private String imagePath;
	
	private boolean isSelected;
	private boolean isBtnTakePhoto;
	
	private int drawableId;
	
	private List<ImageBean> imageBeans;
	
	private boolean isTakePhoto;
	
	private boolean isAddPhoto;
	
	private Bitmap itemBitmap;
	private String uploadUri;
	
	
	
	
	
	
	
	
	public String getUploadUri() {
		return uploadUri;
	}
	public void setUploadUri(String uploadUri) {
		this.uploadUri = uploadUri;
	}
	public Bitmap getItemBitmap() {
		return itemBitmap;
	}
	public void setItemBitmap(Bitmap itemBitmap) {
		this.itemBitmap = itemBitmap;
	}
	public boolean isAddPhoto() {
		return isAddPhoto;
	}
	public void setAddPhoto(boolean isAddPhoto) {
		this.isAddPhoto = isAddPhoto;
	}
	public boolean isTakePhoto() {
		return isTakePhoto;
	}
	public void setTakePhoto(boolean isTakePhoto) {
		this.isTakePhoto = isTakePhoto;
	}
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	public boolean isBtnTakePhoto() {
		return isBtnTakePhoto;
	}
	public void setBtnTakePhoto(boolean isBtnTakePhoto) {
		this.isBtnTakePhoto = isBtnTakePhoto;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImagePath() {
		return "file://" + imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public int getDrawableId() {
		return drawableId;
	}
	public void setDrawableId(int drawableId) {
		this.drawableId = drawableId;
	}
	public List<ImageBean> getImageBeans() {
		return imageBeans;
	}
	public void setImageBeans(List<ImageBean> imageBeans) {
		this.imageBeans = imageBeans;
	}
	
	
	
}