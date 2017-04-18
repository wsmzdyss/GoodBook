package com.philip.goodbook.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.philip.goodbook.LoginActivity;
import com.philip.goodbook.MainActivity;
import com.philip.goodbook.R;

/**
 * Created by admin on 2017/4/18.
 */

public class LoginFragment extends Fragment {
    private Button loginBtn;

    private LoginActivity mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment_layout, container, false);
        mActivity = (LoginActivity) getActivity();
        loginBtn = (Button) view.findViewById(R.id.login_btn_loginfm);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, MainActivity.class);
                startActivity(intent);
            }
        });
        return view;

    }
}
