package com.yhjia.me.photo.take;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yhjia.me.R;
import com.yhjia.me.animation.FromBottomToTopPopup;
import com.yhjia.me.util.DiskCacheUtils;

import java.io.File;

/**
 * Created by jiayonghua on 16/6/21.
 * 调用系统相机 程序的入口
 */
public class ChoicePictureAction implements View.OnClickListener{
    private FromBottomToTopPopup popup;
    private Activity activity;
    private PhotoManager photoManage;
    private ImageLoader imageloader;
    private PictureCallbackListener listener;

    public ChoicePictureAction(Activity activity) {
        this.activity = activity;
        popup = new FromBottomToTopPopup(activity,getView(activity));
        photoManage = new PhotoManager();
        imageloader = ImageLoader.getInstance();
    }

    public void show() {
        popup.show();
    }

    public PhotoManager getPhotoManage() {
        return photoManage;
    }

    public void hide() {
        popup.hide();
    }

    private View getView(Activity activity) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_choice_picture, null);
        View takePhoto = view.findViewById(R.id.takePhoto);
        takePhoto.setOnClickListener(this);
        View choicePhoto = view.findViewById(R.id.choicePhoto);
        choicePhoto.setOnClickListener(this);
        View cancel = view.findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.takePhoto) { // 拍照
            activity.startActivityForResult(photoManage.getCameraIntent(), PhotoManager.CAMERA_REQ_CODE);
            popup.hide();
        } else if (id == R.id.choicePhoto) { // 选择图片
            Intent choicePhotoIntent = new Intent(activity,/**ChoicePictureActivity.class**/ChoiceMorePictureActivity.class);
            activity.startActivityForResult(choicePhotoIntent,998);
            popup.hide();
        } else if (id == R.id.cancel) { //  取消
            popup.hide();
        }
    }

    /**
     * 必须要在Activity 的onActivityResult方法中调用该方法
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == CropImageActivity.RESULT_CODE) {
            String headPath = "file://" + data.getStringExtra(CropImageActivity.PATH);

            File findInCache = DiskCacheUtils.findInCache(headPath, imageloader.getDiskCache());
            if(findInCache!=null){
                findInCache.delete();
            }
            if (listener != null) {
                listener.callback(headPath);
            }

        } else if (requestCode == PhotoManager.CAMERA_REQ_CODE) {
            String imagePath = photoManage.getCameraPath();
            if (listener != null) {
                listener.callback("file://" + photoManage.getCameraPath());
            }
//            toCropImage(imagePath);
        } else if (resultCode == 876) {
            String callBackUrl = data.getStringExtra("callBack");
            if (listener != null) {
                listener.callback(callBackUrl);
            }
        }
    }

    private void toCropImage(String imagePath) {
        if (!TextUtils.isEmpty(imagePath) && new File(imagePath).isFile()) {
            Intent intent = new Intent(activity, CropImageActivity.class);
            intent.putExtra(CropImageActivity.PATH, imagePath);
            activity.startActivityForResult(intent, 133);
        }
    }

    public void setPictureCallbackListener(PictureCallbackListener listener) {
        this.listener = listener;
    }

}
