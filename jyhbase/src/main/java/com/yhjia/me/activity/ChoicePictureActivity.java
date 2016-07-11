package com.yhjia.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.yhjia.me.R;
import com.yhjia.me.logger.LogUtil;
import com.yhjia.me.photo.browse.ImageBean;
import com.yhjia.me.photo.take.BitmapTools;
import com.yhjia.me.photo.take.ChoiceClassifyActivity;
import com.yhjia.me.photo.take.ChoicePictureGridAdapter;
import com.yhjia.me.photo.take.CropImageActivity;
import com.yhjia.me.photo.take.ImageFloder;
import com.yhjia.me.photo.take.PhotoManager;
import com.yhjia.me.view.TopLayoutView;

import java.io.File;
import java.util.List;

/**
 * Created by jiayonghua on 16/6/30.
 */
public class ChoicePictureActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    private GridView gridView;
    private View view;
    private BitmapTools bitmapTools;
    protected ChoicePictureGridAdapter adapter;
    private List<ImageFloder> imageFloders;
    private PhotoManager photoManage;
    private ImageLoader imageLoader;
    private TopLayoutView topLayout;
    private View bottomlayout;
    private View yulan;
    private View btnOk;
    private TextView labelIndex;
    public static  int MAX_PHOTO = 9;
    public static Activity activity;
    private boolean isMore = false;

    @Override
    public int initLayoutID() {
        return R.layout.activity_choice_picture;
    }

    @Override
    public void initView() {
        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setOnItemClickListener(this);
        topLayout = (TopLayoutView) findViewById(R.id.topLayout);
        topLayout.setCommonTopLayout(TopLayoutView.Type.ALL, "相机胶卷", "取消");
        topLayout.setOnClickListener(this);
        bottomlayout = findViewById(R.id.bottomlayout);
        yulan = findViewById(R.id.yulan);
        btnOk = findViewById(R.id.btnOk);
        yulan.setOnClickListener(this);
        btnOk.setOnClickListener(this);
        labelIndex = (TextView) findViewById(R.id.labelIndex);
    }

    @Override
    public void initLogic() {
        MAX_PHOTO = getIntent().getIntExtra("count", 9);
        bitmapTools = new BitmapTools();
        photoManage = new PhotoManager();
        imageLoader = ImageLoader.getInstance();
        isMore = isMoreChoicePhoto();
        if (!isMore) {
            isMore = getIntent().getBooleanExtra("isMore", false);
        }
        ImageFloder imageFloder = (ImageFloder) getIntent().getSerializableExtra("pics");
        if (imageFloder != null) {
            adapter = new ChoicePictureGridAdapter(this, imageFloder.getImages(),isMore,handler2);
            gridView.setAdapter(adapter);
        } else {
            imageFloders = bitmapTools.getDeviceImages(this,handler);
        }
        bottomlayout.setVisibility(isMoreChoicePhoto() ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.leftBtn) {
            Intent intent = new Intent(this,ChoiceClassifyActivity.class);
            intent.putExtra("isMore", isMore);
            startActivity(intent);
            finish();
        } else if (v.getId() == R.id.rightBtn) {
            if (ChoiceClassifyActivity.activity != null) {
                ChoiceClassifyActivity.activity.finish();
                ChoiceClassifyActivity.activity = null;
            }
            finish();
        } else if (v.getId() == R.id.yulan) {
            yulanListener();
        } else if (v.getId() == R.id.btnOk) {
            btnOkListener();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        ImageBean imageBean = (ImageBean)adapter.getItem(position);
        if (imageBean.isBtnTakePhoto()) {
            startActivityForResult(photoManage.getCameraIntent(), PhotoManager.CAMERA_REQ_CODE);
        } else {
            String path = imageBean.getImagePath() + "/" + imageBean.getName();
//			Log.e("Test", "cropPath=" + path);
//            path = path.replace("file://", "");
//            toCropImage(path);
            LogUtil.i("选择图片 path:" + path);
            Intent intent = getIntent();
            intent.putExtra("callBack",path);
            setResult(876,intent);
            finish();

        }
    }

    private void toCropImage(String imagePath) {
        if (!TextUtils.isEmpty(imagePath) && new File(imagePath).isFile()) {
            Intent intent = new Intent(this, CropImageActivity.class);
            intent.putExtra(CropImageActivity.PATH, imagePath);
            startActivityForResult(intent, 133);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == CropImageActivity.RESULT_CODE) {
            String headPath = "file://" + data.getStringExtra(CropImageActivity.PATH);

            File findInCache = DiskCacheUtils.findInCache(headPath, imageLoader.getDiskCache());
            if(findInCache!=null){
                findInCache.delete();
            }

//            if (PersonInfoActivity.photoListener != null) {
//                PersonInfoActivity.photoListener.callback(headPath);
//            }
//
//            if (MyActivity.photoListener != null) {
//                MyActivity.photoListener.callback(headPath);
//            }
            data.putExtra("callBack",headPath);
            setResult(876,data);
            finish();
        } else if (requestCode == PhotoManager.CAMERA_REQ_CODE) {
            String imagePath = photoManage.getCameraPath();
            toCropImage(imagePath);
        }
    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (imageFloders != null && imageFloders.size() > 0) {
                adapter = new ChoicePictureGridAdapter(ChoicePictureActivity.this, imageFloders.get(0).getImages(),isMoreChoicePhoto(),handler2);
                gridView.setAdapter(adapter);
            }
        }

    };

    public boolean isMoreChoicePhoto() {
        return false;
    }

    Handler handler2 = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            List<ImageBean> beans = adapter.getList();
            int choiced = 0;
            if (beans != null && beans.size() > 0) {
                for (ImageBean imageBean : beans) {
                    if (imageBean.isSelected()) {
                        ++choiced;
                    }
                }
            }
            labelIndex.setText(choiced + "/" + MAX_PHOTO);
        }

    };

    protected void yulanListener() {

    }

    protected void btnOkListener() {

    }
}
