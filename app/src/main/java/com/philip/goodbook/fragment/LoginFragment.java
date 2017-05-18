package com.philip.goodbook.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.philip.goodbook.LoginActivity;
import com.philip.goodbook.MainActivity;
import com.philip.goodbook.R;
import com.philip.goodbook.model.User;
import com.philip.goodbook.network.GoodBookService;
import com.philip.goodbook.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by admin on 2017/4/18.
 */

public class LoginFragment extends Fragment {
    private EditText username;

    private EditText password;

    private Button loginBtn;

    private LoginActivity mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment_layout, container, false);
        mActivity = (LoginActivity) getActivity();
        findViews(view);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final User user = new User();
                user.setUsername(username.getText().toString().trim());
                user.setPassword(password.getText().toString().trim());
                Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.baseUrl).addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory
                                (GsonConverterFactory.create())
                        .build();
                GoodBookService goodBookService = retrofit.create(GoodBookService.class);
                Call<String> call = goodBookService.login(user);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d("AAA", "LoginFragment   response ===   " + response.body());
                        if (response.body().equals("success")) {
                            Intent intent = new Intent(mActivity, MainActivity.class);
                            intent.putExtra("username",user.getUsername());
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("AAA", "LoginFragment   onFailure   " + t.getMessage());
                    }
                });

            }
        });
        return view;
    }

    private void findViews(View view) {
        username = (EditText) view.findViewById(R.id.username_loginfm);
        password = (EditText) view.findViewById(R.id.password_loginfm);
        loginBtn = (Button) view.findViewById(R.id.login_btn_loginfm);
    }
}
