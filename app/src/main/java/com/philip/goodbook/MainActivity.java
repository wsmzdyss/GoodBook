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
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.philip.goodbook.adapter.CategoryAdapter;
import com.philip.goodbook.fragment.GoodBookFragment;
import com.philip.goodbook.model.BookBaseEntity;
import com.philip.goodbook.model.CgBaseEntity;
import com.philip.goodbook.model.Book;
import com.philip.goodbook.model.Category;
import com.philip.goodbook.network.BaseTask;
import com.philip.goodbook.network.RetroiftService;
import com.philip.goodbook.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends BaseActivity {
    private DrawerLayout drawerLayout;

    private ImageView menuImg;

    private RecyclerView categoryRv;

    private CategoryAdapter mCategoryAdapter;

    private List<Category> mCategoryList;

    private List<Book> mBookList;

    private TextView titleDlTv;

    public final static int REFRESH_MENU = 0;

    public final static int GOODBOOK_FRAGMENT = 1;

    public final static int REFRESH_BOOKLIST = 2;

    private final static String url = "http://apis.juhe.cn/goodbook/";

    private final static String APPKEY = "0964124c815408f5e817855200d61cf8";

    public FragmentManager fragmentManager;

    public GoodBookFragment goodBookFragment;

    private long time = 0;


    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.d("TAG", "what = : " + msg.what);
            switch (msg.what) {
                case REFRESH_MENU:
                    Collections.sort(mCategoryList);
                    mCategoryList.remove(mCategoryList.size() - 1);
                    mCategoryAdapter.refreshData(mCategoryList);
                    break;
                case GOODBOOK_FRAGMENT:
                    replaceFragment(goodBookFragment);
                    break;
                case REFRESH_BOOKLIST:
                    goodBookFragment.updateList(mBookList);
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

    }

    private void initData() {
        getCategoryFromNet();
        getListFromNet("242", "0", "5");
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

        setListener();
    }


    private void setListener() {
        mCategoryList = new ArrayList<>();
        mBookList = new ArrayList<>();
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
                drawerLayout.closeDrawers();
                Category category = mCategoryList.get(position);
                String id = category.getId();
                getListFromNet(id, "0", "5");
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
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create())
                .build();
        RetroiftService retroiftService = retrofit.create(RetroiftService.class);
        Call<CgBaseEntity> call = retroiftService.getCategories(APPKEY);

        new BaseTask(MainActivity.this).cgHandleResponse(call, new BaseTask.ResponseListener<Category>() {
            @Override
            public void onSuccess(List<Category> t) {
                Log.d("AAA", "BOOK SUCCESS");
                Log.d("AAA", t.toString());
                mCategoryList = t;
                handler.sendEmptyMessage(REFRESH_MENU);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("AAA", "CG Failure");
                Log.d("AAA", "CG Throwable  ===  " + t.getMessage());
            }
        });
    }


    private void getListFromNet(String id, String pn, String rn) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create())
                .build();
        RetroiftService retroiftService = retrofit.create(RetroiftService.class);
        Call<BookBaseEntity> call = retroiftService.getBookList(id, pn, rn, APPKEY);

        new BaseTask(MainActivity.this).bookHandleResponse(call, new BaseTask.ResponseListener<Book>() {
            @Override
            public void onSuccess(List<Book> t) {
                Log.d("AAA", "BOOK SUCCESS");
                Log.d("AAA", "BOOK SUCCESS" + t.toString());
                mBookList = t;
                handler.sendEmptyMessage(REFRESH_BOOKLIST);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("AAA", "BOOK Failure");
                Log.d("AAA", "BOOK Throwable  ===  " + t.getMessage());
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断当点击的是返回键
        if (keyCode == event.KEYCODE_BACK) {
            exit();//退出方法
        }
        return true;
    }


    private void exit() {
        if (System.currentTimeMillis() - time > 2000) {
            time = System.currentTimeMillis();
            ToastUtil.showToast(this, "再点击一次退出应用程序");
        } else {
            removeALLActivity();//执行移除所以Activity方法
        }
    }


}
