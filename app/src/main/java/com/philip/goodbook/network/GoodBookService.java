package com.philip.goodbook.network;

import com.philip.goodbook.model.Book;
import com.philip.goodbook.model.BookBaseEntity;
import com.philip.goodbook.model.CgBaseEntity;
import com.philip.goodbook.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by philip.zhang on 2017/1/20.
 */

public interface GoodBookService {

    @GET("index")
    Call<String> index();

    @POST("register")
    Call<String> register(@Body User user);

    @POST("login")
    Call<String> login(@Body User user);

    @POST("modifyUser")
    Call<String> modifyUser(@Body User user);

    @POST("queryUser/{username}")
    Call<User> queryUser(@Path("username") String username);

    @POST("addCollection/{username}")
    Call<String> addCollection(@Body Book book, @Path("username") String username);

    @POST("deleteCollection/{username}")
    Call<String> deleteCollection(@Body Book book, @Path("username") String username);

    @POST("queryCollection/{username}")
    Call<List<Book>> queryCollection(@Path("username") String username);

}
