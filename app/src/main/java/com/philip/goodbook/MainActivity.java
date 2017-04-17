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
import com.philip.goodbook.network.BaseTask;
import com.philip.goodbook.network.RetroiftService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;

    private ImageView menuImg;

    private RecyclerView categoryRv;

    private CategoryAdapter mCategoryAdapter;

    private List<Category> mCategoryList;

    private TextView titleDlTv;

    public final static int REFRESH_MENU = 0;

    public final static int GOODBOOK_FRAGMENT = 1;

    private String url = "http://apis.juhe.cn/goodbook/";

    private final static String APPKEY = "0964124c815408f5e817855200d61cf8";

    public FragmentManager fragmentManager;

    public GoodBookFragment goodBookFragment;

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.d("TAG", "what = : " + msg.what);
            switch (msg.what) {
                case REFRESH_MENU:
                    Collections.sort(mCategoryList);
                    mCategoryList.remove(mCategoryList.size() - 1);
                    mCategoryAdapter.refreshDataSet(mCategoryList);
                    mCategoryAdapter.notifyDataSetChanged();
                    break;
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
        getCategoryFromNet();

    }

    private void initData() {

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
        mCategoryList = new ArrayList<>();
        categoryRv.setLayoutManager(new GridLayoutManager(this, 3));
        mCategoryAdapter = new CategoryAdapter(mCategoryList);
        categoryRv.setAdapter(mCategoryAdapter);

        menuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        mCategoryAdapter.setItemOnClickListener(new CategoryAdapter.ItemOnClickListener() {
            @Override
            public void ItemOnClick(View v, int position) {
                updateListData(v, position);
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

    private void getCategoryFromNet() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        RetroiftService retroiftService = retrofit.create(RetroiftService.class);
        Call<BaseEntity<List<Category>>> call = retroiftService.getCategories(APPKEY);

        new BaseTask<List<Category>>(MainActivity.this, call).handleResponse(new BaseTask.ResponseListener<List<Category>>() {

            @Override
            public void onSuccess(List<Category> categoryList) {
                mCategoryList = categoryList;
                handler.sendEmptyMessage(REFRESH_MENU);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void updateListData(View v, int position) {

    }

}
