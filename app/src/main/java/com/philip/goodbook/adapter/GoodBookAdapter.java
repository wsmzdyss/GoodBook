package com.philip.goodbook.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.philip.goodbook.R;
import com.philip.goodbook.model.Book;

import java.util.List;

/**
 * Created by philip.zhang on 2017/1/6.
 */

public class GoodBookAdapter extends RecyclerView.Adapter<GoodBookAdapter.ViewHolder> {

    private List<Book> bookList;

    public GoodBookAdapter(List<Book> BookList) {
        this.bookList = BookList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goodbook_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book Book = bookList.get(position);
        holder.title.setText(Book.getTitle());
        holder.img.setImageResource(R.drawable.picture242);
        holder.sub.setText(Book.getSub2());
        holder.reading.setText(Book.getReading());
        holder.bytime.setText(Book.getBytime());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView img;
        TextView sub;
        TextView reading;
        TextView bytime;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.item_gd_title);
            img = (ImageView) itemView.findViewById(R.id.item_gd_img);
            sub = (TextView) itemView.findViewById(R.id.item_gd_sub);
            reading = (TextView) itemView.findViewById(R.id.item_gd_reading);
            bytime = (TextView) itemView.findViewById(R.id.item_gd_bytime);
        }
    }
}
