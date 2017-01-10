package com.philip.goodbook;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.philip.goodbook.adapter.CategoryAdapter;
import com.philip.goodbook.model.Category;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    private ImageView menuImg;

    private RecyclerView categoryRv;

    private List<Category> categoryList;

    private TextView titleDlTv;

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
        category = new Category(getResources().getString(R.string.picture_242), R.drawable.picture242);
        categoryList.add(category);

        category = new Category(getResources().getString(R.string.picture_243), R.drawable.picture243);
        categoryList.add(category);

        category = new Category(getResources().getString(R.string.picture_244), R.drawable.picture244);
        categoryList.add(category);

        category = new Category(getResources().getString(R.string.picture_245), R.drawable.picture245);
        categoryList.add(category);

        category = new Category(getResources().getString(R.string.picture_246), R.drawable.picture246);
        categoryList.add(category);

        category = new Category(getResources().getString(R.string.picture_247), R.drawable.picture247);
        categoryList.add(category);

        category = new Category(getResources().getString(R.string.picture_248), R.drawable.picture248);
        categoryList.add(category);

        category = new Category(getResources().getString(R.string.picture_249), R.drawable.picture249);
        categoryList.add(category);

        category = new Category(getResources().getString(R.string.picture_250_251), R.drawable.picture250_251);
        categoryList.add(category);

        category = new Category(getResources().getString(R.string.picture_252), R.drawable.picture252);
        categoryList.add(category);

        category = new Category(getResources().getString(R.string.picture_253), R.drawable.picture253);
        categoryList.add(category);

        category = new Category(getResources().getString(R.string.picture_254_end), R.drawable.picture254_end);
        categoryList.add(category);


        categoryRv.setLayoutManager(new GridLayoutManager(this, 3));
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
        titleDlTv = (TextView) findViewById(R.id.title_dl_tv);
    }
}
