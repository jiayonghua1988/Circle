package com.chiang.framework.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.TextView;

import com.yhjia.me.R;


public class LoadListView extends ListView implements OnScrollListener {
    private final GestureDetector mGestureDetector;
    View footer;
    int totalItemCount;
    int lastVisibleItem;
    boolean isLoading;
    ILoadListener iLoadListener;
    OnScrollListener onScrollListener;
    boolean isCanGetMore = true;

    public LoadListView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        initView(context);
        mGestureDetector = new GestureDetector(new YScrollDetector());
    }

    public LoadListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        initView(context);
        mGestureDetector = new GestureDetector(new YScrollDetector());
    }

    public LoadListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        initView(context);
        mGestureDetector = new GestureDetector(new YScrollDetector());
    }


    private void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        footer = inflater.inflate(R.layout.footer_layout, null);
        footer.findViewById(R.id.load_layout).setVisibility(View.INVISIBLE);
        this.addFooterView(footer, null, false);
        this.setOnScrollListener(this);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        // TODO Auto-generated method stub
        this.lastVisibleItem = firstVisibleItem + visibleItemCount;
        this.totalItemCount = totalItemCount;

        if (onScrollListener != null) {
            onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        Log.e("LoadListView", "scrollState=" + scrollState);
        // 滑到最后一条
        if (totalItemCount == lastVisibleItem && scrollState == SCROLL_STATE_IDLE) {
            if (!isLoading && isCanGetMore) {
                isLoading = true;
                footer.findViewById(R.id.load_layout).setVisibility(View.VISIBLE);
                if (iLoadListener != null) {
                    iLoadListener.onLoad();
                }
            } else {
                footer.findViewById(R.id.load_layout).setVisibility(View.INVISIBLE);
            }
        }
    }


    public void loadComplete() {
        isLoading = false;
        footer.findViewById(R.id.load_layout).setVisibility(View.INVISIBLE);
    }

    public void loadOver() {
        loadOver(null);
    }

    public void loadOver(String msg) {
        isLoading = false;
        footer.findViewById(R.id.load_layout).setVisibility(View.GONE);
        footer.findViewById(R.id.ll_message_over).setVisibility(View.VISIBLE);
        if (msg != null) {
            ((TextView) footer.findViewById(R.id.tv_message_over)).setText(msg);
        }
    }


    public void setGetMore(boolean isCanGetMore) {
        this.isCanGetMore = isCanGetMore;
    }


    public void setInterface(ILoadListener iLoadListener) {
        this.iLoadListener = iLoadListener;
    }

    public void setScrollListener(OnScrollListener listener) {
        this.onScrollListener = listener;
    }

    public interface ILoadListener {
        public void onLoad();
    }

    public interface OnScrollListener {
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount);
    }

    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (distanceY != 0 && distanceX != 0) {

            }
            if (Math.abs(distanceY) >= Math.abs(distanceX)) {
                return true;
            }
            return false;
        }
    }
}
