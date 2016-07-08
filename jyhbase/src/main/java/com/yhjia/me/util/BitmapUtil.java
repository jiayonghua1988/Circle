package com.yhjia.me.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
	/** 
	 * ͼƬ���ع����� 
	 *  
	 * @author gaozhibin 
	 * 
	 */
	public class BitmapUtil { 
	    private static final String TAG = "BtimapUtil"; 
	      
	  
	    /** 
	     * �����ַ���ͼƬ�����ȴӱ��ػ�ȡ������û������������� 
	     *  
	     * @param url  ͼƬ��ַ 
	     * @param context ������ 
	     * @return ͼƬ 
	     */
	    public static Bitmap getBitmap(String url,Context context){ 
	        Log.e(TAG, "------url="+url); 
	        String imageName= url.substring(url.lastIndexOf("/")+1, url.length()); 
	        File file = new File(getPath(context),imageName); 
	        if(file.exists()){ 
	            Log.e(TAG, "getBitmap from Local"); 
	            return BitmapFactory.decodeFile(file.getPath()); 
	        } 
	        return getNetBitmap(url,file,context); 
	    } 
	    
//	    public static String getBitmapUri(String url,Context context) {
//	    	 Log.e(TAG, "------url="+url); 
//		        String imageName= url.substring(url.lastIndexOf("/")+1, url.length()); 
//		        File file = new File(getPath(context),imageName); 
//		        if(file.exists()){ 
//		            Log.e(TAG, "getBitmap from Local"); 
//		            return BitmapFactory.decodeFile(file.getPath()); 
//		        } 
//		        return getNetBitmap(url,file,context); 
//	    }
	      
	    /** 
	     * ��ݴ����list�б����ͼƬ��ַ����ȡ��Ӧ��ͼƬ�б� 
	     *  
	     * @param list  ����ͼƬ��ַ���б� 
	     * @param context ������ 
	     * @return ͼƬ�б� 
	     */
	    public static List<Bitmap> getBitmap(List<String> list,Context context){ 
	        List<Bitmap> result = new ArrayList<Bitmap>(); 
	        for(String strUrl : list){ 
	            Bitmap bitmap = getBitmap(strUrl,context); 
	            if(bitmap!=null){ 
	                result.add(bitmap); 
	            } 
	        } 
	        return result; 
	    } 
	  
	    /** 
	     * ��ȡͼƬ�Ĵ洢Ŀ¼������sd���������Ϊ ��/sdcard/apps_images/��Ӧ�ð���/cach/images/�� 
	     * û��sd�������Ϊ��/data/data/��Ӧ�ð���/cach/images/�� 
	     *  
	     * @param context ������ 
	     * @return ����ͼƬ�洢Ŀ¼ 
	     */
	    public static String getPath(Context context){ 
	        String path = null; 
	        boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); 
	        String packageName = context.getPackageName()+"/cach/images/"; 
	        if(hasSDCard){ 
	            path="/sdcard/apps_images/"+packageName; 
	        }else{ 
	            path="/data/data/"+packageName;      
	        }   
	        File file = new File(path); 
	        boolean isExist = file.exists(); 
	        if(!isExist){ 
	              
	            file.mkdirs(); 
	              
	        } 
	        return file.getPath(); 
	    } 
	  
	    /** 
	     * �������״̬�£�����ͼƬ�������ڱ��� 
	     *  
	     * @param strUrl ͼƬ��ַ 
	     * @param file ���ر����ͼƬ�ļ� 
	     * @param context  ������ 
	     * @return ͼƬ 
	     */
	    private static Bitmap getNetBitmap(String strUrl,File file,Context context) { 
	        Log.e(TAG, "getBitmap from net"); 
	        Bitmap bitmap = null; 
//	        if(NetUtil.isConnnected(context)){ 
	            try { 
	                URL url = new URL(strUrl); 
	                HttpURLConnection con = (HttpURLConnection) url.openConnection(); 
	                con.setDoInput(true); 
	                con.connect(); 
	                InputStream in = con.getInputStream(); 
	                bitmap = BitmapFactory.decodeStream(in); 
	                FileOutputStream out = new FileOutputStream(file.getPath()); 
	                bitmap.compress(Bitmap.CompressFormat.PNG,100, out); 
	                out.flush(); 
	                out.close(); 
	                in.close(); 
	            } catch (MalformedURLException e) { 
	                e.printStackTrace(); 
	            } catch (IOException e) { 
	                e.printStackTrace(); 
	            } finally{ 
	                  
	            }            
//	        } 
	        return bitmap; 
	    } 
	}
