package com.yhjia.me.util;

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

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

public class FileUtil {

	/**
	 * ɾ���ļ�or��Ŀ¼�µ��ļ�
	 * @param stringPath �ļ���������ļ���
	 * @return true �ɹ�
	 */
	public static boolean deleteFile(String stringPath){
		boolean deleteFlag = false;
		if(!SdCardUtil.sdCardMounted()) return deleteFlag; //sd��������
		final File file = new File(stringPath);
		if(file.exists()){
			delete(file);
			deleteFlag = true;
		}else{
			//Prompt.printLog("file is not exist");
		}
		return deleteFlag;
	}
	/**
	 * ɾ���ļ�
	 */
	private static void delete(File file){
		if (file.isFile()) { // �ж��Ƿ����ļ�
			file.delete(); 
		} else if (file.isDirectory()) { // �����������һ��Ŀ¼
			File files[] = file.listFiles(); // ����Ŀ¼�����е��ļ� files[];
			for (int i = 0; i < files.length; i++) { // ����Ŀ¼�����е��ļ�
				delete(file);
			}
		}
	}
	
	/**
	 * ��bitmap����Ϊjpg�ļ���SD��ָ��λ��
	 * @param bitmap λͼ
	 * @param path ����·��
	 * @param name ������ļ����,�ļ���ư��׺��
	 */
	public static boolean saveBitmap2SD(Bitmap bitmap,String path,String name){
		boolean isSave = false;
		if(!SdCardUtil.sdCardMounted()) return isSave; //sd��������
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
	
	/**
	 * ���������ļ�������ʹ����������
	 * @param url �ļ���url
	 * @param path �ļ������·��,·����β������/
	 * @return �����ļ��������ƣ������null���ʧ��
	 */
//	public static String downloadFile2SD(String url,String path) {
//		return downloadFile2SD(url, path ,null);
//	}
	
	/**
	 * ���������ļ�������ʹ����������
	 * @param urlStr �ļ���url,��ƻ���MD5���ܣ�����getFileName��ȡ
	 * @param path �ļ������·��,·����β������/
	 * @return �����ļ��������ƣ������null���ʧ��
	 */
//	public static String downloadFile2SD(String urlStr,String path,HashMap<String, String> heads) {
//		if(!SdCardUtil.sdCardMounted()){
//			 return null;//sd��������
//		}
//		
//		String name = getFileName(urlStr);
//		if(name == null){
//			return name;//�����ļ���ƴ���
//		}
//		
//		File dir = new File(path);
//		if (!dir.exists()) {
//			dir.mkdirs();
//		}
//		byte buffer[] = new byte[1024];
//		File file = new File(path+name);
//		FileOutputStream fileOutputStream = null;
//		BufferedOutputStream bos = null;
//		try {
//			URL url = new URL(urlStr);
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			if(heads !=null){//�Ƿ�����ͷ��Ϣ
//				HttpHelper.setGetHead(conn, heads);	
//			}
//			conn.connect();
//			InputStream inputStream = conn.getInputStream();
//			fileOutputStream = new FileOutputStream(file);
//			bos = new BufferedOutputStream(fileOutputStream);
//			int length = 0;
//			while ((length = inputStream.read(buffer)) != -1) {
//				bos.write(buffer, 0, length);
//			}
//			bos.flush();
//			return name;
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				fileOutputStream.close();
//				bos.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		return null;
//	}
	
	
	/**
	 * ʹ��MDC����ļ����
	 */
	public static String getFileName(String urlStr) {
		return toMD5(urlStr);
	}

	/**
	 * ���16λ����ļ����
	 */
	public static String toMD5(String str) {
		StringBuffer buf = new StringBuffer("");
        try {
             //���ʵ��ָ��ժҪ�㷨�� MessageDigest ����
             MessageDigest md = MessageDigest.getInstance("MD5");    
             //ʹ��ָ�����ֽ��������ժҪ��
             md.update(str.getBytes());
             //ͨ��ִ���������֮������ղ�����ɹ�ϣ���㡣
             byte b[] = md.digest();
             //��ɾ����md5���뵽buf����
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
	
	/**
	 * �����ַ��ļ���
	 * @param str �ַ�
	 * @param path ·��
	 * @param name �ļ����
	 * @return true �ɹ�
	 */
	public static boolean saveString2File(String str,String path,String name){
		boolean saveFlag = false;
		if(!SdCardUtil.sdCardMounted()) return saveFlag; //sd��������
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
	
	/**
	 * �����ļ� 
	 * @param sourceFile
	 * @param targetFile
	 */
    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // �½��ļ���������������л���
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // �½��ļ��������������л���
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // ��������
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // ˢ�´˻���������
            outBuff.flush();
        } finally {
            // �ر���
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }
    
    /**
     * ����ͼƬ�����
     * @param bitmap ͼƬ
     * @param name ͼƬ��ƣ����׺��һ����jpg
     */
    public static void saveIMG2Photo(Context context,Bitmap bitmap,String name) {
		if(bitmap == null || TextUtils.isEmpty(name)){
			return;
		}
		MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, name, name);
		context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri
				.parse("file://" + Environment.getExternalStorageDirectory())));
	}

	/**
	 * ���ļ�ת��byte���鷵��
	 */
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
}
