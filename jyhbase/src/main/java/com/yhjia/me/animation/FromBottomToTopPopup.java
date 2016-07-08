package com.yhjia.me.animation;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.yhjia.me.R;

/**
 * Created by jiayonghua on 16/6/16.
 * 定义从底部滑出的动画
 */
public class FromBottomToTopPopup {
    private Activity activity;
    private View view;
    private PopupWindow popupWindow;
    public FromBottomToTopPopup(final Activity activity,View view) {
        this.activity = activity;
        this.view = view;
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.activity_animation);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                lp.alpha = 1f;
                activity.getWindow().setAttributes(lp);
            }
        });
    }

    /**
     * 显示
     */
    public void show() {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 0.5f;
        activity.getWindow().setAttributes(lp);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        popupWindow.update();
    }

    /**
     * 隐藏
     */
    public void hide() {
        popupWindow.dismiss();
    }


}
