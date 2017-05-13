package com.philip.goodbook.network;

import com.philip.goodbook.model.BookBaseEntity;
import com.philip.goodbook.model.CgBaseEntity;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by philip.zhang on 2017/1/20.
 */

public interface RetroiftService {
    @GET("catalog")
    Call<CgBaseEntity> getCategories(@Query("key") String key);

    @FormUrlEncoded
    @POST("query")
    Call<BookBaseEntity> getBookList(@Field("catalog_id") String id, @Field("pn") String
            pn, @Field("rn") String rn, @Field("key") String key);
}
