package com.yhjia.me.photo.take;

import android.content.Intent;
import android.widget.Toast;

import com.yhjia.me.activity.ChoicePictureActivity;
import com.yhjia.me.logger.LogUtil;
import com.yhjia.me.photo.browse.BrowserImageActivity;
import com.yhjia.me.photo.browse.ImageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiayonghua on 16/7/11.
 * 选择多张图片 支持预览功能
 */
public class ChoiceMorePictureActivity extends ChoicePictureActivity{

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

        ImageBean imageBean = imageBeans2.get(0);
        String path = imageBean.getImagePath() + "/" + imageBean.getName();
        LogUtil.i("选择图片 path:" + path);
        Intent intent = getIntent();
        intent.putExtra("callBack",path);
        setResult(876,intent);
        finish();

    }
}
