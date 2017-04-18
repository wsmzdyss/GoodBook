package com.philip.goodbook;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.philip.goodbook.fragment.LoginFragment;
import com.philip.goodbook.fragment.RegisterFragment;

/**
 * Created by philip.zhang on 2017/4/18.
 */

public class LoginActivity extends Activity {
    private TextView loginFmBtn;
    private TextView registerFmBtn;
    private FragmentManager mFmManager;
    private FragmentTransaction mFmTransaction;
    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;
    private ImageView tabImg;
    private boolean onLoginFm = true;

    private final static int LOGIN_FRAGMENT = 0;
    private final static int REGISTER_FRAGMENT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        initView();
    }

    private void initView() {
        findViews();
        mFmManager = getFragmentManager();
        loginFragment = new LoginFragment();
        registerFragment = new RegisterFragment();
        loginFmBtn.setSelected(true);
        swithFragment(LOGIN_FRAGMENT);
        setListeners();
    }

    private void setListeners() {
        loginFmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginFmBtn.setSelected(true);
                registerFmBtn.setSelected(false);
                startAnimator(LOGIN_FRAGMENT);
                swithFragment(LOGIN_FRAGMENT);
            }
        });

        registerFmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginFmBtn.setSelected(false);
                registerFmBtn.setSelected(true);
                startAnimator(REGISTER_FRAGMENT);
                swithFragment(REGISTER_FRAGMENT);
            }
        });
    }

    private void findViews() {
        loginFmBtn = (TextView) findViewById(R.id.loginfm_btn);
        registerFmBtn = (TextView) findViewById(R.id.registerfm_btn);
        tabImg = (ImageView) findViewById(R.id.tab_login_img);
    }

    public void swithFragment(int index) {
        switch (index) {
            case LOGIN_FRAGMENT:
                mFmTransaction = mFmManager.beginTransaction();
                mFmTransaction.replace(R.id.fragment_login_fl, loginFragment);
                mFmTransaction.commit();
                onLoginFm = true;
                break;
            case REGISTER_FRAGMENT:
                mFmTransaction = mFmManager.beginTransaction();
                mFmTransaction.replace(R.id.fragment_login_fl, registerFragment);
                mFmTransaction.commit();
                onLoginFm = false;
                break;
            default:
                break;
        }
    }

    private void startAnimator(int index) {
        if (index == LOGIN_FRAGMENT && onLoginFm == false) {
            ObjectAnimator objAnimator = ObjectAnimator.ofFloat(tabImg, "translationX", 150, 0);
            objAnimator.setDuration(500);
            objAnimator.start();
        } else if (index == REGISTER_FRAGMENT && onLoginFm == true) {
            ObjectAnimator objAnimator = ObjectAnimator.ofFloat(tabImg, "translationX", 0, 150);
            objAnimator.setDuration(500);
            objAnimator.start();
        }
    }

}
