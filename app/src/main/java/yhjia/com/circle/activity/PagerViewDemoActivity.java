package yhjia.com.circle.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.yhjia.me.pagerfragment.PagerView;
import com.yhjia.me.util.ToastUtil;
import java.util.ArrayList;
import java.util.List;
import yhjia.com.circle.fragment.Fragment1;
import yhjia.com.circle.fragment.Fragment2;
import yhjia.com.circle.fragment.Fragment3;
import yhjia.com.circle.fragment.Fragment4;

/**
 * Created by jiayonghua on 16/7/9.
 */
public class PagerViewDemoActivity extends FragmentActivity implements PagerView.OnPagerListener{
    private PagerView pagerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagerview);
        pagerView = (PagerView) findViewById(R.id.pagerView);
        pagerView.setOnPagerListener(this);

        List<String> titles = new ArrayList<String>();
        for (int i = 0;i < 4;i++) {
            titles.add("标题" + i);
        }

        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new Fragment1());
        fragments.add(new Fragment2());
        fragments.add(new Fragment3());
        fragments.add(new Fragment4());

        pagerView.setPagerData(getSupportFragmentManager(),titles,fragments);
    }

    @Override
    public void onPager(int position) {
        ToastUtil.showToast(String.valueOf(position));
    }


    public static void startPage(Activity activity) {
        Intent intent = new Intent(activity,PagerViewDemoActivity.class);
        activity.startActivity(intent);
    }
}
