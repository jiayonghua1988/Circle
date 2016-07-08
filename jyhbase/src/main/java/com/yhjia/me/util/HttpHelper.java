package com.yhjia.me.util;

import android.net.Uri;

import com.google.gson.Gson;
import com.yhjia.me.voice.Request;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;

public class HttpHelper {

	private static final Gson gson = new Gson();

	private static final int TIME_OUT_VALUE = 20000;

	public static String post(Request request) throws ClientProtocolException, IOException {
		HttpPost httpPost = new HttpPost(request.getUrl());
		setPostHead(httpPost, request.getHeads());
		if (request.getParams() != null) {
			String json = gson.toJson(request.getParams());
			httpPost.setEntity(new StringEntity(json, HTTP.UTF_8));
			Prompt.printLog("Request post json:" + json);
		}
		
		return HttpClientExcute(httpPost);
	}

	public static String HttpClientExcute(HttpPost httpPost) throws ClientProtocolException, IOException {
		DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
		HttpParams params = defaultHttpClient.getParams();
		params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, TIME_OUT_VALUE);
        params.setParameter(CoreConnectionPNames.SO_TIMEOUT, TIME_OUT_VALUE);
		HttpResponse httpResponse = defaultHttpClient.execute(httpPost);
		HttpEntity httpEntity = httpResponse.getEntity();
		String str = GZipUtil.decompressGZip(httpEntity.getContent());
		Prompt.printLog("response post json:" + str);
		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			return str;
		}
		return null;
	}

	public static void setPostHead(HttpPost httpPost, HashMap<String, String> heads) {
		if (heads != null) {
			for (Entry<String, String> entry : heads.entrySet()) {
				Prompt.printLog("Requst Post Heads:Key: " + entry.getKey() + "  Value:" + entry.getValue());
				httpPost.setHeader(entry.getKey(), entry.getValue());
			}
		}
	}

	public static String downloadUrl(Request request,String method) throws IOException {
		InputStream is = null;
		try {
			URL url = new URL(jointGetUrl(request));
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10000 /* milliseconds */);
			conn.setConnectTimeout(TIME_OUT_VALUE /* milliseconds */);
			conn.setRequestMethod(method);
			setGetHead(conn, request.getHeads());
			conn.setDoInput(true);
			// Starts the query
			conn.connect();

			int response = conn.getResponseCode();
			Prompt.printLog("Reponse get code:" + response);
			is = conn.getInputStream();
			String content = GZipUtil.decompressGZip(is);
			Prompt.printLog("response get json:" + content);
			return content;

		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

	public static void setGetHead(HttpURLConnection conn, HashMap<String, String> heads) {
		if (heads != null) {
			for (Entry<String, String> entry : heads.entrySet()) {
				conn.setRequestProperty(entry.getKey(), entry.getValue());
				Prompt.printLog("Requst Get Heads:Key: " + entry.getKey() + "  Value:" + entry.getValue());
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static String jointGetUrl(Request request) {
		StringBuilder builder = new StringBuilder();
		builder.append(request.getUrl());
		Object params = request.getParams();
		if (params != null && params instanceof HashMap) {
			builder.append("&");
			HashMap<String, String> hashMap = (HashMap<String, String>) params;
			for (Entry<String, String> entry : hashMap.entrySet()) {
				builder.append(entry.getKey());
				builder.append("=");
				builder.append(Uri.encode(entry.getValue()));
				builder.append("&");
			}
			if (builder.lastIndexOf("&") == (builder.length() - 1)) {
				builder.deleteCharAt(builder.length() - 1);
			}
		}
		String joinUrl = builder.toString();
		Prompt.printLog("request get joinUrl:" + joinUrl);
		return joinUrl;
	}

//	public static String delete(Request request) throws IOException {
//		InputStream is = null;
//		try {
//			URL url = new URL(jointGetUrl(request));
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			conn.setReadTimeout(10000 /* milliseconds */);
//			conn.setConnectTimeout(TIME_OUT_VALUE /* milliseconds */);
//			conn.setRequestMethod("DELETE");
//			setGetHead(conn, request.getHeads());
//			conn.setDoInput(true);
//			// Starts the query
//			conn.connect();
//			int response = conn.getResponseCode();
//			Prompt.printLog("RequestMethod = " + conn.getRequestMethod());
//			Prompt.printLog("Reponse delete code:" + response);
//			is = conn.getInputStream();
//
//			// *****判断是否需要GZIP解压缩*****/
//			String content = GZipUtil.decompressGZip(is);
//			Prompt.printLog("response delete json:" + content);
//			return content;
//
//			// 在app完成使用以后要确认关闭io流
//		} finally {
//			if (is != null) {
//				is.close();
//			}
//		}
//	}

	public static Gson getGson() {
		return gson;
	}

}