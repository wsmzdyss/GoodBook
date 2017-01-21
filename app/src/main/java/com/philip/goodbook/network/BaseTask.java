package com.philip.goodbook.network;

import android.content.Context;

import com.philip.goodbook.model.BaseEntity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by philip.zhang on 2017/1/21.
 */

public class BaseTask<T> {
    private Call<BaseEntity<T>> call;

    private Context context;

    private final static String SUCCESS = "success";

    private final static String SUCCESSCODE = "200";

    public BaseTask(Context context, Call call) {
        this.context = context;
        this.call = call;
    }

    public void handleResponse(final ResponseListener listener) {
        call.enqueue(new Callback<BaseEntity<T>>() {
            @Override
            public void onResponse(Call<BaseEntity<T>> call, Response<BaseEntity<T>> response) {
                BaseEntity<T> t = response.body();
                if (t.getReason().equals(SUCCESS) && t.getResultcode().equals(SUCCESSCODE)) {
                    listener.onSuccess((T) t.getResult());
                }
            }

            @Override
            public void onFailure(Call<BaseEntity<T>> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public interface ResponseListener<T> {
        void onSuccess(T t);

        void onFailure(Throwable t);
    }

}
