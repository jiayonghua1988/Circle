package com.yhjia.me.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;

import com.handmark.pulltorefresh.library.PullToRefreshScrollView;


/**
 * Created by pandayu on 16/3/30.
 */
public class PullRefreshScrollView extends PullToRefreshScrollView implements PullToRefreshScrollView.PullScrollListener {

    public PullRefreshScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPullScrollListener(this);
    }

    @Override
    public void stopScroll() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                ImageLoader.getInstance().pause();
            }
        }, 100);

    }

    @Override
    public void startScroll() {
//        ImageLoader.getInstance().stop();
    }

}
