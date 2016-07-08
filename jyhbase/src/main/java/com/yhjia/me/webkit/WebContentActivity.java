package com.yhjia.me.webkit;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yhjia.me.R;
import com.yhjia.me.activity.BaseActivity;
import com.yhjia.me.util.Prompt;
import com.yhjia.me.view.TopLayoutView;

/**
 * Created by jiayonghua on 16/6/15.
 */
public class WebContentActivity extends BaseActivity{
    public static final String EXTRA_WEB_URL = "webUrl";
    private TopLayoutView topLayout;
    private WebView webView;
    private String webUrl;
    private String htmlCode;

    @Override
    public int initLayoutID() {
        return R.layout.activity_webcontent;
    }

    @Override
    public void initView() {
        topLayout = findView(R.id.topLayout);
        webView = findView(R.id.webView);
        webUrl = getIntent().getStringExtra(EXTRA_WEB_URL);
        htmlCode = getIntent().getStringExtra("htmlCode");
        String showHint = getIntent().getStringExtra("showHint");
        if (!TextUtils.isEmpty(webUrl) && webUrl.startsWith("www")) {
            webUrl = "http://" + webUrl;
        }
        webView.getSettings().setJavaScriptEnabled(true);
        // 打开关闭使用缓存
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView.getSettings().setUseWideViewPort(true);
//        String userAgent = webView.getSettings().getUserAgentString();
//        userAgent = userAgent + " Android JiahaoApp(Quyun)";
//        webView.getSettings().setUserAgentString(userAgent);
//        ToastUtil.showToast(this, userAgent);
//        Log.e("Test", "userAgent 设置前 ：" + userAgent);
//        Log.e("Test","userAgent 设置后:" + webView.getSettings().getUserAgentString());
//		webView.getSettings().setBlockNetworkImage(true);
        //设置 缓存模式
        //webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启 DOM storage API 功能
        //webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                removeAllCookie();
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler, SslError error) {
                handler.proceed();
            }



            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//				webView.getSettings().setBlockNetworkImage(false);
                Prompt.dismissLoadingDialog();
                topLayout.setCommonTopLayout(TopLayoutView.Type.BACK_AND_TITLE, view.getTitle());
            }

        });
        webView.addJavascriptInterface(new LinkManager(), "app");

        Log.e("Test","webUrl:" + webUrl);
        if (!TextUtils.isEmpty(webUrl)) {
//            removeAllCookie();
            webView.loadUrl(webUrl);
        } else if (!TextUtils.isEmpty(htmlCode)) {
            //webView.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, failUrl);
//            removeAllCookie();
            webView.loadUrl(htmlCode);
        }
    }

    @Override
    public void initLogic() {
        topLayout.setOnClickListener(this);
        topLayout.setCommonTopLayout(TopLayoutView.Type.BACK_AND_TITLE, "");
        webUrl = getIntent().getStringExtra("webUrl");
        htmlCode = getIntent().getStringExtra("htmlCode");
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }


    class LinkManager {

        @JavascriptInterface
        public void test() {

        }
    }

    //清除所有cookie
    private void removeAllCookie()
    {
        CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(webView.getContext());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();

        String testcookie1 = cookieManager.getCookie(webUrl);
        cookieManager.removeAllCookie();
        cookieSyncManager.sync();

//		String testcookie2 = cookieManager.getCookie(webUrl);
    }
}
