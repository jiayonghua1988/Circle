package com.yhjia.me.httpclient.async;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.yhjia.me.httpclient.core.HttpDelete;
import com.yhjia.me.httpclient.core.HttpGet;
import com.yhjia.me.httpclient.core.HttpHead;
import com.yhjia.me.httpclient.core.HttpMethod;
import com.yhjia.me.httpclient.core.HttpPost;
import com.yhjia.me.httpclient.core.HttpRequestException;
import com.yhjia.me.httpclient.core.HttpResponse;
import com.yhjia.me.httpclient.core.NormalHttpClient;
import com.yhjia.me.httpclient.core.ParameterList;

public class LSHttpClient {
	public static final String TAG = LSHttpClient.class.getSimpleName();
	private NormalHttpClient httpClient;

	public LSHttpClient() {
		httpClient = new NormalHttpClient();
	}
	
	 /**
     * Perform a HTTP GET request without any parameters and track the Android Context which
     * initiated the request.
     *
     * @param url             the URL to send the request to.
     * @param responseHandler the response handler instance that should handle the response.
     */
    public void get(Context context,String url, ResponseHandler responseHandler) {
        get(context,url, null, responseHandler);
    }
    
    /**
     * Perform a HTTP GET request with parameters.
     *
     * @param url             the URL to send the requesØt to.
     * @param params          additional GET parameters to send with the request.
     * @param responseHandler the response handler instance that should handle the response.
     */
    public void get(Context context,String url, ParameterList params, ResponseHandler responseHandler) {
    	 sendRequest(context,new HttpGet(url, params), responseHandler);
    }

    /**
     * Perform a HTTP POST request with parameters.
     *
     * @param url             the URL to send the request to.
     * @param responseHandler the response handler instance that should handle the response.
     */
    public void post(Context context,String url, ResponseHandler responseHandler) {
        post(context,url, null, responseHandler);
    }

    /**
     * Perform a HTTP POST request with parameters.
     *
     * @param url             the URL to send the request to.
     * @param params          additional POST parameters or files to send with the request.
     * @param responseHandler the response handler instance that should handle the response.
     */
    public void post(Context context,String url, ParameterList params, ResponseHandler responseHandler) {
    	sendRequest(context,new HttpPost(url,params), responseHandler);
    }
    
    /**
     * Perform a HTTP POST request with parameters.
     *
     * @param url             the URL to send the request to.
     * @param contentType     parameters mime type
     * @param data			  POST parameters to send with the request.
     * @param responseHandler the response handler instance that should handle the response.
     */
    public void post(Context context,String url, String contentType, byte[] data, ResponseHandler responseHandler) {
    	sendRequest(context,new HttpPost(url, null, contentType, data), responseHandler);
    }
    
    /**
     * Perform a HTTP DELETE request.
     *
     * @param url             the URL to send the request to.
     * @param responseHandler the response handler instance that should handle the response.
     */
    public void delete(Context context,String url, ResponseHandler responseHandler) {
    	delete(context,url, null,responseHandler);
    }
	
    /**
     * Perform a HTTP DELETE request.
     *
     * @param url             the URL to send the request to.
     * @param params          additional DELETE parameters or files to send along with request
     * @param responseHandler the response handler instance that should handle the response.
     */
    public void delete(Context context,String url,ParameterList params, ResponseHandler responseHandler) {
    	sendRequest(context,new HttpDelete(url, params), responseHandler);
    }
    
    /**
     * Perform a HTTP HEAD request, without any parameters.
     *
     * @param url             the URL to send the request to.
     * @param responseHandler the response handler instance that should handle the response.
     * @return RequestHandle of future request process
     */
    public void head(Context context,String url, ResponseHandler responseHandler) {
        head(context,url, null, responseHandler);
    }

    /**
     * Perform a HTTP HEAD request with parameters.
     *
     * @param url             the URL to send the request to.
     * @param params          additional HEAD parameters to send with the request.
     * @param responseHandler the response handler instance that should handle the response.
     */
    public void head(Context context,String url, ParameterList params, ResponseHandler responseHandler) {
    	sendRequest(context,new HttpHead(url, params), responseHandler);
    }

    /**
     * execute request
     * @param method
     * @param responseHandler
     */
	public void sendRequest(Context context,HttpMethod method,ResponseHandler responseHandler) {
		HttpUtils.addPublicHeader(method.getParams());
		//HttpUtils.addPublicHeader(parameterList, context)
		if (context != null && responseHandler != null) {
			responseHandler.setContext(context);
		}
		//if (IApplication.debug) {
			Log.d(TAG, "url: " + method.getRequestUrl());
			if (method.getParams() != null) {
				StringBuilder sb = new StringBuilder();
				for (ParameterList.Parameter parameter : method.getParams()) {
					if (parameter instanceof ParameterList.HeaderParameter) {
						if (sb.length() > 0) {
							sb.append("&");
						}
						sb.append(parameter.name + "=" + ((ParameterList.HeaderParameter)parameter).value);
					}
				}
				Log.d(TAG, "header: " + sb.toString());				
				Log.d(TAG, "body: " + method.getParams().urlEncode());
			}
		//}
		
		if (!isConnect(context)) {
//			if (context instanceof BaseActivity) {
//				DialogUtil.showHintDialog(context, "球星！你的网络不稳定哦～");
//			}
			return;
		}
		
		new AsyncTask<Object, Void, HttpResponse>() {
			@Override
			protected HttpResponse doInBackground(Object... params) {
				HttpResponse response = null;
				ResponseHandler responseHandler = (ResponseHandler) params[1];
				try {
					if (responseHandler != null) {
						responseHandler.sendStartMessage();
					}
					response =  httpClient.tryMany((HttpMethod)params[0]);
					if (responseHandler != null) {
						responseHandler.sendResponseMessage(response);
					}
				} catch (HttpRequestException e) {
					if (responseHandler != null) {
						responseHandler.sendFailureMessage(response != null ? response.getStatus() : 0,response != null ? response.getBodyAsString() : null, e);
					}
					e.printStackTrace();
				} finally {
					if (responseHandler != null) {
						responseHandler.sendFinishMessage();
					}
				}
				return response;
			}
		}.execute(method,responseHandler);
	}
	
	
	
	
	public static boolean isConnect(Context context) { 


        // 获取手机�?有连接管理对象（包括对wi-fi,net等连接的管理�? 

    try { 

        ConnectivityManager connectivity = (ConnectivityManager) context 

                .getSystemService(Context.CONNECTIVITY_SERVICE); 

        if (connectivity != null) { 


            // 获取网络连接管理的对�? 

            NetworkInfo info = connectivity.getActiveNetworkInfo(); 


            if (info != null&& info.isConnected()) { 

                // 判断当前网络是否已经连接 

                if (info.getState() == NetworkInfo.State.CONNECTED) { 

                    return true; 

                } 

            } 

        } 

    } catch (Exception e) { 

// TODO: handle exception 

    Log.v("error",e.toString()); 

} 

        return false; 

    } 
}
