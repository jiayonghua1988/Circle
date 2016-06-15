package com.yhjia.me.httpclient.async;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.yhjia.me.httpclient.core.HttpResponse;

import java.util.List;
import java.util.Map;


public abstract class ResponseHandler {
	private static final String LOG_TAG = ResponseHandler.class.getSimpleName();

	protected static final int SUCCESS_MESSAGE = 0;
	protected static final int FAILURE_MESSAGE = 1;
	protected static final int START_MESSAGE = 2;
	protected static final int FINISH_MESSAGE = 3;
	
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			ResponseHandler.this.handleMessage(msg);
		};
	};

	protected Context mContext;

	public abstract void onSuccess(int statusCode, Map<String, List<String>> headers,String response);

	public abstract void onFailure(int statusCode,Throwable error);
	
	public void onStart() {

	}

	public void onFinish() {

	}
	
	protected void handleMessage(Message message) {
		Object[] response;
		switch (message.what) {
		case SUCCESS_MESSAGE:
			response = (Object[]) message.obj;
			if (response != null && response.length >= 3) {
				onSuccess((Integer) response[0], (Map<String, List<String>>)response[1], (String) response[2]);
			} else {
				Log.e(LOG_TAG, "SUCCESS_MESSAGE didn't got enough params");
			}
			break;
		case FAILURE_MESSAGE:
			response = (Object[]) message.obj;
			if (response != null && response.length >= 4) {
				onFailure((Integer) response[0], (Throwable) response[1]);
			} else {
				Log.e(LOG_TAG, "FAILURE_MESSAGE didn't got enough params");
			}
			break;
		case START_MESSAGE:
			onStart();
			break;
		case FINISH_MESSAGE:
			onFinish();
			break;
		}
	}

	public final void sendSuccessMessage(int statusCode, Map<String, List<String>> headers, String response) {
		sendMessage(SUCCESS_MESSAGE, new Object[]{statusCode, headers, response});
	}

	public final void sendFailureMessage(int statusCode, String response, Throwable throwable) {
		sendMessage(FAILURE_MESSAGE, new Object[]{statusCode,response, throwable});
	}

	public void sendStartMessage() {
		sendMessage(START_MESSAGE, null);
	}

	public void sendFinishMessage() {
		sendMessage(FINISH_MESSAGE, null);
	}

	protected void postRunnable(Runnable runnable) {
		if (runnable != null) {
			handler.post(runnable);
		}
	}

	protected void sendMessage(int what, Object obj) {
		Message.obtain(handler, what, obj).sendToTarget();
	}

	public void sendResponseMessage(HttpResponse response) {
		if (response.getStatus() >= 300) {
			sendFailureMessage(response.getStatus(),response.getBodyAsString(),new Exception("status >= 300"));
		} else {
			sendSuccessMessage(response.getStatus(),response.getHeaders(),response.getBodyAsString());
		}
	}

	public void setContext(Context context) {
		mContext = context;
	}
	
	public Context getContext() {
		return mContext;
	}
}
