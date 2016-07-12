package yhjia.com.circle.activity;

import android.view.View;

import com.yhjia.me.activity.BaseActivity;
import com.yhjia.me.listview.PinnedSectionListView;
import com.yhjia.me.view.TopLayoutView;

import java.util.ArrayList;
import java.util.List;

import yhjia.com.circle.adapter.PinnerSectionListAdapter;
import yhjia.com.circle.bean.PinnerSectionListBean;

/**
 * Created by jiayonghua on 16/7/12.
 */
public class PinnerSectionListDemo extends BaseActivity{

    private TopLayoutView topLayout;
    private PinnedSectionListView pinnerList;
    private PinnerSectionListAdapter adapter;
    @Override
    public int initLayoutID() {
        return R.layout.activity_pinner_selection;
    }

    @Override
    public void initView() {
        topLayout = findView(R.id.topLayout);
        pinnerList = findView(R.id.pinnerList);
        topLayout.setOnClickListener(this);
    }

    @Override
    public void initLogic() {
        adapter = new PinnerSectionListAdapter(this,getTestData());
        pinnerList.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()) {
           case R.id.leftBtn:
               finish();
           break;
       }
    }

    private List<PinnerSectionListBean> getTestData() {
        List<PinnerSectionListBean> beans = new ArrayList<PinnerSectionListBean>();
        for (int i = 0; i < 50;i++) {
            beans.add(new PinnerSectionListBean(getType(i),getTypeLabel(i)));
        }
        return beans;
    }

    private int getType(int positon) {
        if (positon == 0 || positon == 15 || positon == 30 || positon == 35 || positon == 40) {
            return 1;
        }
        return 0;
    }
    private String getTypeLabel(int position) {
        String labelStr = null;
        switch (position) {
            case 0:
                labelStr = "分类一";
            break;
            case 15:
                labelStr = "分类二";
            break;
            case 30:
                labelStr = "分类三";
            break;
            case 35:
                labelStr = "分类四";
            break;
            case 40:
                labelStr = "分类五";
            break;
            default:
                labelStr = "测试" + position;
                break;
        }
        return labelStr;
    }
}
