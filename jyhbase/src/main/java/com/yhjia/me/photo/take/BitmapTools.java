package com.yhjia.me.photo.take;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.ImageView;

import com.yhjia.me.photo.browse.ImageBean;
import com.yhjia.me.util.ToastUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BitmapTools {
	private Map<String, String> map;
	private List<ImageFloder> imageFloders;
	private List<ImageBean> imageBeans;
	private String firstPath;
	private int total;
	
	public Bitmap getScalePic(String path,int scale) {
		
	    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
	    bmOptions.inJustDecodeBounds = false;
	    bmOptions.inPreferredConfig = Bitmap.Config.RGB_565;
	    bmOptions.inSampleSize = scale;
	    bmOptions.inPurgeable = true;
	    
	    Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);
	    bitmap = photoRotate(bitmap, path);
	    return bitmap;
	}
	
	public Bitmap getScalePic(String mCurrentPhotoPath,ImageView mImageView) {
	    int targetW = mImageView.getWidth();
	    int targetH = mImageView.getHeight();
	  
	    return getScalePic(mCurrentPhotoPath, targetW, targetH);
	}
	
	public Bitmap getScalePic(String mCurrentPhotoPath,int width,int height) {
	    // Get the dimensions of the bitmap
	    BitmapFactory.Options bmOptions = getBitmapSize(mCurrentPhotoPath);
	    int photoW = bmOptions.outWidth;
	    int photoH = bmOptions.outHeight;
	  
	    // Determine how much to scale down the image
	    int scaleFactor = Math.min(photoW/width, photoH/height);
	  
	    // Decode the image file into a Bitmap sized to fill the View
	    bmOptions.inJustDecodeBounds = false;
	    bmOptions.inPreferredConfig = Bitmap.Config.RGB_565;
	    bmOptions.inSampleSize = scaleFactor;
	    bmOptions.inPurgeable = true;
	  
	    Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
	    return bitmap;
	}
	
	public static BitmapFactory.Options getBitmapSize(String path){
		 // Get the dimensions of the bitmap
	    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
	    bmOptions.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(path, bmOptions);
	    
	    return bmOptions;
	}
	
	public Bitmap photoRotate(Bitmap bitmap,String path){
		int degree = getPicRotate(path);
		if(degree!=0){
			Matrix m = new Matrix();
			int width = bitmap.getWidth();  
			int height = bitmap.getHeight();  
			m.setRotate(degree); // 旋转angle度  '
			Bitmap newBit = Bitmap.createBitmap(bitmap, 0, 0, width, height,m, true);// 从新生成图片  
			bitmap.recycle();
			bitmap = null;
			return newBit;
		}
		return bitmap;
	}
	

	public static int getPicRotate(String path) {
		int degree  = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}
	
	
	
	public Bitmap getComScalePic(Bitmap image,int scale,int quality) {
		Bitmap bitmap = null;
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, quality, baos);
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		
		 BitmapFactory.Options bmOptions = new BitmapFactory.Options();
	     bmOptions.inJustDecodeBounds = false;
	     bmOptions.inPreferredConfig = Bitmap.Config.RGB_565;
	     bmOptions.inSampleSize = scale;
	     bmOptions.inPurgeable = true;
		
		bitmap = BitmapFactory.decodeStream(bais, null, bmOptions);
		
		return bitmap;
	}
	

	
	/**
	 * bitmap转byte
	 */
	public static byte[] bitmap2Bytes(Bitmap bm) {
		if (bm == null) {
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		byte[] byteArray = baos.toByteArray();
		try {
			baos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return byteArray;
	}
	
	public Bitmap getComPic(Bitmap image,int quality) {
		Bitmap bitmap = null;
		try{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			image.compress(Bitmap.CompressFormat.JPEG, quality, baos);
			
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			bitmap = BitmapFactory.decodeStream(bais, null, null);
			bais.close();
			baos.close();
		}catch(IOException io){
			io.printStackTrace();
		}
		return bitmap;
	}
	
	
	/**
	 * 获取手机上的所有图片
	 */
	public List<ImageFloder> getDeviceImages(final Activity activity,final Handler handler) {
		imageFloders = new ArrayList<ImageFloder>();
		map = new HashMap<String, String>();
		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			ToastUtil.showToast("暂无外部存储");
			return imageFloders; 
		}
		firstPath = null;
		final List<ImageBean> allBeans = new ArrayList<ImageBean>();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				ContentResolver mContentResolver = activity.getContentResolver();
				//查询只有jpeg 和png的图片""
				Cursor cursor = mContentResolver.query(mImageUri, null, MediaStore.Images.Media.MIME_TYPE + " = ? or "
						+ MediaStore.Images.Media.MIME_TYPE + " = ? ", new String [] {"image/jpeg","image/png"},
						MediaStore.Images.Media.DATE_MODIFIED);
				while (cursor.moveToNext()) {
					// 获取图片路径
					String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
					if (TextUtils.isEmpty(firstPath)) {
						firstPath = path;
					}
					// 获取文件目录
					File parentFile = new File(path).getParentFile();
					if (parentFile == null) {
						continue;
					}
					ImageFloder imageFloder = null;
					// 文件目录
					String dirPath = parentFile.getAbsolutePath();
					if (map.containsKey(dirPath)) {
						continue;
					} else {
//						Log.e("Test", "路径=" + dirPath);
						map.put(dirPath, dirPath);
						imageFloder = new ImageFloder();
						imageFloder.setDir(dirPath);
						imageFloder.setFirstImagePath(path);
					}
					imageBeans = new ArrayList<ImageBean>();
					String [] picArray = parentFile.list(new FilenameFilter() {
						
						@Override
						public boolean accept(File dir, String filename) {
							if (filename.endsWith(".jpg") || filename.endsWith(".png") || filename.endsWith(".jpeg")) {
//								Log.e("Test", "目录下的图片路径:" + dir.getAbsolutePath() + "/" + filename);
								ImageBean imageBean = new ImageBean();
								imageBean.setName(filename);
								imageBean.setImagePath(dir.getAbsolutePath());
								allBeans.add(imageBean);
								imageBeans.add(imageBean);
								return true;
							} else {
								return false;
							}
						}
					});
					int picSize = 0;
					if (picArray != null) {
						picSize = picArray.length;
					}
					total += picSize;
					imageFloder.setImages(imageBeans);
					imageFloder.setCount(picSize);
					imageFloders.add(imageFloder);
				}
				ImageFloder allFloder = new ImageFloder();
				allFloder.setDirName("全部图片");
				allFloder.setCount(total);
				allFloder.setDir("全部图片");
				allFloder.setFirstImagePath(firstPath);
				allFloder.setImages(allBeans);
				imageFloders.add(0, allFloder);
				
				if (handler != null) {
					handler.sendEmptyMessage(50);
				}
			}
		}).start();
		return imageFloders;
	}
	
	
	
}