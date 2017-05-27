package com.philip.goodbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.philip.goodbook.model.Book;
import com.philip.goodbook.network.GoodBookService;
import com.philip.goodbook.utils.Constants;
import com.philip.goodbook.utils.MyDialog;
import com.philip.goodbook.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Zhangdingying on 2017/5/9.
 */

public class DetailActivity extends BaseActivity {
    private ImageView img;
    private ImageView collection;
    private TextView title;
    private TextView sub1;
    private TextView catlog;
    private TextView bytime;
    private TextView reading;
    private TextView sub2;
    private Button btnJD;
    private Button btnDD;
    private Button btnAMZ;

    private Retrofit retrofit;

    private GoodBookService goodBookService;

    private Book book;

    private String username = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);

        initViews();
        initData();
    }

    private void initViews() {
        findViews();

        sub2.setMovementMethod(ScrollingMovementMethod.getInstance());

        retrofit = new Retrofit.Builder().baseUrl(Constants.baseUrl).addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        goodBookService = retrofit.create(GoodBookService.class);

        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (book.isCollection()) {
                    final MyDialog myDialog = new MyDialog(DetailActivity.this);
                    myDialog.setMessage("是否取消本书收藏");
                    myDialog.setYesBtn("是", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            collection.setImageResource(R.drawable.collection_normal);
                            book.setCollection(false);
                            Call<String> call = goodBookService.deleteCollection(book, username);
                            call.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    Log.d("AAA", "CollectionDelete  response ===   " + response.body());
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Log.d("AAA", "CollectionDelete  Failure   " + t.getMessage());
                                }
                            });
                            myDialog.dissmissDialog();
                        }
                    });
                    myDialog.setNoBtn("否", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            myDialog.dissmissDialog();
                        }
                    });

                } else {
                    collection.setImageResource(R.drawable.collection_selected);
                    book.setCollection(true);

                    Call<String> call = goodBookService.addCollection(book, username);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Log.d("AAA", "CollectionAdd  response ===   " + response.body());
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.d("AAA", "CollectionAdd  Failure   " + t.getMessage());
                        }
                    });
                }
            }
        });
    }

    private void initData() {
        Intent intent = getIntent();
        book = (Book) intent.getSerializableExtra("bookDetail");
        username = intent.getStringExtra("username");
        Glide.with(DetailActivity.this).load(book.getImg()).error(R.drawable.default_img).placeholder(R.drawable.default_img).into(img);
        title.setText(book.getTitle());
        sub1.setText(book.getSub1());
        catlog.setText(book.getCatalog());
        bytime.setText(book.getBytime());
        reading.setText(book.getReading());
        sub2.setText(book.getSub2());
        if (book.isCollection()) {
            collection.setImageResource(R.drawable.collection_selected);
        } else {
            collection.setImageResource(R.drawable.collection_normal);
        }

        final String[] online = Utils.parseOnline(book.getOnline());
        btnJD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, ShoppingActivity.class);
                intent.putExtra("urlShp", online[0]);
                startActivity(intent);
            }
        });

        btnDD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, ShoppingActivity.class);
                intent.putExtra("urlShp", online[1]);
                startActivity(intent);
            }
        });

        btnAMZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, ShoppingActivity.class);
                intent.putExtra("urlShp", online[2]);
                startActivity(intent);
            }
        });


    }

    private void findViews() {
        img = (ImageView) findViewById(R.id.detail_img);
        collection = (ImageView) findViewById(R.id.detail_collection);
        title = (TextView) findViewById(R.id.detail_title);
        sub1 = (TextView) findViewById(R.id.detail_sub1);
        catlog = (TextView) findViewById(R.id.detail_catalog);
        bytime = (TextView) findViewById(R.id.detail_bytime);
        reading = (TextView) findViewById(R.id.detail_reading);
        sub2 = (TextView) findViewById(R.id.detail_sub2);
        btnJD = (Button) findViewById(R.id.detail_bt_jd);
        btnDD = (Button) findViewById(R.id.detail_bt_dd);
        btnAMZ = (Button) findViewById(R.id.detail_bt_amz);
    }
}
