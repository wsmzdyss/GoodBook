package com.philip.goodbook;

import android.icu.util.ULocale;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.philip.goodbook.adapter.CategoryAdapter;
import com.philip.goodbook.model.Category;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    private ImageView menuImg;

    private RecyclerView categoryRv;

    private List<Category> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mian_layout);
        findViews();
        initDate();
        setListener();
    }

    private void initDate() {
        categoryList = new ArrayList<>();
        Category category;
        for(int i = 0 ; i <12;i++)
        {
            category= new Category("武侠",R.mipmap.ic_launcher);
            categoryList.add(category);
        }

        categoryRv.setLayoutManager(new GridLayoutManager(this,3));
        categoryRv.setAdapter(new CategoryAdapter(categoryList));
    }

    private void setListener() {
        menuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void findViews() {
        drawerLayout = (DrawerLayout) findViewById(R.id.main_dl);
        menuImg = (ImageView) findViewById(R.id.leftmenu_img);
        categoryRv = (RecyclerView) findViewById(R.id.category_rv);
    }
}
