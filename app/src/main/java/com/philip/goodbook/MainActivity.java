package com.philip.goodbook;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.philip.goodbook.adapter.CategoryAdapter;
import com.philip.goodbook.fragment.CollectionFragment;
import com.philip.goodbook.fragment.GoodBookFragment;
import com.philip.goodbook.model.BookBaseEntity;
import com.philip.goodbook.model.CgBaseEntity;
import com.philip.goodbook.model.Book;
import com.philip.goodbook.model.Category;
import com.philip.goodbook.model.User;
import com.philip.goodbook.network.BaseTask;
import com.philip.goodbook.network.GoodBookService;
import com.philip.goodbook.network.RetroiftService;
import com.philip.goodbook.utils.Constants;
import com.philip.goodbook.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends BaseActivity {
    private DrawerLayout drawerLayout;

    private ImageView menuImg;

    private ImageView goodbookImg;

    private ImageView collectImg;

    private RecyclerView categoryRv;

    private CategoryAdapter mCategoryAdapter;

    private List<Category> mCategoryList;

    private List<Book> mBookList;

    private ImageView iconImg;

    private TextView nickname;

    private TextView sex;

    private TextView age;

    private TextView sign;

    private Button edit;

    private RelativeLayout editRl;

    private EditText editNickname;

    private EditText editAge;

    private EditText editSign;

    private RadioGroup rgSex;

    private Button editButton;

    private RelativeLayout progressBar;

    public final static int REFRESH_MENU = 0;

    public final static int GOODBOOK_FRAGMENT = 1;

    public final static int REFRESH_BOOKLIST = 2;

    public final static int REFRESH_USER = 3;

    public final static int REFRESH_BOOKLIST_FC = 4;

    private final static String url = "http://apis.juhe.cn/goodbook/";

    private final static String APPKEY = "0964124c815408f5e817855200d61cf8";

    public FragmentManager fragmentManager;

    private Fragment currentFragment;

    public GoodBookFragment goodBookFragment;

    public CollectionFragment collectionFragment;

    private long time = 0;

    public String currentCata = "242";

    public String username;

    private boolean isEdit = false;


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
                    dismissProgressBar();
                    getCollectFromDB(username, mBookList);
                    break;

                case REFRESH_USER: {
                    User user = (User) msg.obj;
                    if (user.getNickname() != null) {
                        nickname.setText(user.getNickname());
                    } else {
                        nickname.setText("请修改你的昵称");
                    }
                    age.setText(user.getAge() + "岁");
                    if (user.getSign() != null) {
                        sign.setText(user.getSign());
                    } else {
                        sign.setText("请留下您的个性签名！");
                    }
                    if (user.getSex() == 0) {
                        sex.setText("女");
                    } else {
                        sex.setText("男");
                    }
                    break;
                }
                case REFRESH_BOOKLIST_FC:
                    goodBookFragment.updateList(mBookList);
                    goodBookFragment.swipeRL.setRefreshing(false);
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
        username = getIntent().getStringExtra("username");
        showProgressBar();
        getUserFromDB(username);
        getCategoryFromNet();
        getListFromNet("242", "0", "30");
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

        collectionFragment = new CollectionFragment();

        handler.sendEmptyMessage(GOODBOOK_FRAGMENT);

        goodbookImg.setSelected(true);

        setListener();
    }


    private void setListener() {
        mCategoryList = new ArrayList<>();
        mBookList = new ArrayList<>();
        categoryRv.setLayoutManager(new GridLayoutManager(this, 3));
        mCategoryAdapter = new CategoryAdapter(mCategoryList);
        categoryRv.setAdapter(mCategoryAdapter);

        Glide.with(MainActivity.this).load(R.drawable.icon).into(iconImg);

        menuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                editRl.setVisibility(View.INVISIBLE);
                categoryRv.setVisibility(View.VISIBLE);
                isEdit = false;
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        goodbookImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(goodBookFragment);
                showProgressBar();
                getListFromNet(currentCata, "0", "30");
                goodbookImg.setSelected(true);
                collectImg.setSelected(false);
            }
        });

        collectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(collectionFragment);
                goodbookImg.setSelected(false);
                collectImg.setSelected(true);
            }
        });

        mCategoryAdapter.setItemOnClickListener(new CategoryAdapter.ItemOnClickListener() {
            @Override
            public void ItemOnClick(View v, int position) {
                drawerLayout.closeDrawers();
                Category category = mCategoryList.get(position);
                String id = category.getId();
                getListFromNet(id, "0", "30");
                currentCata = id;
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEdit) {
                    editRl.setVisibility(View.INVISIBLE);
                    categoryRv.setVisibility(View.VISIBLE);
                    isEdit = false;
                } else {
                    editRl.setVisibility(View.VISIBLE);
                    categoryRv.setVisibility(View.INVISIBLE);
                    isEdit = true;
                }
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nickname = editNickname.getText().toString().trim();
                final int age = Integer.parseInt(editAge.getText().toString().trim());
                final String sign = editSign.getText().toString().trim();
                final int sex = rgSex.getCheckedRadioButtonId() == R.id.female_sex_mainfl ? 0 : 1;
                if (TextUtils.isEmpty(sign) || TextUtils.isEmpty(nickname) || TextUtils.isEmpty(editAge.getText().toString().trim())) {
                    ToastUtil.showToast(MainActivity.this, "信息不能为空");
                } else {
                    User user = new User();
                    user.setUsername(username);
                    user.setNickname(nickname);
                    user.setAge(age);
                    user.setSex(sex);
                    user.setSign(sign);
                    Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.baseUrl).addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory
                                    (GsonConverterFactory.create())
                            .build();
                    GoodBookService goodBookService = retrofit.create(GoodBookService.class);
                    Call<String> call = goodBookService.modifyUser(user);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Log.d("AAA", "Modify   response ===   " + response.body());
                            if (response.body().equals("success")) {
                                User user = new User();
                                user.setUsername(username);
                                user.setNickname(nickname);
                                user.setAge(age);
                                user.setSex(sex);
                                user.setSign(sign);
                                Message msg = Message.obtain();
                                msg.what = REFRESH_USER;
                                msg.obj = user;
                                handler.sendMessage(msg);
                                Log.d("AAA", "user ===   " + user.toString());
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.d("AAA", "Modify   onFailure   " + t.getMessage());
                        }
                    });


                    editRl.setVisibility(View.INVISIBLE);
                    categoryRv.setVisibility(View.VISIBLE);
                    isEdit = false;
                }
            }
        });
    }

    private void findViews() {
        drawerLayout = (DrawerLayout) findViewById(R.id.main_dl);
        menuImg = (ImageView) findViewById(R.id.leftmenu_img);
        goodbookImg = (ImageView) findViewById(R.id.goodbook_main_img);
        collectImg = (ImageView) findViewById(R.id.collection_main_img);
        categoryRv = (RecyclerView) findViewById(R.id.category_rcv);
        iconImg = (ImageView) findViewById(R.id.icon_main);
        nickname = (TextView) findViewById(R.id.nickname_main);
        sex = (TextView) findViewById(R.id.sex_main);
        age = (TextView) findViewById(R.id.age_main);
        sign = (TextView) findViewById(R.id.sign_main);
        edit = (Button) findViewById(R.id.edit_main);
        progressBar = (RelativeLayout) findViewById(R.id.refresh_main_rl);
        editRl = (RelativeLayout) findViewById(R.id.edit_main_rl);
        editNickname = (EditText) findViewById(R.id.nickname_mainfm);
        editAge = (EditText) findViewById(R.id.age_mainfm);
        editSign = (EditText) findViewById(R.id.sign_mainfm);
        rgSex = (RadioGroup) findViewById(R.id.sex_main_rg);
        editButton = (Button) findViewById(R.id.btn_mainfm);
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (currentFragment != fragment) {
            fragmentTransaction.replace(R.id.content_fl, fragment).commit();
        }

    }

    public void getCategoryFromNet() {
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


    public void getListFromNet(String id, String pn, String rn) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create())
                .build();
        RetroiftService retroiftService = retrofit.create(RetroiftService.class);
        Call<BookBaseEntity> call = retroiftService.getBookList(id, pn, rn, APPKEY);

        new BaseTask(MainActivity.this).bookHandleResponse(call, new BaseTask.ResponseListener<Book>() {
            @Override
            public void onSuccess(List<Book> t) {
                Log.d("AAA", "BOOK SUCCESS");
                //Log.d("AAA", "BOOK SUCCESS" + t.toString());
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

    public void getUserFromDB(String username) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.baseUrl).addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory
                        (GsonConverterFactory.create())
                .build();
        GoodBookService goodBookService = retrofit.create(GoodBookService.class);
        Call<User> call = goodBookService.queryUser(username);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("AAA", "QueryUser    response ===  ");
                User user = response.body();
                Message msg = Message.obtain();
                msg.what = REFRESH_USER;
                msg.obj = user;
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("AAA", "QueryUser    Failure  " + t.getMessage());
            }
        });
    }

    public void getCollectFromDB(String username, final List<Book> books) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.baseUrl).addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory
                        (GsonConverterFactory.create())
                .build();
        GoodBookService goodBookService = retrofit.create(GoodBookService.class);
        Call<List<Book>> call = goodBookService.queryCollection(username);

        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                List<Book> list = response.body();
                for (Book book : list) {
                    for (Book book1 : books) {
                        if (book.getTitle().equals(book1.getTitle())) {
                            book1.setCollection(true);
                            Log.d("AAA", "setCollection     " + book1.getTitle() + "  is collection");
                        }
                    }
                }
                mBookList = books;
                Log.d("AAA","mBookList is       "+mBookList.get(2).isCollection());
                handler.sendEmptyMessage(REFRESH_BOOKLIST_FC);
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {

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

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void dismissProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }


}
