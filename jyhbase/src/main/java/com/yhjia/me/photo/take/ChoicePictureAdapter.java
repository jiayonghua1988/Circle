package com.yhjia.me.photo.take;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yhjia.me.R;
import com.yhjia.me.adapter.YYBaseAdapter;

import java.util.List;

/**
 * Created by jiayonghua on 16/7/2.
 */
public class ChoicePictureAdapter extends YYBaseAdapter<ImageFloder> {

    public ChoicePictureAdapter(Context context, List<ImageFloder> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HolderView holderView = null;
        if (convertView == null) {
            holderView = new HolderView();
            convertView = inflater.inflate(R.layout.pop_choice_type_item, null);
            holderView.img = (ImageView) convertView.findViewById(R.id.img);
            holderView.dirName = (TextView) convertView.findViewById(R.id.dirName);
            convertView.setTag(holderView);
        } else {
            holderView = (HolderView) convertView.getTag();
        }
        ImageFloder imageFloder = list.get(position);
        imageLoader.displayImage(imageFloder.getFirstImagePath(), holderView.img, options);
        holderView.dirName.setText(imageFloder.getDirName());
        return convertView;
    }

    public class HolderView {
        public ImageView img;
        public TextView dirName;
    }

    @Override
    public int settingDefaultPicture() {
        return 0;
    }

}
