package com.philip.goodbook;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Zhangdingying on 2017/5/13.
 */

public class BaseActivity extends Activity {
    private MyApplication application;
    private BaseActivity oContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (application == null) {
            // 得到Application对象
            application = (MyApplication) getApplication();
        }
        oContext = this;// 把当前的上下文对象赋值给BaseActivity
        addActivity();// 调用添加方法
    }

    // 添加Activity方法
    public void addActivity() {
        application.addActivity(oContext);// 调用myApplication的添加Activity方法
    }

    //销毁当个Activity方法
    public void removeActivity() {
        application.removeActivity(oContext);// 调用myApplication的销毁单个Activity方法
    }

    //销毁所有Activity方法
    public void removeALLActivity() {
        application.removeALLActivity();// 调用myApplication的销毁所有Activity方法
    }

}
