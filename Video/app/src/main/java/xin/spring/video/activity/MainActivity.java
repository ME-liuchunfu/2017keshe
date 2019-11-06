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
        LoadRecyclerView recyclerView = (LoadRecyclerView)findViewById(R.id.main_recycler_view);
        LinearLayoutManager layoutManager  = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        mAdapter = new VideoRecyclerAdapter(videos, this);
        recyclerView.setAdapter(mAdapter);
        load();

    }

    /**
     * 首次加载数据
     */
    private void load(){
       OkGo.<String>get(AppApi.LIST_URL + 41)
            .tag(this).headers(AppSimgleUtils.registerHttpHeaders())
            .cacheKey(MainActivity.class.getSimpleName())
            .cacheMode(CacheMode.NO_CACHE)
            .execute(new StringCallback(){
                @Override
                public void onSuccess(Response<String> response) {
                    System.out.println(response.body());
                    EventBus.getDefault().post(new MessageEvent(response.body()));
                    System.out.println("请求完了");
                }

            });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleData(MessageEvent mMessageEvent) {
        Toast.makeText(this, "数据请求成功", Toast.LENGTH_SHORT).show();
        Gson gson = new Gson();
        VideoResult result = gson.fromJson(mMessageEvent.getMessage(), VideoResult.class);
        videos.clear();
        if(result != null && result.getList() != null){
            videos.addAll(result.getList());
            mAdapter.notifyDataSetChanged();
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    public void onEventMainThread(VideoEvents event) {
        if (event.type == VideoEvents.POINT_START_ICON) {
            Log.i("Video Event", "POINT_START_ICON" + " title is : " + event.obj + " url is : " + event.obj1);
        } else if (event.type == VideoEvents.POINT_START_THUMB) {
            Log.i("Video Event", "POINT_START_THUMB" + " title is : " + event.obj + " url is : " + event.obj1);
        } else if (event.type == VideoEvents.POINT_STOP) {
            Log.i("Video Event", "POINT_STOP" + " title is : " + event.obj + " url is : " + event.obj1);
        } else if (event.type == VideoEvents.POINT_STOP_FULLSCREEN) {
            Log.i("Video Event", "POINT_STOP_FULLSCREEN" + " title is : " + event.obj + " url is : " + event.obj1);
        } else if (event.type == VideoEvents.POINT_RESUME) {
            Log.i("Video Event", "POINT_RESUME" + " title is : " + event.obj + " url is : " + event.obj1);
        } else if (event.type == VideoEvents.POINT_RESUME_FULLSCREEN) {
            Log.i("Video Event", "POINT_RESUME_FULLSCREEN" + " title is : " + event.obj + " url is : " + event.obj1);
        } else if (event.type == VideoEvents.POINT_CLICK_BLANK) {
            Log.i("Video Event", "POINT_CLICK_BLANK" + " title is : " + event.obj + " url is : " + event.obj1);
        } else if (event.type == VideoEvents.POINT_CLICK_BLANK_FULLSCREEN) {
            Log.i("Video Event", "POINT_CLICK_BLANK_FULLSCREEN" + " title is : " + event.obj + " url is : " + event.obj1);
        } else if (event.type == VideoEvents.POINT_CLICK_SEEKBAR) {
            Log.i("Video Event", "POINT_CLICK_SEEKBAR" + " title is : " + event.obj + " url is : " + event.obj1);
        } else if (event.type == VideoEvents.POINT_CLICK_SEEKBAR_FULLSCREEN) {
            Log.i("Video Event", "POINT_CLICK_SEEKBAR_FULLSCREEN" + " title is : " + event.obj + " url is : " + event.obj1);
        } else if (event.type == VideoEvents.POINT_AUTO_COMPLETE) {
            Log.i("Video Event", "POINT_AUTO_COMPLETE" + " title is : " + event.obj + " url is : " + event.obj1);
        } else if (event.type == VideoEvents.POINT_AUTO_COMPLETE_FULLSCREEN) {
            Log.i("Video Event", "POINT_AUTO_COMPLETE_FULLSCREEN" + " title is : " + event.obj + " url is : " + event.obj1);
        } else if (event.type == VideoEvents.POINT_ENTER_FULLSCREEN) {
            Log.i("Video Event", "POINT_ENTER_FULLSCREEN" + " title is : " + event.obj + " url is : " + event.obj1);
        } else if (event.type == VideoEvents.POINT_QUIT_FULLSCREEN) {
            Log.i("Video Event", "POINT_QUIT_FULLSCREEN" + " title is : " + event.obj + " url is : " + event.obj1);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
