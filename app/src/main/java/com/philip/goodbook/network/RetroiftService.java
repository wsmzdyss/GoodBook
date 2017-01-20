package com.philip.goodbook.network;

import com.philip.goodbook.model.BaseEntity;
import com.philip.goodbook.model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by philip.zhang on 2017/1/20.
 */

public interface RetroiftService {
    @GET("catalog")
    Call<BaseEntity<List<Category>>> getCategories(@Query("key") String key);
}
