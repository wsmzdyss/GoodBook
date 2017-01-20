package com.philip.goodbook.adapter;

import android.support.v7.widget.RecyclerView;
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

    public CategoryAdapter(List<Category> categoryList){
        this.categoryList = categoryList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.categoryName.setText(category.getCatalog());
        holder.categoryIcon.setImageResource(R.drawable.picture242);

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static  class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView categoryIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            categoryName = (TextView) itemView.findViewById(R.id.categoryName_item);
            categoryIcon = (ImageView) itemView.findViewById(R.id.categoryImg_item);
        }
    }
}
