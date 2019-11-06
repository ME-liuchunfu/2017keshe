package xin.spring.video.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.utils.OkLogger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.VideoEvents;
import xin.spring.video.R;
import xin.spring.video.adapter.VideoRecyclerAdapter;
import xin.spring.video.aip.AppApi;
import xin.spring.video.bean.Video;
import xin.spring.video.bean.VideoResult;
import xin.spring.video.event.DateEvent;
import xin.spring.video.event.MessageEvent;
import xin.spring.video.utils.AppSimgleUtils;
import xin.spring.video.view.AutoSwipeRefreshLayout;
import xin.spring.video.view.LoadRecyclerView;
import xin.spring.video.view.SimpleDividerItemDecoration;


public class MainActivity extends AppCompatActivity {

    private List<Video> videos = new ArrayList<Video>();

    private VideoResult result = new VideoResult();
    private VideoRecyclerAdapter mAdapter;
    private AutoSwipeRefreshLayout mSwipeRefreshLayout;
    private LoadRecyclerView recyclerView;

    private boolean isToLast = false;//判断是否是垂直向下方向滑动
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * 如果没有埋点需求，可以不用注册eventbus
         * <br>
         * if you do not want to get Buried Point , you do not need regist eventbus here
         */
        EventBus.getDefault().register(this);
        mSwipeRefreshLayout = (AutoSwipeRefreshLayout)findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setRefreshing(false);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               load();
            }
        });
        //swipeRefreshLayout.post();
        //swipeRefreshLayout.autoRefresh();
        // 设置适配器
        recyclerView = (LoadRecyclerView)findViewById(R.id.main_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        mAdapter = new VideoRecyclerAdapter(videos, this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
                // 当不滚动时(看需求，滚动的时候也可以加上)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition
                    int lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition();//从0开始
                    int totalItemCount = layoutManager.getItemCount();
                    // 判断是否滚动到底部，并且是向下滚动
                    if (lastVisibleItem == (totalItemCount - 1) && isToLast ) {
                        //加载更多功能的代码
                        System.out.println("到最后一个item了");
                        //load();
                        timer = 10;
                        EventBus.getDefault().post(new DateEvent(10, 1));
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isToLast = dy > 0;
            }
        });
        load();
    }

    /**
     * 首次加载数据
     */
    private void load(){
       OkGo.<String>get(AppApi.getList(result, 41))
            .tag(this).headers(AppSimgleUtils.registerHttpHeaders())
            .cacheKey(MainActivity.class.getSimpleName())
            .cacheMode(CacheMode.NO_CACHE)
            .execute(new StringCallback(){
                @Override
                public void onSuccess(Response<String> response) {
                    System.out.println(response.body());
                    EventBus.getDefault().post(new MessageEvent(response.body(), 1));
                    System.out.println("请求完了");
                }

            });
    }

    private void loadMore(){
        OkGo.<String>get(AppApi.getList(result, 41))
                .tag(this).headers(AppSimgleUtils.registerHttpHeaders())
                .cacheKey(MainActivity.class.getSimpleName())
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new StringCallback(){
                    @Override
                    public void onSuccess(Response<String> response) {
                        System.out.println(response.body());
                        EventBus.getDefault().post(new MessageEvent(response.body(), 2));
                        System.out.println("请求完了");
                    }

                });
    }

    private int timer = 0;

    private boolean loaded = false;

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void handleTime(DateEvent dateEvent){
        if(!loaded){
            loadMore();
        }
        Toast.makeText(getApplicationContext(), "加载中。。。", Toast.LENGTH_LONG).show();
        if (dateEvent.getType() == 1 && timer >= 1) {
            loaded = true;
            //timer = dateEvent.getTime();
            while (timer > 1) {
                try {
                    Thread.sleep(1000);
                    timer--;
                    System.out.println("当前：" + timer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            timer = 0;
            loaded = false;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleData(MessageEvent mMessageEvent) {
        Toast.makeText(this, "数据请求成功", Toast.LENGTH_SHORT).show();
        Gson gson = new Gson();
        result = gson.fromJson(mMessageEvent.getMessage(), VideoResult.class);
        if(mMessageEvent.getType() == 1){
            videos.clear();
            if(result != null && result.getList() != null){
                videos.addAll(result.getList());
                System.out.println("个数："+ result.getList().size() + ", 总个数：" + videos.size());
            }
        }else if(mMessageEvent.getType() == 2){
            if(result != null && result.getList() != null){
                videos.addAll(result.getList());
                System.out.println("个数："+ result.getList().size() + ", 总个数：" + videos.size());
            }
        }
        mAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
