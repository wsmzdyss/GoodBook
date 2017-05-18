package com.philip.goodbook.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.philip.goodbook.R;


/**
 * Created by Zhangdingying on 2017/5/17.
 */

public class MyDialog {

    private Context context;

    private TextView content;

    private Button yesBtn;

    private Button noBtn;

    private Dialog dialog;

    private AlertDialog.Builder builder;

    public MyDialog(Context context) {
        this.context = context;
        builder = new AlertDialog.Builder(context);
        dialog = builder.create();
        dialog.show();
        dialog.setContentView(R.layout.dialog_layout);
        content = (TextView) dialog.findViewById(R.id.content_dialog_tv);
        yesBtn = (Button) dialog.findViewById(R.id.yes_dialog_btn);
        noBtn = (Button) dialog.findViewById(R.id.no_dialog_btn);
    }

    public void setMessage(String content) {
        this.content.setText(content);
    }

    public void setYesBtn(String yes, View.OnClickListener listener) {
        yesBtn.setText(yes);
        yesBtn.setOnClickListener(listener);
    }

    public void setNoBtn(String no, View.OnClickListener listener) {
        noBtn.setText(no);
        noBtn.setOnClickListener(listener);
    }

    public void dissmissDialog() {
        dialog.dismiss();
    }


}
