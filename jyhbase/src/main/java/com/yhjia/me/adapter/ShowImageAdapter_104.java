package com.yhjia.me.adapter;

import android.content.Context;

import com.yhjia.me.R;
import com.yhjia.me.bean.MessagePicture;

import java.util.List;

/**
 * Created by jiayonghua on 16/7/9.
 */
public class ShowImageAdapter_104 extends ShowImageAdapter {

    public ShowImageAdapter_104(Context context, List<MessagePicture> list) {
        super(context, list);
    }

    @Override
    public int initLayoutId() {
        return R.layout.item_show_image_4;
    }

}
