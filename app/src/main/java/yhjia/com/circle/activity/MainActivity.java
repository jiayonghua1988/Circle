package yhjia.com.circle.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.yhjia.me.activity.BaseActivity;

public class MainActivity extends BaseActivity {
    private View btnGuide;
    private View erCode;
    private View btnCrash;
    private View loadWeb;



    @Override
    public int initLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        btnGuide = findView(R.id.btnGuide);
        erCode = findView(R.id.erCode);
        btnCrash = findView(R.id.btnCrash);
        loadWeb = findView(R.id.loadWeb);
    }

    @Override
    public void initLogic() {
        btnGuide.setOnClickListener(this);
        erCode.setOnClickListener(this);
        btnCrash.setOnClickListener(this);
        loadWeb.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btnGuide: // 引导页
                intent = new Intent(this,TestGuideActivity.class);
                startActivity(intent);
            break;
            case R.id.erCode: // 扫描二维码
                intent = new Intent(this, CaptureActivity.class);
                startActivityForResult(intent,234);
            break;
            case R.id.btnCrash: // 异常采集
                String str = null;
                if (str.equals("aa")) {

                }
            break;
            case R.id.loadWeb: // 加载网页

            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 234) {
            Log.e("Test", "扫码结果：" + getIntent().getStringExtra("result"));
            toast("扫描成功");
        }
    }
}
