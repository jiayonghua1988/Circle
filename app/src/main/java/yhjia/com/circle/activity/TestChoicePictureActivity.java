package yhjia.com.circle.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yhjia.me.activity.BaseActivity;
import com.yhjia.me.photo.take.ChoicePictureAction;
import com.yhjia.me.photo.take.PictureCallbackListener;
import com.yhjia.me.view.TopLayoutView;

/**
 * Created by jiayonghua on 16/6/18.
 */
public class TestChoicePictureActivity extends BaseActivity implements PictureCallbackListener{
    private TopLayoutView topLayout;
    private View btnChoicePicture;
    private ImageView showImg;
    private ChoicePictureAction action;
    private ImageLoader imageLoader;
    @Override
    public int initLayoutID() {
        return R.layout.activity_test_choice;
    }

    @Override
    public void initView() {
        topLayout = findView(R.id.topLayout);
        btnChoicePicture = findView(R.id.btnChoicePicture);
        showImg = findView(R.id.showImg);
        topLayout.setCommonTopLayout(TopLayoutView.Type.BACK_AND_TITLE,getString(R.string.system_camera));
        topLayout.setOnClickListener(this);
        action = new ChoicePictureAction(this);
        action.setPictureCallbackListener(this);
    }

    @Override
    public void initLogic() {
        btnChoicePicture.setOnClickListener(this);

        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLeft:
                finish();
            break;
            case R.id.btnChoicePicture:
                action.show();
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        action.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void callback(String picUrl) {
        Log.e("Test","callback picUrl:" + picUrl);
        if (!TextUtils.isEmpty(picUrl)) {
            imageLoader.displayImage(picUrl,showImg);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
