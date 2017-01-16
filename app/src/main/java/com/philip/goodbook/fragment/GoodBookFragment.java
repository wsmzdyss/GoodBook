package com.philip.goodbook.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.philip.goodbook.MainActivity;
import com.philip.goodbook.R;
import com.philip.goodbook.adapter.GoodBookAdapter;
import com.philip.goodbook.model.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by philip.zhang on 2017/1/16.
 */

public class GoodBookFragment extends Fragment {

    public RecyclerView recyclerView;

    private MainActivity mActivity;

    public SwipeRefreshLayout swipeRL;

    public List<Book> bookList;

    public GoodBookAdapter goodBookAdapter;

    private Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.goodbook_swp, container, false);
        findViews(view);
        initData();
        setListener();
        return view;
    }

    private void setListener() {
        swipeRL.setColorSchemeColors(mActivity.getColor(R.color.red));
        swipeRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //handler.sendEmptyMessage(0);
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initData();
                                goodBookAdapter.notifyDataSetChanged();
                                swipeRL.setRefreshing(false);
                            }
                        });
                    }
                }).start();
            }
        });
        goodBookAdapter = new GoodBookAdapter(bookList);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(goodBookAdapter);
    }

    public void initData() {
        bookList = new ArrayList<Book>();
        Book book;
        for (int i = 0; i < 10; i++) {
            book = new Book("微观历史", "中国文学 历史", "一本好书 中国历史 历史好书推荐 历史日记 当代中国", "《微观历史》：1957—1965", "《微观历史》是中国作家祝伟坡著作的一部历史笔记。作品从个人视角出发，记录了1957年至1965年期间中国大跃进人民公社化运动、整风整社运动、四清运动等建国初期的历史事件。《微观历史》以震撼人心的坦率直播个人命运的进退，用亲身经历的事实投影社会历史的浮沉，堪称一部微观的共和国史。\n" +
                    "     《微观历史》作者祝伟坡于1935年出生在河北省魏县，是河北师范大学副教授、中国历史学者。他早年曾研究外国文学，后主要研究中国近现代史，经常在多处报刊发表学术论文和散文，文学代表作有《踏步集》、《微观历史》等。\n" +
                    "     新中国建国初期，那是一段怎样的岁月呢？小时候，爷爷奶奶偶尔会给我们讲那个时代的故事；历史课本上，我们也能了解个大概。但是，那段岁月始终如一个蒙着面纱的女子，朦朦胧胧的，瞧不见她的真容。而《微观历史》，却帮助我们轻轻撩起了那层神秘的面纱，再现了那个时期真实的社会风貌。\n" +
                    "     《微观历史》以日记的形式，记录了历次运动中的所见所闻、所思所感，带领我们走进那段不平凡的岁月，让我们重温历史，体会当时人们迷茫的心理和曲折坎坷的人生，感受时代脉搏的跳动。这些字字真诚的日记于细微处见证了一个时代的理想、迷失和荒诞，它既是火热年代平民的心灵史，更是时代的活化石。《微观历史》中大量珍贵的历史照片，让我们直击历史场景，让那段岁月的故事如电影般播放在我们的眼前，清清楚楚。\n" +
                    "     翻开《微观历史》，阅读珍藏的日记，让我们体味到一个青年的心灵独白。拂去历史的尘埃，《微观历史》再现了一个国家的时代风貌。著名学者单正平说：“这是一个普通知识分子的心路历程。一切都似曾相识，一切都陌生新鲜，一切都惊心动魄，一切都微波细澜。家史即国史，心史即信史。大一统下的破碎人生，最宜用日记也只有用日记做记录。时过境未迁，后生堪镜鉴。”\n" +
                    "     《微观历史》真实地为读者们展现了那段不平凡年代的历史，对于年轻一代正确地总结过去、珍惜今天，有着十分积极的意义。\n" +
                    "     《微观历史》", "http://apis.juhe.cn/goodbook/img/93b469cf22ef86b40ce84a6c63b95e82.jpg", "2737人阅读", "京东商城:http://item.jd.com/11174489.html 当当网:http://product.dangdang.com/product.aspx?product_id=23181422 亚马逊:http://www.amazon.cn/%E5%BE%AE%E8%A7%82%E5%8E%86%E5%8F%B2-1957-1965-%E7%A5%9D%E4%BC%9F%E5%9D%A1/dp/B00B20MZ3M/ref=sr_1_1", "2013年8月6日");
            bookList.add(book);
        }

    }

    private void findViews(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.goodbook_rcv);
        swipeRL = (SwipeRefreshLayout) view.findViewById(R.id.goodbook_swp);
        mActivity = (MainActivity) getActivity();
    }

    private void getHandler() {
        this.handler = mActivity.handler;
    }


}
