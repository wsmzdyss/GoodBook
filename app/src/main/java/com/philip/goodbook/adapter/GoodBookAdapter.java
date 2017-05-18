package com.philip.goodbook.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.philip.goodbook.MainActivity;
import com.philip.goodbook.R;
import com.philip.goodbook.ShoppingActivity;
import com.philip.goodbook.model.Book;
import com.philip.goodbook.network.GoodBookService;
import com.philip.goodbook.utils.Constants;
import com.philip.goodbook.utils.MyDialog;
import com.philip.goodbook.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by philip.zhang on 2017/1/6.
 */

public class GoodBookAdapter extends RecyclerView.Adapter<GoodBookAdapter.ViewHolder> {

    private List<Book> bookList;

    private MainActivity mActivity;

    private Retrofit retrofit;

    private GoodBookService goodBookService;

    public GoodBookAdapter(MainActivity mActivity, List<Book> bookList) {
        this.mActivity = mActivity;
        this.bookList = bookList;
    }

    public void refreshData(List<Book> bookList) {
        this.bookList = bookList;
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goodbook_item,
                parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    public interface ItemOnClickListener {
        void ItemOnClick(View view, int position, Book book);
    }

    private ItemOnClickListener itemOnClickListener;

    public void setItemOnClickListener(ItemOnClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Book book = bookList.get(position);
        holder.title.setText(book.getTitle());
        Glide.with(mActivity).load(book.getImg()).error(R.drawable.default_img).placeholder(R.drawable.default_img).into(holder.img);
        holder.sub.setText(book.getSub2());
        holder.reading.setText(book.getReading());
        holder.bytime.setText(book.getBytime());
        retrofit = new Retrofit.Builder().baseUrl(Constants.baseUrl).addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        goodBookService = retrofit.create(GoodBookService.class);
        holder.collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (book.isCollection()) {
                    final MyDialog myDialog = new MyDialog(mActivity);
                    myDialog.setMessage("是否取消本书收藏");
                    myDialog.setYesBtn("是", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            holder.collection.setImageResource(R.drawable.collection_normal);
                            bookList.get(position).setCollection(false);
                            Call<String> call = goodBookService.deleteCollection(book, mActivity.username);
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
                    holder.collection.setImageResource(R.drawable.collection_selected);
                    bookList.get(position).setCollection(true);

                    Call<String> call = goodBookService.addCollection(book, mActivity.username);
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

        final String[] online = Utils.parseOnline(bookList.get(position).getOnline());
        holder.jd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, ShoppingActivity.class);
                intent.putExtra("urlShp", online[0]);
                mActivity.startActivity(intent);
            }
        });

        holder.dd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, ShoppingActivity.class);
                intent.putExtra("urlShp", online[1]);
                mActivity.startActivity(intent);
            }
        });

        holder.amz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, ShoppingActivity.class);
                intent.putExtra("urlShp", online[2]);
                mActivity.startActivity(intent);
            }
        });

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemOnClickListener.ItemOnClick(view, position, bookList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View view;
        TextView title;
        ImageView img;
        TextView sub;
        TextView reading;
        TextView bytime;
        ImageView collection;
        Button jd;
        Button dd;
        Button amz;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            title = (TextView) itemView.findViewById(R.id.item_gd_title);
            img = (ImageView) itemView.findViewById(R.id.item_gd_img);
            sub = (TextView) itemView.findViewById(R.id.item_gd_sub);
            reading = (TextView) itemView.findViewById(R.id.item_gd_reading);
            bytime = (TextView) itemView.findViewById(R.id.item_gd_bytime);
            collection = (ImageView) itemView.findViewById(R.id.item_gd_collection);
            jd = (Button) itemView.findViewById(R.id.item_gd_bt_jd);
            dd = (Button) itemView.findViewById(R.id.item_gd_bt_dd);
            amz = (Button) itemView.findViewById(R.id.item_gd_bt_amz);
        }
    }

}
