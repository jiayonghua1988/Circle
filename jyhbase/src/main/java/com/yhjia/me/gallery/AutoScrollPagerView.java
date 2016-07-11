package com.yhjia.me.gallery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yhjia.me.R;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jiayonghua on 16/7/9.
 * 自动轮播banner
 */
public class AutoScrollPagerView extends LinearLayout implements ViewPager.OnPageChangeListener {

    private LayoutInflater inflater;
    private DisallowParentTouchViewPager pager;
    private ViewGroup rootIcons;
    private List<TopGalleryBean> galleryBeans;
    protected DisplayImageOptions.Builder builder;
    private ImageView[] imageViews;
    private Activity activity;
    private TopPagerAdapter adapter;
    private Timer timer;
    private boolean isOnTouch = false;
    private long selectTime;
    private int tab;
    private ViewGroup parent;



    public void setTopGalleryData(List<TopGalleryBean> galleryBeans,Activity activity) {
        this.galleryBeans = galleryBeans;
        this.activity = activity;
        if (galleryBeans != null) {
            imageViews = new ImageView[galleryBeans.size()];
            rootIcons.removeAllViews();
            if (galleryBeans.size() > 1) {
                for (int i = 0; i < galleryBeans.size(); i++) {
                    ImageView imageView = new ImageView(activity);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            getResources().getDrawable(R.drawable.dot_active)
                                    .getIntrinsicWidth() + 10,
                            LayoutParams.WRAP_CONTENT);
                    params.rightMargin = 10;
                    params.bottomMargin = 10;
                    imageView.setLayoutParams(params);
                    imageViews[i] = imageView;
                    if (i == 0) {
                        imageViews[i].setImageResource(R.drawable.dot_active);
                    } else {
                        imageViews[i].setImageResource(R.drawable.dot);
                    }
                    rootIcons.addView(imageView);
                }
            }
            adapter = new TopPagerAdapter(activity);
            pager.setAdapter(adapter);

            timer = new Timer();
            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    handler.sendEmptyMessage(1000);
                }
            }, 3 * 1000,2 * 1000);
        }
    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (adapter.getCount() > 1 && (System.currentTimeMillis() - selectTime >= 3 * 1000)) {
                pager.getCurrentItem();
                pager.setCurrentItem((pager.getCurrentItem() + 1) % (adapter.getCount()));
            }
        }
    };

    public AutoScrollPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_top_gallery, this);
        pager = (DisallowParentTouchViewPager) view.findViewById(R.id.pager);
        pager.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent event) {
                if (MotionEvent.ACTION_DOWN == event.getAction()) {
//					Log.e("Test", "按下....");
                    isOnTouch = true;
                } else if (MotionEvent.ACTION_UP == event.getAction()) {
                    isOnTouch = false;
//					Log.e("Test", "弹起....");
                }
                return false;
            }
        });
        rootIcons = (ViewGroup) view.findViewById(R.id.rootIcons);
        pager.setOnPageChangeListener(this);

    }


    @Override
    public void onPageScrollStateChanged(int position) {
    }


    @Override
    public void onPageScrolled(int arg0, float arg1, int position) {
    }


    @Override
    public void onPageSelected(int position) {
        selectTime = System.currentTimeMillis();
        for (int i = 0; i < galleryBeans.size(); i++) {
            if (position != i) {
                imageViews[i].setImageResource(R.drawable.dot);
            } else {
                imageViews[position].setImageResource(R.drawable.dot_active);
            }
        }


    }


    class TopPagerAdapter extends PagerAdapter {
        private Context context;
        private LayoutInflater inflater;
        protected ImageLoader imageLoader;
        protected DisplayImageOptions options;


        public TopPagerAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
            configLoadImage(R.drawable.banner_default);
        }

        @Override
        public int getCount() {
            return galleryBeans.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View convertView = inflater.inflate(R.layout.item_top_gallery_pager, container,false);
            ImageView img = (ImageView) convertView.findViewById(R.id.itemImg);
            final TopGalleryBean bean = galleryBeans.get(position);
            imageLoader.displayImage(bean.getIcon(), img,options);
            container.addView(convertView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            convertView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Intent intent = new Intent(activity, bean.getClazz());
                    if (bean.getType() == 6) {
                        intent.putExtra("userId",bean.getUserid());
                    }
                    intent.putExtra(bean.getIntentParamKey(), bean.getSourceid());
                    activity.startActivity(intent);
                }
            });
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

        private void configLoadImage(int defaultDrawableId) {
            imageLoader = ImageLoader.getInstance();
            builder = new DisplayImageOptions.Builder();
            builder.cacheOnDisk(true);
            builder.considerExifParams(true);
            builder.cacheInMemory(false);
            builder.bitmapConfig(Bitmap.Config.RGB_565);
            if (defaultDrawableId > 0) {
                builder.showImageOnFail(defaultDrawableId);
                builder.showImageForEmptyUri(defaultDrawableId);
            }else{
                builder.showImageOnFail(R.drawable.default_empty);
                builder.showImageForEmptyUri(R.drawable.default_empty);
            }
            options = builder.build();
        }

    }

    public void setParent(ViewGroup parent) {
        pager.setNestParent(parent);
    }


    /**
     * 圈子  1
     * 趣孕 2
     * @param tab
     */
    public void tabType(int tab) {
        this.tab = tab;
    }
}
