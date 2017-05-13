package com.philip.goodbook.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.philip.goodbook.R;
import com.philip.goodbook.model.Book;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by philip.zhang on 2017/1/6.
 */

public class GoodBookAdapter extends RecyclerView.Adapter<GoodBookAdapter.ViewHolder> {

    private List<Book> bookList;

    private Context context;

    public GoodBookAdapter(Context context, List<Book> bookList) {
        this.context = context;
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
        Book Book = bookList.get(position);
        holder.title.setText(Book.getTitle());
        Glide.with(context).load(Book.getImg()).into(holder.img);
        holder.sub.setText(Book.getSub2());
        holder.reading.setText(Book.getReading());
        holder.bytime.setText(Book.getBytime());
        holder.collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bookList.get(position).isCollection()) {
                    holder.collection.setImageResource(R.drawable.collection_normal);
                    bookList.get(position).setCollection(false);
                } else {
                    holder.collection.setImageResource(R.drawable.collection_selected);
                    bookList.get(position).setCollection(true);
                }
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

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            title = (TextView) itemView.findViewById(R.id.item_gd_title);
            img = (ImageView) itemView.findViewById(R.id.item_gd_img);
            sub = (TextView) itemView.findViewById(R.id.item_gd_sub);
            reading = (TextView) itemView.findViewById(R.id.item_gd_reading);
            bytime = (TextView) itemView.findViewById(R.id.item_gd_bytime);
            collection = (ImageView) itemView.findViewById(R.id.item_gd_collection);
        }
    }

}
