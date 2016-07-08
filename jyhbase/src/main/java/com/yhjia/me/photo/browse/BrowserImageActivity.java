package com.yhjia.me.photo.browse;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yhjia.me.R;
import com.yhjia.me.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class BrowserImageActivity extends BaseActivity implements View.OnClickListener {

	private ViewPager viewPager;
	private RelativeLayout parentLay;
	private DisplayImageOptions options;
//	private TextView name, num;

	private List<String> imgs = new ArrayList<String>();
	;
	private int index;

	public static final String TAG_NAME = "name";
	public static final String TAG_IMGS = "imgUrls";
	public static final String TAG_INDEX = "imgIndex";
	private String nameStr;
	private ImageLoader imageLoader;
	private List<ImageBean> imageBeans;
	private int currentPosition;

	private View btnLeft;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public int initLayoutID() {
		return R.layout.activity_browser_image;
	}


	@Override
	public void initView() {
		imgs.addAll(getIntent().getStringArrayListExtra(TAG_IMGS));
		nameStr = getIntent().getStringExtra(TAG_NAME);
		index = getIntent().getIntExtra(TAG_INDEX, 0);

		parentLay = (RelativeLayout) findViewById(R.id.parentLay);
		viewPager = new HackyViewPager(this);
		parentLay.addView(viewPager);
//		name = (TextView)findViewById(R.id.name);
//		num = (TextView)findViewById(R.id.num);
		btnLeft = findViewById(R.id.btnLeft);
		btnLeft.setOnClickListener(this);
//		btnState = (ImageView) findViewById(R.id.btnState);
//		btnState.setOnClickListener(this);
//		btnOk = findViewById(R.id.btnOk);
//		btnOk.setOnClickListener(this);
		ImageBean imageBean = (ImageBean) getIntent().getSerializableExtra("iBean");
		if (imageBean != null) {
			imageBeans = imageBean.getImageBeans();
		}
	}

	@Override
	public void initLogic() {
		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder()
//		.showImageOnLoading(R.drawable.default_empty)
//				.showImageForEmptyUri(R.drawable.default_empty)
//				.showImageOnFail(R.drawable.default_empty)
				.cacheInMemory(false)
				.cacheOnDisk(true)
				.considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.build();

//		num.setText((index + 1) + "/" + imgs.size());
//		name.setText(nameStr);
		viewPager.setAdapter(new SamplePagerAdapter());
		viewPager.setCurrentItem(index);
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
//				num.setText((arg0 + 1) + "/" + imgs.size());
//				name.setText(nameStr);
				currentPosition = arg0;

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	public class SamplePagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return imgs.size();
		}

		@Override
		public View instantiateItem(ViewGroup container, int position) {
			View convertView = getLayoutInflater().inflate(R.layout.item_browser_image, null);
			ImageView photoView = (ImageView) convertView.findViewById(R.id.photoView);
			imageLoader.displayImage(imgs.get(position), photoView, options);
			photoView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					BrowserImageActivity.this.finish();
				}
			});
			// Now just add PhotoView to ViewPager and return it
			container.addView(convertView, Gallery.LayoutParams.MATCH_PARENT, Gallery.LayoutParams.MATCH_PARENT);
			return convertView;
		}


		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
	}

	@Override
	public void onClick(View v) {

	}
}