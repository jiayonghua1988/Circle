package com.yhjia.me.httpclient.async;

import android.util.Log;
import android.widget.Toast;

import com.yhjia.me.app.IApplication;
import com.yhjia.me.util.Prompt;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public abstract class TLSResponseHander extends ResponseHandler {

	@Override
	public void onSuccess(int statusCode, Map<String, List<String>> headers,
			String response) {
		Prompt.dismissLoadingDialog();
		Log.e("TLSResponseHander   .. success..", response);
		try {
			JSONObject jsonObject = new JSONObject(response);
			String success = jsonObject.getString("success");
			if ("true".equals(success)) {
				success(statusCode, headers, response);
			} else {
				String errorMsg = jsonObject.getString("errorMsg");
				Toast.makeText(IApplication.getInstance().getApplicationContext(),errorMsg, Toast.LENGTH_LONG).show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}


	}

	@Override
	public void onFailure(int statusCode, Throwable error) {
		Log.e("TLSResponseHander   .. failre..", error.getMessage());
		Prompt.dismissLoadingDialog();
		Toast.makeText(IApplication.getInstance().getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

	}
	
	public abstract void success(int statusCode, Map<String, List<String>> headers,String response);
	

}
