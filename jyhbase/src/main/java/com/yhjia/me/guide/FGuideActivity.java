package com.yhjia.me.guide;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.yhjia.me.R;
import com.yhjia.me.activity.BaseActivity;
import com.yhjia.me.adapter.FGuideAdapter;

import java.util.List;


/**
 * 应用引导页
 * 目标：1.实现该类就可以实现简单的引导功能
 *      2.布局定制
 *      3.有当前引导页的标示
 */
public abstract class FGuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener{
    private ViewPager viewPager;
    private FGuideAdapter adapter;

    // 底部的引导索引
    protected ViewGroup dotLayout;
    @Override
    public int initLayoutID() {
        return R.layout.activity_fguide;
    }

    @Override
    public void initView() {
        viewPager = findView(R.id.viewPager);
        viewPager.setOnPageChangeListener(this);
        dotLayout = findView(R.id.dotLayout);
    }

    @Override
    public void initLogic() {

    }

    /**
     * 设置引导页的页面
     * @param views
     */
    public void setDatas(List<View> views) {
        if (views != null && views.size() > 0) {
            adapter = new FGuideAdapter(views,this);
            viewPager.setAdapter(adapter);
        }
    }

    @Override
    public void onClick(View v) {

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        pageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 选中当前页的索引
     * @param position
     */
    public abstract void pageSelected(int position);

}
