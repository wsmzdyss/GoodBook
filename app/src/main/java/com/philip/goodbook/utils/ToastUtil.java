package com.philip.goodbook.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Zhangdingying on 2017/5/13.
 */

public class ToastUtil {
    private static Toast toast;

    public static void showToast(Context context, String content) {
        if (toast != null) {
            toast.setText(content);
        } else {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        }
        toast.show();
    }
}
