package yhjia.com.circle.activity;

import android.view.View;
import android.widget.ListPopupWindow;

import com.yhjia.me.activity.BaseActivity;

import java.util.Arrays;

import yhjia.com.circle.adapter.ListPopupWindowAdapter;

/**
 * Created by jiayonghua on 16/7/20.
 */
public class ListPopupWindowDemoActivity extends BaseActivity{
    private ListPopupWindow listPopupWindow;
    private ListPopupWindowAdapter adapter;

    private View showMenu;

    @Override
    public int initLayoutID() {
        return R.layout.activity_popupmenu;
    }

    @Override
    public void initView() {
        showMenu = findView(R.id.showMenu);
    }

    @Override
    public void initLogic() {
        showMenu.setOnClickListener(this);
        listPopupWindow = new ListPopupWindow(this);

        /**
         * 设置显示的触发控件
         */
        listPopupWindow.setAnchorView(showMenu);

        String [] arrays = getResources().getStringArray(R.array.list_popupWindow);
        adapter = new ListPopupWindowAdapter(this, Arrays.asList(arrays));
        listPopupWindow.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.showMenu) {
            listPopupWindow.show();
        }
    }
}
