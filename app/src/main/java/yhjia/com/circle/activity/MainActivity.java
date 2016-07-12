package yhjia.com.circle.activity;

import android.content.Intent;
import android.widget.ListView;
import com.yhjia.me.activity.BaseActivity;
import com.yhjia.me.constant.CodeConstant;
import com.yhjia.me.util.ToastUtil;
import com.yhjia.me.view.TopLayoutView;

import yhjia.com.circle.adapter.MainAdapter;
import yhjia.com.circle.bean.MainBean;
import yhjia.com.circle.bean.MainClickHandler;

public class MainActivity extends BaseActivity {

    private ListView list;
    private TopLayoutView topLayout;

    private MainAdapter adapter;
    private MainBean mainBean;

    @Override
    public int initLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        list = findView(R.id.list);
        topLayout = findView(R.id.topLayout);
        topLayout.setCommonTopLayout(TopLayoutView.Type.ONLY_TITLE,getString(R.string.function_list));
    }

    @Override
    public void initLogic() {
        mainBean = MainBean.getInstance(this);
        adapter = new MainAdapter(this,mainBean.getData());
        adapter.setMainItemClickListener(new MainClickHandler(this));
        list.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == CodeConstant.CODE_234) {
            ToastUtil.showToast(R.string.scan_success);
        }
    }

}
