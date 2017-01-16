package com.philip.goodbook;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.philip.goodbook.adapter.CategoryAdapter;
import com.philip.goodbook.fragment.GoodBookFragment;
import com.philip.goodbook.model.Category;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    private ImageView menuImg;

    private RecyclerView categoryRv;

    private List<Category> categoryList;

    private TextView titleDlTv;

    public final static int REFRESH = 0;

    public final static int GOODBOOK_FRAGMENT = 1;

    public FragmentManager fragmentManager;

    public GoodBookFragment goodBookFragment;

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GOODBOOK_FRAGMENT:
                    replaceFragment(goodBookFragment);
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mian_layout);
        findViews();
        initView();
        initData();
        setListener();

    }

    private void initData() {
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


    }

    private void initView() {
        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        fragmentManager = getFragmentManager();

        goodBookFragment = new GoodBookFragment();

        handler.sendEmptyMessage(GOODBOOK_FRAGMENT);
    }


    private void setListener() {
        categoryRv.setLayoutManager(new GridLayoutManager(this, 3));
        categoryRv.setAdapter(new CategoryAdapter(categoryList));

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
        categoryRv = (RecyclerView) findViewById(R.id.category_rcv);
        titleDlTv = (TextView) findViewById(R.id.title_dl_tv);
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.content_fl, fragment).commit();

    }

}
