package com.yhjia.me.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.yhjia.me.R;
import com.yhjia.me.adapter.ShowBigImageAdapter;
import com.yhjia.me.adapter.ShowImageAdapter;
import com.yhjia.me.adapter.ShowImageAdapter_104;
import com.yhjia.me.adapter.ShowSmallImageAdapter;
import com.yhjia.me.bean.MessagePicture;
import com.yhjia.me.photo.browse.BrowserImageActivity;
import com.yhjia.me.util.DimensionPixelUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiayonghua on 16/7/9.
 * 9宫格图片显示
 */
public class NineGridView extends LinearLayout{

    private View view;
    private FixGridView grid;
    private FixGridView grid2;
    private ShowImageAdapter gridAdapter;
    private ShowImageAdapter grid2Adapter;
    private Context context;
    private Activity activity;
    private GridClickListener gridClickListener;
    public NineGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        view  = LayoutInflater.from(context).inflate(R.layout.view_show_image, this);
        grid = (FixGridView) view.findViewById(R.id.grid);
        grid2 = (FixGridView) view.findViewById(R.id.grid2);

        grid.setOnItemClickListener( new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (activity != null) {
                    ArrayList<String> pics = new ArrayList<String>();
                    List<MessagePicture> pictures = gridAdapter.getList();
                    for (MessagePicture messagePicture : pictures) {
                        pics.add(messagePicture.getPicUrl());
                    }
                    Intent intent = new Intent(activity,BrowserImageActivity.class);
                    intent.putExtra(BrowserImageActivity.TAG_IMGS, pics);
                    intent.putExtra(BrowserImageActivity.TAG_INDEX,position);
                    activity.startActivity(intent);
                }

            }
        });

        grid2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (activity != null) {
                    ArrayList<String> pics = new ArrayList<String>();
                    List<MessagePicture> pictures = grid2Adapter.getList();
                    for (MessagePicture messagePicture : pictures) {
                        pics.add(messagePicture.getPicUrl());
                    }
                    Intent intent = new Intent(activity,BrowserImageActivity.class);
                    intent.putExtra(BrowserImageActivity.TAG_IMGS, pics);
                    intent.putExtra(BrowserImageActivity.TAG_INDEX,position);
                    activity.startActivity(intent);
                }
            }
        });
        this.context = context;
        setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (gridClickListener != null) {
                    gridClickListener.click();
                }
            }
        });
    }


    public void showImages(List<MessagePicture> pictures) {
        if (pictures == null || pictures.size() < 1) {
            grid2Adapter = new ShowImageAdapter(context,new ArrayList<MessagePicture>());
            gridAdapter = new ShowImageAdapter(context,new ArrayList<MessagePicture>());
            grid.setAdapter(gridAdapter);
            grid2.setAdapter(grid2Adapter);
            grid.setVisibility(View.GONE);
            grid2.setVisibility(View.GONE);
            setVisibility(View.GONE);
        } else {
            pictures = formatData(pictures);
            setVisibility(View.VISIBLE);
            if (pictures.size() == 4) {
                grid2.setVisibility(View.VISIBLE);
                grid.setVisibility(view.GONE);
                grid2Adapter = new ShowImageAdapter(context, pictures);
                grid2.setAdapter(grid2Adapter);
            } else {
                grid.setVisibility(View.VISIBLE);
                grid2.setVisibility(view.GONE);
                gridAdapter = new ShowImageAdapter(context, pictures);
                grid.setAdapter(gridAdapter);
            }
        }
    }

    public void showImages_104(List<MessagePicture> pictures){
        if (pictures == null || pictures.size() < 1) {
            setVisibility(View.GONE);
        } else {
            setVisibility(View.VISIBLE);
            if (pictures.size() == 4) {
                grid2.setVisibility(View.VISIBLE);
                grid.setVisibility(view.GONE);
                grid2Adapter = new ShowImageAdapter_104(context, pictures);
                grid2.setAdapter(grid2Adapter);

                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)grid2.getLayoutParams();
                params.rightMargin = DimensionPixelUtil.dip2px(getContext(),120);
//				Toast.makeText(getContext(), ""+params.rightMargin, Toast.LENGTH_SHORT).show();
                grid2.setLayoutParams(params);
            } else {
                pictures = formatData(pictures);
                grid.setVisibility(View.VISIBLE);
                grid2.setVisibility(view.GONE);
                gridAdapter = new ShowImageAdapter_104(context, pictures);
                grid.setAdapter(gridAdapter);

                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)grid.getLayoutParams();
                params.rightMargin = DimensionPixelUtil.dip2px(getContext(),10);
                grid.setLayoutParams(params);
            }
        }
    }

    public void showBigImages(List<MessagePicture> pictures) {
        if (pictures == null || pictures.size() < 1) {
            setVisibility(View.GONE);
        } else {
            setVisibility(View.VISIBLE);
            if (pictures.size() == 4) {
                grid2.setVisibility(View.VISIBLE);
                grid.setVisibility(view.GONE);
                grid2Adapter = new ShowBigImageAdapter(context, pictures);
                grid2.setAdapter(grid2Adapter);
            } else {
                pictures = formatData(pictures);
                grid.setVisibility(View.VISIBLE);
                grid2.setVisibility(view.GONE);
                gridAdapter = new ShowBigImageAdapter(context, pictures);
                grid.setAdapter(gridAdapter);
            }
        }
    }

    public void showSmallImages(List<MessagePicture> pictures) {
        if (pictures == null || pictures.size() < 1) {
            setVisibility(View.GONE);
        } else {
            setVisibility(View.VISIBLE);
            if (pictures.size() == 4) {
                grid2.setVisibility(View.VISIBLE);
                grid.setVisibility(view.GONE);
                grid2Adapter = new ShowSmallImageAdapter(context, pictures);
                grid2.setAdapter(grid2Adapter);
            } else {
                grid.setVisibility(View.VISIBLE);
                grid2.setVisibility(view.GONE);
                gridAdapter = new ShowSmallImageAdapter(context, pictures);
                grid.setAdapter(gridAdapter);
            }
        }
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
//		if (onItemClickListener != null) {
//			grid.setOnItemClickListener(onItemClickListener);
//			grid2.setOnItemClickListener(onItemClickListener);
//		}
    }


    public void setActivity(Activity activity) {
        this.activity = activity;
    }


    public static interface GridClickListener {
        void click();
    }

    public void setGridClickListener(GridClickListener gridClickListener) {
        this.gridClickListener = gridClickListener;
    }

    public void setOnTouchBlankPositionListener(FixGridView.OnTouchBlankPositionListener listener){
        grid.setOnTouchBlankPositionListener(listener);
        grid.setOnTouchBlankPositionListener(listener);
    }

    private List<MessagePicture> formatData(List<MessagePicture> mps) {
        List<MessagePicture> items = new ArrayList<MessagePicture>();
        if (mps != null && mps.size() <=9) {
            return  mps;
        } else {
            for (int i = 0;i < 9;i++) {
                items.add(mps.get(i));
            }
            return  items;
        }
    }
}
