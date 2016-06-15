package com.yhjia.me.webkit;

import android.view.View;
import android.webkit.WebView;

import com.yhjia.me.R;
import com.yhjia.me.activity.BaseActivity;
import com.yhjia.me.view.TopLayoutView;

/**
 * Created by jiayonghua on 16/6/15.
 */
public class WebContentActivity extends BaseActivity{
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
}
