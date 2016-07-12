package com.yhjia.me.listview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by jiayonghua on 16/7/12.
 */
public abstract  class PinnerSectionAdapter<T> extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter{

    public Context context;
    public List<T> list;

    public PinnerSectionAdapter(Context context,List<T> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getHolderView(position,convertView,parent);
    }

    /**
     *
     * @param viewType  type == 1 为标题的view
     * @return
     */
    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == 1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return getHolderItemViewType(position);
    }


    /**
     *
     * @param position
     * @return  1  为标题
     */
    public abstract int getHolderItemViewType(int position);

    /**
     * 用来设置 view
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public abstract View getHolderView(int position, View convertView, ViewGroup parent);
}
