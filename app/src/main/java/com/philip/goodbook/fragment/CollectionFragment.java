package com.philip.goodbook.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.philip.goodbook.DetailActivity;
import com.philip.goodbook.MainActivity;
import com.philip.goodbook.R;
import com.philip.goodbook.adapter.GoodBookAdapter;
import com.philip.goodbook.model.Book;
import com.philip.goodbook.network.GoodBookService;
import com.philip.goodbook.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Zhangdingying on 2017/5/17.
 */

public class CollectionFragment extends Fragment {

    public RecyclerView recyclerView;

    private MainActivity mActivity;

    public SwipeRefreshLayout swipeRL;

    public List<Book> bookList;

    public GoodBookAdapter collectionAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.goodbook_swp, container, false);
        findViews(view);
        initData();
        setListener();
        return view;
    }

    private void setListener() {
        swipeRL.setColorSchemeColors(mActivity.getResources().getColor(R.color.red));
        swipeRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //handler.sendEmptyMessage(0);
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initData();
                                collectionAdapter.notifyDataSetChanged();
                                swipeRL.setRefreshing(false);
                            }
                        });
                    }
                }).start();
            }
        });
        collectionAdapter = new GoodBookAdapter(mActivity, bookList);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(collectionAdapter);

        collectionAdapter.setItemOnClickListener(new GoodBookAdapter.ItemOnClickListener() {
            @Override
            public void ItemOnClick(View view, int position, Book book) {
                Intent intent = new Intent(mActivity, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("bookDetail", book);
                if (book != null) {
                    Log.d("AAA", "ColeectionFragment" + book.toString());
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Log.d("AAA", "book == null");
                }
            }
        });
    }

    public void initData() {
        bookList = new ArrayList<Book>();
        getCollectionFromDB(mActivity.username);
    }

    private void findViews(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.goodbook_rcv);
        swipeRL = (SwipeRefreshLayout) view.findViewById(R.id.goodbook_swp);
        mActivity = (MainActivity) getActivity();
    }

    private void getCollectionFromDB(String username) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.baseUrl).addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory
                        (GsonConverterFactory.create())
                .build();
        GoodBookService goodBookService = retrofit.create(GoodBookService.class);
        Call<List<Book>> call = goodBookService.queryCollection(username);

        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                Log.d("AAA", "QueryCollection    response ===  " + response.body().toString());
                List<Book> books = response.body();
                collectionAdapter.refreshData(books);
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Log.d("AAA", "QueryCollection    Failure  " + t.getMessage());
            }
        });
    }
}
