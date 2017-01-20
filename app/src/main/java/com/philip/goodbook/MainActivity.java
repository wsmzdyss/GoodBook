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
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.philip.goodbook.adapter.CategoryAdapter;
import com.philip.goodbook.fragment.GoodBookFragment;
import com.philip.goodbook.model.BaseEntity;
import com.philip.goodbook.model.Category;
import com.philip.goodbook.network.RetroiftService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;

    private ImageView menuImg;

    private RecyclerView categoryRv;

    private CategoryAdapter categoryAdapter;

    private List<Category> categoryList;

    private TextView titleDlTv;

    public final static int REFRESH = 0;

    public final static int GOODBOOK_FRAGMENT = 1;

    private String url = "http://apis.juhe.cn/goodbook/";

    private final static String APPKEY = "0964124c815408f5e817855200d61cf8";

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

        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        RetroiftService retroiftService = retrofit.create(RetroiftService.class);
        Call<BaseEntity<List<Category>>> call = retroiftService.getCategories(APPKEY);
        call.enqueue(new Callback<BaseEntity<List<Category>>>() {
            @Override
            public void onResponse(Call<BaseEntity<List<Category>>> call, Response<BaseEntity<List<Category>>> response) {
                BaseEntity<List<Category>> baseEntity = response.body();
                Log.d("TAG", baseEntity.getReason());
                Log.d("TAG", baseEntity.getResultcode());
                if (baseEntity.getReason().equals("success") && baseEntity.getResultcode().equals("200")) {
                    categoryList = baseEntity.getResult();
                }
            }

            @Override
            public void onFailure(Call<BaseEntity<List<Category>>> call, Throwable t) {
            }
        });

        for (Category c : categoryList) {
            Log.d("TAG", c.getId());
            Log.d("TAG", c.getCatalog());
        }

        categoryAdapter.notifyDataSetChanged();
    }

    private void initData() {
//        categoryList = new ArrayList<>();
//        Category category;
//        category = new Category(getResources().getString(R.string.picture_242), R.drawable.picture242);
//        categoryList.add(category);
//
//        category = new Category(getResources().getString(R.string.picture_243), R.drawable.picture243);
//        categoryList.add(category);
//
//        category = new Category(getResources().getString(R.string.picture_244), R.drawable.picture244);
//        categoryList.add(category);
//
//        category = new Category(getResources().getString(R.string.picture_245), R.drawable.picture245);
//        categoryList.add(category);
//
//        category = new Category(getResources().getString(R.string.picture_246), R.drawable.picture246);
//        categoryList.add(category);
//
//        category = new Category(getResources().getString(R.string.picture_247), R.drawable.picture247);
//        categoryList.add(category);
//
//        category = new Category(getResources().getString(R.string.picture_248), R.drawable.picture248);
//        categoryList.add(category);
//
//        category = new Category(getResources().getString(R.string.picture_249), R.drawable.picture249);
//        categoryList.add(category);
//
//        category = new Category(getResources().getString(R.string.picture_250_251), R.drawable.picture250_251);
//        categoryList.add(category);
//
//        category = new Category(getResources().getString(R.string.picture_252), R.drawable.picture252);
//        categoryList.add(category);
//
//        category = new Category(getResources().getString(R.string.picture_253), R.drawable.picture253);
//        categoryList.add(category);
//
//        category = new Category(getResources().getString(R.string.picture_254_end), R.drawable.picture254_end);
//        categoryList.add(category);

        //Collections.sort(categoryList);


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
        categoryList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(categoryList);
        categoryRv.setLayoutManager(new GridLayoutManager(this, 3));
        categoryRv.setAdapter(categoryAdapter);

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
