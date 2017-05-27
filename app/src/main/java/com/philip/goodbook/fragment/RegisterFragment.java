package com.philip.goodbook.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.philip.goodbook.R;
import com.philip.goodbook.model.User;
import com.philip.goodbook.network.GoodBookService;
import com.philip.goodbook.utils.Constants;
import com.philip.goodbook.utils.ToastUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by admin on 2017/4/18.
 */

public class RegisterFragment extends Fragment {

    private Button register;

    private EditText nickname;

    private EditText username;

    private EditText password;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment_layout, container, false);
        findViews(view);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nicknameStr = nickname.getText().toString().trim();
                String usernameStr = username.getText().toString().trim();
                String passwordStr = password.getText().toString().trim();
                if (TextUtils.isEmpty(usernameStr) || TextUtils.isEmpty(passwordStr)) {
                    ToastUtil.showToast(getActivity(), "用户名或密码为空");
                } else {
                    User user = new User();
                    user.setNickname(nicknameStr);
                    user.setUsername(usernameStr);
                    user.setPassword(passwordStr);
                    Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.baseUrl).addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory
                                    (GsonConverterFactory.create())
                            .build();
                    GoodBookService goodBookService = retrofit.create(GoodBookService.class);
                    Call<String> call = goodBookService.register(user);

                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Log.d("AAA", "RegisterFragment   response ===   " + response.body());
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.d("AAA", "RegisterFragment   onFailure   " + t.getMessage());
                        }

                    });
                }
            }
        });
        return view;
    }

    public void findViews(View view) {
        register = (Button) view.findViewById(R.id.register_btn_regifm);
        nickname = (EditText) view.findViewById(R.id.nickname_regifm);
        username = (EditText) view.findViewById(R.id.username_regifm);
        password = (EditText) view.findViewById(R.id.password_regifm);
    }
}
