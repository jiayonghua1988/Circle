package com.yhjia.me.photo.take;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;

import java.io.File;
import java.util.Date;

public class PhotoManager {

	public static final int CAMERA_REQ_CODE = 131;
	public static final int PHOTO_REQ_CODE = 132;
	public static final String CACHE_IMAGE_PATH = Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.google.base/cache/";
	private String name;

	public Intent getCameraIntent() {
		name = String.valueOf(System.currentTimeMillis())+".jpg";
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		creatDir2SDCard(CACHE_IMAGE_PATH);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(CACHE_IMAGE_PATH, name)));
		return intent;
	}

	public Intent getPhotoIntent() {
		name = String.valueOf(System.currentTimeMillis());
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
		return intent;
	}

	public Intent getCropIntent() {
		name = DateFormat.format("yyyy-MM-dd-hh-mm-ss", new Date()).toString();
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
		// intent.putExtra("crop", "true");
		// intent.putExtra("aspectX", 1);
		// intent.putExtra("aspectY", 1);
		// intent.putExtra("outputX", 114);
		// intent.putExtra("outputY", 114);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(CACHE_IMAGE_PATH, name)));
		return intent;
	}

	public String getCameraPath() {
		String path = CACHE_IMAGE_PATH + name;
		if(new File(path).isFile()){
			return path;
		}
		return null;
	}
	
	public void showChoose(final Activity act){
		AlertDialog.Builder builder=new AlertDialog.Builder(act);
		builder.setItems(new String[]{"拍照","相册"}, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				arg0.dismiss();
				if(arg1 == 0){
					act.startActivityForResult(getCameraIntent(), CAMERA_REQ_CODE);
				}else{
					act.startActivityForResult(getPhotoIntent(), PHOTO_REQ_CODE);
				}
			}
		});
		builder.show();
	}
	
	public String getPhotoPath(Intent data,Context context) {
		String photoPath = null;
		if(data == null){
			return photoPath;
		}
		String dataString = data.getDataString();
		if(dataString.startsWith("file://")){
			return data.getData().getPath();
		}else{
			Uri originalUri = data.getData();
			ContentResolver cr = context.getContentResolver();
			Cursor cursor = cr.query(originalUri, null, null, null, null);
			if(cursor.moveToFirst()){
				photoPath= cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
			}
			cursor.close();
		}
		return photoPath;
	}

	private String creatDir2SDCard(String dirPath) {
		File file = new File(dirPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		return dirPath;
	}

}