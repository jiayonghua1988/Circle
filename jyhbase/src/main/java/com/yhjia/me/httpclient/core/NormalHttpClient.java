package com.yhjia.me.httpclient.core;

import java.net.HttpURLConnection;

import android.os.Build;

public class NormalHttpClient extends AbsHttpClient {

    public NormalHttpClient() {
        super();
    }

    static {
        disableConnectionReuseIfNecessary();
        // See http://code.google.com/p/basic-http-client/issues/detail?id=8
        // if (Build.VERSION.SDK_INT > 8)
        // ensureCookieManager();
    }

   // {"name","dfdssfs"}
 
   
    /**
     * Work around bug in {@link HttpURLConnection} on older versions of
     * Android.
     * http://android-developers.blogspot.com/2011/09/androids-http-clients.html
     */
    @SuppressWarnings("deprecation")
    private static void disableConnectionReuseIfNecessary() {
        // HTTP connection reuse which was buggy pre-froyo
        if (Integer.parseInt(Build.VERSION.SDK) < Build.VERSION_CODES.FROYO) {
            System.setProperty("http.keepAlive", "false");
        }
    }

    @Override
    public HttpResponse head(String path, ParameterList params) throws HttpRequestException{
        HttpMethod req = new HttpHead(path, params);
        return tryMany(req);
    }

    @Override
    public HttpResponse get(String path, ParameterList params) throws HttpRequestException {
        HttpMethod req = new HttpGet(path, params);
        return tryMany(req);
    }

    @Override
    public HttpResponse post(String path, ParameterList params) throws HttpRequestException {
        HttpMethod req = new HttpPost(path, params);
        return tryMany(req);
    }
   /* public HttpResponse post(String path,HeaderParameter[] header,ParameterList params)  {
    	
    }*/
    @Override
    public HttpResponse post(String path, String contentType, byte[] data)
            throws HttpRequestException {
        HttpMethod req = new HttpPost(path, null, contentType, data);
        return tryMany(req);
    }
/*
    public HttpResponse post(String path,ParameterList params, String body)
            throws HttpRequestException {
        HttpMethod req = new HttpPost(path, params,  RequestHandler.UTF8, data);
        return tryMany(req);
    }
*/
    @Override
    public HttpResponse put(String path, String contentType, byte[] data)
            throws HttpRequestException {
        HttpMethod req = new HttpPut(path, null, contentType, data);
        return tryMany(req);
    }

    @Override
    public HttpResponse delete(String path, ParameterList params) throws HttpRequestException {
        HttpMethod req = new HttpDelete(path, params);
        return tryMany(req);
    }
}
