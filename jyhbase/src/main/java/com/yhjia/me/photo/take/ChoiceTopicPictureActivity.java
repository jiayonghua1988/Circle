package com.yhjia.me.photo.take;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.yhjia.me.activity.ChoicePictureActivity;
import com.yhjia.me.photo.browse.BrowserImageActivity;
import com.yhjia.me.photo.browse.ImageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiayonghua on 16/7/2.
 */
public class ChoiceTopicPictureActivity extends ChoicePictureActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

    }

    @Override
    public boolean isMoreChoicePhoto() {
        return true;
    }

    @Override
    protected void yulanListener() {
        List<ImageBean> imageBeans = adapter.getList();
        if (imageBeans != null && imageBeans.size() > 0) {
            ArrayList<String> newBeans = new ArrayList<String>();
            ArrayList<ImageBean> imageBeans2 = new ArrayList<ImageBean>();
            for (ImageBean imageBean : imageBeans) {
                if (imageBean.isSelected()) {
                    newBeans.add(imageBean.getImagePath() + "/" + imageBean.getName());
                    imageBeans2.add(imageBean);
                }
            }
            if (newBeans.size() < 1) {
                Toast.makeText(this, "请选择图片", Toast.LENGTH_LONG).show();
                return;
            }
            ImageBean iBean = new ImageBean();
            iBean.setImageBeans(imageBeans2);
            Intent intent = new Intent(this, BrowserImageActivity.class);
            intent.putExtra(BrowserImageActivity.TAG_IMGS, newBeans);
            intent.putExtra("iBean", iBean);
//			intent.putExtra(BrowserImageActivity.TAG_NAME, "认证图片");
            startActivity(intent);
        }
    }

    @Override
    protected void btnOkListener() {
        List<ImageBean> imageBeans = adapter.getList();
        ArrayList<ImageBean> imageBeans2 = new ArrayList<ImageBean>();
        for (ImageBean imageBean : imageBeans) {
            if (imageBean.isSelected()) {
                imageBeans2.add(imageBean);
            }
        }
        if (imageBeans2.size() < 1) {
            Toast.makeText(this, "请选择图片", Toast.LENGTH_LONG).show();
            return;
        }


//        if (ReleaseTopicActivity.choicePhotoListener != null) {
//            ReleaseTopicActivity.choicePhotoListener.choice(imageBeans2);
//        }
//
//        if(ReleaseDairyActivity.choicePhotoListener!=null){
//            ReleaseDairyActivity.choicePhotoListener.choice(imageBeans2);
//        }
//
//        if (CircleFragment.choicePhotoListener != null) {
//            CircleFragment.choicePhotoListener.choice(imageBeans2);
//        }
//
//        if (CircleNewFragment_1.choicePhotoListener != null) {
//            CircleNewFragment_1.choicePhotoListener.choice(imageBeans2);
//        }
//
//        if (CircleNewFragment_2.choicePhotoListener != null) {
//            CircleNewFragment_2.choicePhotoListener.choice(imageBeans2);
//        }
//
//        if (TopicDetailActivity.choicePhotoListener != null) {
//            TopicDetailActivity.choicePhotoListener.choice(imageBeans2);
//        }
//
//        if (ChattingActivity.choicePhotoListener != null) {
//            ChattingActivity.choicePhotoListener.choice(imageBeans2);
//        }
//
//        if (DiaryDetailActivity.choicePhotoListener != null) {
//            DiaryDetailActivity.choicePhotoListener.choice(imageBeans2);
//        }
        finish();

    }
}
