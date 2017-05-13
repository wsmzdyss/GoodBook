package com.philip.goodbook.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.philip.goodbook.R;
import com.philip.goodbook.model.Category;

import java.util.List;

/**
 * Created by philip.zhang on 2017/1/6.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<Category> categoryList;

    public CategoryAdapter(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public void refreshData(List<Category> categoryList) {
        this.categoryList = categoryList;
        this.notifyDataSetChanged();
    }

    public interface ItemOnClickListener {
        void ItemOnClick(View v, int position);
    }

    private ItemOnClickListener itemOnClickListener;

    public void setItemOnClickListener(ItemOnClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.categoryName.setText(category.getCatalog());
        String id = category.getId();
        switch (id) {
            case "242":
                holder.categoryIcon.setImageResource(R.drawable.picture242);
                break;
            case "243":
                holder.categoryIcon.setImageResource(R.drawable.picture243);
                break;
            case "244":
                holder.categoryIcon.setImageResource(R.drawable.picture244);
                break;
            case "245":
                holder.categoryIcon.setImageResource(R.drawable.picture245);
                break;
            case "246":
                holder.categoryIcon.setImageResource(R.drawable.picture246);
                break;
            case "247":
                holder.categoryIcon.setImageResource(R.drawable.picture247);
                break;
            case "248":
                holder.categoryIcon.setImageResource(R.drawable.picture248);
                break;
            case "249":
                holder.categoryIcon.setImageResource(R.drawable.picture249);
                break;
            case "250":
                holder.categoryIcon.setImageResource(R.drawable.picture250);
                break;
            case "251":
                holder.categoryIcon.setImageResource(R.drawable.picture251);
                break;
            case "252":
                holder.categoryIcon.setImageResource(R.drawable.picture252);
                break;
            case "253":
                holder.categoryIcon.setImageResource(R.drawable.picture253);
                break;
            case "254":
                holder.categoryIcon.setImageResource(R.drawable.picture254);
                break;
            case "255":
                holder.categoryIcon.setImageResource(R.drawable.picture255);
                break;
            case "256":
                holder.categoryIcon.setImageResource(R.drawable.picture256);
                break;
            case "257":
                holder.categoryIcon.setImageResource(R.drawable.picture257);
                break;
            default:
                break;
        }

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getLayoutPosition();
                itemOnClickListener.ItemOnClick(view, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView categoryName;
        ImageView categoryIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            categoryName = (TextView) itemView.findViewById(R.id.categoryName_item);
            categoryIcon = (ImageView) itemView.findViewById(R.id.categoryImg_item);
            view = itemView;
        }
    }
}
