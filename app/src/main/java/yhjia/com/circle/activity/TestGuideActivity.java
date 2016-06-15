package yhjia.com.circle.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.yhjia.me.guide.FGuideActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiayonghua on 16/6/14.
 */
public class TestGuideActivity extends FGuideActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setDatas(getTestData());

    }

    private List<View> getTestData() {
        List<View> views = new ArrayList<View>();
        views.add(getView(R.layout.test_guide_1));
        views.add(getView(R.layout.test_guide_2));
        views.add(getView(R.layout.test_guide_3));
        views.add(getView(R.layout.test_guide_4));
        return views;
    }

    private View getView(int resLayoutId) {
       return LayoutInflater.from(this).inflate(resLayoutId,null);
    }

    @Override
    public void pageSelected(int position) {
        toast("position:" + position);
    }
}
