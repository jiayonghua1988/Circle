package com.yhjia.me.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

public abstract class YYBaseAdapter<T> extends BaseAdapter {
    protected LayoutInflater inflater;
    protected List<T> list;
    protected ImageLoader imageLoader;
    protected Context mContext;
    protected DisplayImageOptions.Builder builder;
    protected DisplayImageOptions options;

    public YYBaseAdapter(Context context, List<T> list) {
        inflater = LayoutInflater.from(context);
        this.list = list;
        mContext = context;
        configLoadImage(settingDefaultPicture());
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

    public T getItemBean(int position) {
        return list.get(position);
    }

    public void addList(List<T> newList) {
        addOrResetData(newList, true);
    }

    public void addItem(T t) {
        list.add(t);
        notifyDataSetChanged();
    }

    public void addItem(T t, int position) {
        list.add(position, t);
        notifyDataSetChanged();
    }

    public void setList(List<T> initList) {
        addOrResetData(initList, false);
    }

    private void addOrResetData(List<T> newList, boolean isAdd) {
        if (newList != null) {
            if (isAdd) {
                list.addAll(newList);
            } else {
                this.list = newList;
            }
            newList = null;
        }
        notifyDataSetChanged();
    }

    public List<T> getList() {
        return list;
    }

    private void configLoadImage(int defaultDrawableId) {
        imageLoader = ImageLoader.getInstance();
        builder = new DisplayImageOptions.Builder();
        builder.cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .bitmapConfig(Bitmap.Config.RGB_565);//设置图片的解码类型//
        if (defaultDrawableId > 0) {
            builder.showImageOnFail(defaultDrawableId);
            builder.showImageForEmptyUri(defaultDrawableId);
        }
        options = builder.build();

    }

    public abstract int settingDefaultPicture();

    public void clearCache() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                ImageLoader.getInstance().clearMemoryCache();
//            }
//        }).start();
    }
}