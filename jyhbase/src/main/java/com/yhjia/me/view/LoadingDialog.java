package com.yhjia.me.view;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.yhjia.me.R;

/**
 * 等待�?
 * @author JiangHao
 */
public class LoadingDialog extends AlertDialog {

    private TextView dialogTextView;


    private String message = null;

    public LoadingDialog(Context context) {
        super(context);
        message = getContext().getResources().getString(R.string.loading);
    }

    public LoadingDialog(Context context, String message) {
        super(context);
        this.message = message;
        this.setCancelable(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.ddialog_loading);
        dialogTextView = (TextView) findViewById(R.id.tips_loading_msg);
        dialogTextView.setText(this.message);
    }

    public void setText(String message) {
        this.message = message;
        dialogTextView.setText(this.message);
    }
}