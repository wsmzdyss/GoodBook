package com.philip.goodbook.network;

import android.content.Context;

import com.philip.goodbook.model.BookBaseEntity;
import com.philip.goodbook.model.Category;
import com.philip.goodbook.model.CgBaseEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by philip.zhang on 2017/1/21.
 */

public class BaseTask {
    private Call<CgBaseEntity> cgCall;

    private Call<BookBaseEntity> bookCall;

    private Context context;

    private final static String SUCCESS1 = "success";

    private final static String SUCCESS2 = "Success";

    private final static String SUCCESSCODE = "200";

    public BaseTask(Context context) {
        this.context = context;
    }

    public void cgHandleResponse(Call cgCall, final ResponseListener listener) {
        this.cgCall = cgCall;
        this.cgCall.enqueue(new Callback<CgBaseEntity>() {
            @Override
            public void onResponse(Call<CgBaseEntity> call, Response<CgBaseEntity> response) {
                CgBaseEntity cg = response.body();
                if (cg.getReason().equals(SUCCESS1) &&
                        cg.getResultcode().equals(SUCCESSCODE)) {
                    listener.onSuccess(cg.getResult());
                }
            }

            @Override
            public void onFailure(Call<CgBaseEntity> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public void bookHandleResponse(Call bookCall, final ResponseListener listener) {
        this.bookCall = bookCall;
        this.bookCall.enqueue(new Callback<BookBaseEntity>() {
            @Override
            public void onResponse(Call<BookBaseEntity> call, Response<BookBaseEntity> response) {
                BookBaseEntity book = response.body();
                if (book.getReason().equals(SUCCESS2) && book.getResultcode().equals(SUCCESSCODE)) {
                    listener.onSuccess(book.getResult().getData());
                }
            }

            @Override
            public void onFailure(Call<BookBaseEntity> call, Throwable t) {
                    listener.onFailure(t);
            }
        });
    }

    public interface ResponseListener<T> {
        void onSuccess(List<T> t);

        void onFailure(Throwable t);
    }

}
