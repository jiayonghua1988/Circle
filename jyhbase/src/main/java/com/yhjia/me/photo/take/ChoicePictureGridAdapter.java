package com.yhjia.me.photo.take;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.yhjia.me.R;
import com.yhjia.me.activity.ChoicePictureActivity;
import com.yhjia.me.adapter.YYBaseAdapter;
import com.yhjia.me.logger.LogUtil;
import com.yhjia.me.photo.browse.ImageBean;

import java.util.List;

public class ChoicePictureGridAdapter extends YYBaseAdapter<ImageBean> {
	private boolean isMoreChoicePhoto;
	private Handler handler;
	private Context context;
	public ChoicePictureGridAdapter(Context context, List<ImageBean> list,boolean isMoreChoicePhoto,Handler handler) {
		super(context, list);
		this.isMoreChoicePhoto = isMoreChoicePhoto;
		this.handler = handler;
		this.context = context;


		imageLoader = ImageLoader.getInstance();
		builder = new DisplayImageOptions.Builder();
		builder.cacheInMemory(false)//设置下载的图片是否缓存在内存中
				.cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
				.considerExifParams(false)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT);//设置图片的解码类型//

		options = builder.build();

	}
	

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		HolderView holderView = null;
		if (convertView == null) {
			holderView = new HolderView();
			convertView = inflater.inflate(R.layout.choice_picture_grid_item, null);
			holderView.choice_pic = (ImageView) convertView.findViewById(R.id.choice_pic);
			holderView.btn_selected = (ImageView) convertView.findViewById(R.id.btn_selected);
			convertView.setTag(holderView);
		} else {
			holderView = (HolderView) convertView.getTag();
		}
		final ImageBean imageBean = list.get(position);
//		Log.e("Test", "path:" + imageBean.getImagePath() + "/" + imageBean.getName());
		if (imageBean.isBtnTakePhoto()) {
			holderView.choice_pic.setImageResource(imageBean.getDrawableId());
		} else {
			LogUtil.i("displayImageFromAlbumn");
			imageLoader.displayImage(imageBean.getImagePath() + "/" + imageBean.getName(), holderView.choice_pic, options);
		}
		holderView.btn_selected.setVisibility(isMoreChoicePhoto ? View.VISIBLE : View.GONE);
		holderView.btn_selected.setImageResource(imageBean.isSelected() ? R.drawable.btn_choosedown : R.drawable.btn_choose);
		holderView.btn_selected.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int choiced = 0;
				for (ImageBean imageBean : list) {
					if (imageBean.isSelected()) {
						++choiced;
					}
				}
				if (choiced < ChoicePictureActivity.MAX_PHOTO) {
					list.get(position).setSelected(!imageBean.isSelected());
					notifyDataSetChanged();
					handler.sendEmptyMessage(100);
				} else {
					if (list.get(position).isSelected()) {
						list.get(position).setSelected(!imageBean.isSelected());
						--choiced;
						notifyDataSetChanged();
						handler.sendEmptyMessage(100);
					} else {
						Toast.makeText(context, "最多9张图片", Toast.LENGTH_LONG).show();
					}
				}
			}
		});
		return convertView;
	}
	
	public class HolderView {
		public ImageView choice_pic;
		public ImageView btn_selected;
	}

	@Override
	public int settingDefaultPicture() {
		return 0;
	}




}