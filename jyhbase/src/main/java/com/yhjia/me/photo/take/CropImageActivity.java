package com.yhjia.me.photo.take;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.yhjia.me.R;
import com.yhjia.me.activity.BaseActivity;
import com.yhjia.me.util.FileUtil;

public class CropImageActivity extends BaseActivity {

	public static final String PATH = "path";
	public static final int RESULT_CODE = 1010;
	public static final String CACHE_IMAGE_PATH = Environment.getExternalStorageDirectory().getPath() + "/Android/data/me.yhjia.base/cache/";
	
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private CropImageView cropImageView;

	private TextView save;

	private String path;
	private View btnCancel;


	@Override
	public int initLayoutID() {
		return R.layout.activity_cropimage;
	}

	@Override
	public void initView() {
		cropImageView = findView(R.id.cropImg);
		save = findView(R.id.save);
		btnCancel = findView(R.id.btnCancel);
	}

	@Override
	public void initLogic() {
		path = getIntent().getStringExtra(PATH);
		btnCancel.setOnClickListener(this);
		save.setOnClickListener(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (path == null)
			return;
		
		ImageSize imageSize =new ImageSize(640, 640);
		Bitmap bitmap = imageLoader.loadImageSync("file:///" + path,imageSize,new DisplayImageOptions.Builder().considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).build());
		cropImageView.setDrawable(new BitmapDrawable(getResources(), bitmap), 128*2, 128*2);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btnCancel) {
			finish();
		} else if (v.getId() == R.id.save) {
			String name = "cropImageView.png";
			String path =CACHE_IMAGE_PATH + name;
			Bitmap cropImage = cropImageView.getCropImage();
			FileUtil.saveBitmap2SD(cropImage, CACHE_IMAGE_PATH, name);
			if(!cropImage.isRecycled()){
				cropImage.recycle();
			}

			Intent intent = new Intent();
			intent.putExtra("path", path);
			setResult(RESULT_CODE, intent);
			finish();
		}
	}
}