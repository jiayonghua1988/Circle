package com.yhjia.me.photo.take;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yhjia.me.R;
import com.yhjia.me.activity.BaseActivity;
import com.yhjia.me.activity.ChoicePictureActivity;
import com.yhjia.me.view.TopLayoutView;

import java.util.List;

public class ChoiceClassifyActivity extends BaseActivity implements AdapterView.OnItemClickListener {
	private TopLayoutView topLayout;
	private ListView classifyList;
	private List<ImageFloder> imageFloders;
	private BitmapTools bitmapTools;
	public static Activity activity;
	private boolean isMore;

	@Override
	public int initLayoutID() {
		return R.layout.activity_choice_classify;
	}

	@Override
	public void initView() {
		topLayout = (TopLayoutView) findViewById(R.id.topLayout);
		classifyList = (ListView) findViewById(R.id.classifyList);
	}

	@Override
	public void initLogic() {
		isMore = getIntent().getBooleanExtra("isMore", false);
		topLayout.setOnClickListener(this);
		topLayout.setCommonTopLayout(TopLayoutView.Type.TITLE_AND_RIGHT, "选择相册","取消");
		bitmapTools = new BitmapTools();
		imageFloders = bitmapTools.getDeviceImages(this,handler);
		classifyList.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		if (v.getId() == R.id.rightBtn) {
			finish();
		}
	}
	
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			ChoicePictureAdapter choicePictureAdapter = new ChoicePictureAdapter(ChoiceClassifyActivity.this, imageFloders);
			classifyList.setAdapter(choicePictureAdapter);
//			if (imageFloders != null && imageFloders.size() > 0) {
//				Intent intent = new Intent(ChoiceClassifyActivity.this,ChoicePictureActivity.class);
//				intent.putExtra("pics", imageFloders.get(0));
//				startActivity(intent);
//			}
		}
		
	};
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (imageFloders != null && imageFloders.size() > position) {
			ImageFloder imageFloder = imageFloders.get(position);
			Intent intent = new Intent(ChoiceClassifyActivity.this,isMore ? ChoiceTopicPictureActivity.class : ChoicePictureActivity.class);
			intent.putExtra("pics", imageFloder);
			intent.putExtra("isMore", isMore);
			startActivityForResult(intent, 985);
			finish();
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
//		if (resultCode == 138) {
//			String path = data.getStringExtra("path");
//			Intent intent = new Intent(ChoiceClassifyActivity.this,PersonInfoActivity.class);
//			intent.putExtra("path", path);
//			setResult(145, data);
//			finish();
//		}
	}
}
