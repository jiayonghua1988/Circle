package yhjia.com.circle.activity;

import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;

import com.yhjia.me.activity.BaseActivity;
import com.yhjia.me.util.ToastUtil;

/**
 * Created by jiayonghua on 16/7/20.
 */
public class PopupMenuDemoActivity extends BaseActivity implements android.widget.PopupMenu.OnMenuItemClickListener{
    private View showMenu;

    private PopupMenu popupMenu;

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

        /**
         * 两个参数 第一个参数context
         * 第二个参数，激发弹出菜单的控件
         */
        popupMenu = new PopupMenu(this,showMenu);
        getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.showMenu) {
            showView();
        }
    }

    protected void showView() {
        popupMenu.show();
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int strId = R.string.btn_hot;
        switch (item.getItemId()) {
            case R.id.recommend:
                strId = R.string.btn_recommend;
            break;
            case R.id.hot:
                strId = R.string.btn_hot;
            break;
            case R.id.society:
                strId = R.string.btn_society;
            break;
            case R.id.military:
                strId = R.string.btn_military;
            break;
        }
        ToastUtil.showToast(strId);
        popupMenu.dismiss();
        return true;
    }
}
