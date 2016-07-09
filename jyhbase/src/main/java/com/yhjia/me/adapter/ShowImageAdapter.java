package com.yhjia.me.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yhjia.me.R;
import com.yhjia.me.bean.MessagePicture;

import java.util.List;

/**
 * Created by jiayonghua on 16/7/9.
 */
public class ShowImageAdapter extends YYBaseAdapter<MessagePicture>{

    private HolderView holderView;

    public ShowImageAdapter(Context context, List<MessagePicture> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holderView = new HolderView();
            convertView = inflater.inflate(initLayoutId() > 0 ?  initLayoutId() : R.layout.item_show_image, null);
            holderView.pic = (ImageView) convertView.findViewById(R.id.pic);
            convertView.setTag(holderView);
        } else {
            holderView = (HolderView) convertView.getTag();
        }

        imageLoader.displayImage(list.get(position).getPicUrl(), holderView.pic, options);
        return convertView;
    }

    public class HolderView {
        public ImageView pic;
    }

    @Override
    public int settingDefaultPicture() {
        return R.drawable.default_empty;
    }

    public int initLayoutId() {
        return 0;
    }

}
