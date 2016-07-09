package com.yhjia.me.pagerfragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.yhjia.me.R;

import java.util.List;

/**
 * Created by jiayonghua on 16/7/9.
 * 自定义View的左右滚动和顶部导航
 */
public class PagerView extends LinearLayout implements ViewPager.OnPageChangeListener{
    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private TabPagerAdapter adapter;
    private OnPagerListener onPagerListener;
    public PagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        View view = LayoutInflater.from(context).inflate(R.layout.item_pager_fragment,null);
        tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        pager = (ViewPager) view.findViewById(R.id.pager);

        addView(view);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        bindListener(position);
    }

    @Override
    public void onPageSelected(int position) {
        bindListener(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 设置数据
     * @param titles 标题
     * @param fragments
     */
    public void setPagerData(FragmentManager manager,List<String> titles,List<Fragment> fragments) {
        adapter = new TabPagerAdapter(manager,titles,fragments);
        pager.setAdapter(adapter);
        int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);
        tabs.setViewPager(pager);
        tabs.setOnPageChangeListener(this);
    }

    public static interface OnPagerListener {
        void onPager(int position);
    }

    /**
     * 设置滚动监听
     */
    public void setOnPagerListener(OnPagerListener onPagerListener) {
        this.onPagerListener = onPagerListener;
    }

    private void bindListener(int position) {
        if (onPagerListener != null) {
            onPagerListener.onPager(position);
        }
    }
}
