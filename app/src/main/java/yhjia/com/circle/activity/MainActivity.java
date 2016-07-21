package yhjia.com.circle.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.yhjia.me.activity.BaseActivity;
import com.yhjia.me.constant.CodeConstant;
import com.yhjia.me.util.ToastUtil;
import com.yhjia.me.view.TopLayoutView;
import java.util.Arrays;
import yhjia.com.circle.adapter.MainAdapter;
import yhjia.com.circle.bean.MainClickHandler;

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    private ListView list;
    private TopLayoutView topLayout;

    private MainAdapter adapter;
    private MainClickHandler mainClickHandler;

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
        list.setOnItemClickListener(this);
        String [] arrays = getResources().getStringArray(R.array.main_demo_list);
        adapter = new MainAdapter(this, Arrays.asList(arrays));
        mainClickHandler = new MainClickHandler(this);
        list.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == CodeConstant.CODE_234) {
            ToastUtil.showToast(R.string.scan_success);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mainClickHandler.onItem(position);
    }
}
