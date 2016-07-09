package com.yhjia.me.pagerfragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by jiayonghua on 16/7/9.
 */
public class TabPagerAdapter extends FragmentPagerAdapter{
    private List<String> titles;
    private List<Fragment> fragments;

    public TabPagerAdapter(FragmentManager fm,List<String> titles,List<Fragment> fragments) {
        super(fm);
        this.titles = titles;
        this.fragments = fragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        if (titles == null) {
            return 0;
        }
        return titles.size();
    }
}
