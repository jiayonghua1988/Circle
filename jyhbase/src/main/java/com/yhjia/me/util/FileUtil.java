package com.yhjia.me.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.HashMap;

public class FileUtil {

	public static boolean deleteFile(String stringPath){
		boolean deleteFlag = false;
		final File file = new File(stringPath);
		if(file.exists()){
			delete(file);
			deleteFlag = true;
		}else{
			Prompt.printLog("file is not exist");
		}
		return deleteFlag;
	}
	private static void delete(File file){
		if (file.isFile()) { // 判断是否是文件
			file.delete();
		} else if (file.isDirectory()) { // 否则如果它是一个目录
			File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
			for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
				delete(file);
			}
		}
	}

	public static boolean saveBitmap2SD(Bitmap bitmap,String path,String name){
		boolean isSave = false;
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File fileName = new File(path+"/"+name);
		try{
			OutputStream os  =  new FileOutputStream(fileName);
			bitmap.compress(CompressFormat.JPEG,100,os);
			os.flush();
			os.close();
			isSave = true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return isSave;
	}

	public static String downloadFile2SD(String url,String path) {
		return downloadFile2SD(url, path ,null);
	}

	public static String downloadFile2SD(String urlStr,String path,HashMap<String, String> heads) {

		String name = getFileName(urlStr);
		if(name == null){
			return name;
		}

		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		byte buffer[] = new byte[1024];
		File file = new File(path+name);
		FileOutputStream fileOutputStream = null;
		BufferedOutputStream bos = null;
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			if(heads !=null){
				HttpHelper.setGetHead(conn, heads);
			}
			conn.connect();
			InputStream inputStream = conn.getInputStream();
			fileOutputStream = new FileOutputStream(file);
			bos = new BufferedOutputStream(fileOutputStream);
			int length = 0;
			while ((length = inputStream.read(buffer)) != -1) {
				bos.write(buffer, 0, length);
			}
			bos.flush();
			return name;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fileOutputStream.close();
				bos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}


	public static String getFileName(String urlStr) {
		return toMD5(urlStr);
	}

	public static String toMD5(String str) {
		StringBuffer buf = new StringBuffer("");
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte b[] = md.digest();
			int i;
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return buf.toString().substring(8, 24);
	}

	public static boolean saveString2File(String str,String path,String name){
		boolean saveFlag = false;
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file;
		if(path.endsWith("/")){
			file = new File(path+name);
		}else{
			file = new File(path+""+name);
		}
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(file), true);
			pw.println(str);
			pw.close();
			saveFlag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return saveFlag;
	}

	public static void copyFile(File sourceFile, File targetFile) throws IOException {
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		try {
			inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

			outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			outBuff.flush();
		} finally {
			if (inBuff != null)
				inBuff.close();
			if (outBuff != null)
				outBuff.close();
		}
	}

	public static void saveIMG2Photo(Context context,Bitmap bitmap,String name) {
		if(bitmap == null || TextUtils.isEmpty(name)){
			return;
		}
		MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, name, name);
		context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri
				.parse("file://" + Environment.getExternalStorageDirectory())));
	}

	public static byte[] getBytesFromFile(String filePath) {
		try {
			File file = new File(filePath);
			if(file.isFile()){
				FileInputStream stream = new FileInputStream(file);
				ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
				byte[] b = new byte[1000];
				int n;
				while ((n = stream.read(b)) != -1) {
					out.write(b, 0, n);
				}
				stream.close();
				out.close();
				return out.toByteArray();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 根据Uri 获取 File的绝对路径
	 * @return
	 */
	public static String getRealFilePathFromUri(Context context,Uri uri) {
		if (null == uri) {
			return null;
		}
		String scheme = uri.getScheme();
		String data = null;
		if (scheme == null) {
			data = uri.getPath();
		} else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
			data = uri.getPath();
		} else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
			Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
			if ( null != cursor ) {
				if ( cursor.moveToFirst() ) {
					int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
					if ( index > -1 ) {
						data = cursor.getString( index );
					}
				}
				cursor.close();
			}
		}
		return data;
	}

	/**
	 * 通过String uri 获取 File对象 支持图片上传需求
	 * @param context
	 * @param uri
	 * @return
	 */
	public static File getFileFromStringUri(Context context,String uri) {
		Uri fileUri = Uri.parse(uri);
		String filePath = getRealFilePathFromUri(context,fileUri);
		if (!TextUtils.isEmpty(filePath)) {
			return new File(filePath);
		}
		return null;
	}
}
