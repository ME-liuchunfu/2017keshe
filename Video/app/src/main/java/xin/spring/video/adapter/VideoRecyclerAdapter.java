package xin.spring.video.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ixuea.android.downloader.DownloadService;
import com.ixuea.android.downloader.callback.DownloadListener;
import com.ixuea.android.downloader.callback.DownloadManager;
import com.ixuea.android.downloader.domain.DownloadInfo;
import com.ixuea.android.downloader.exception.DownloadException;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import xin.spring.video.R;
import xin.spring.video.bean.Video;
import xin.spring.video.constrant.AppConstrant;
import xin.spring.video.utils.AppSimgleUtils;
import xin.spring.video.utils.CircleTransform;
import xin.spring.video.utils.EmmcSDCard;

/**
 * 视频RecyclerView适配器
 */
public class VideoRecyclerAdapter extends RecyclerView.Adapter<VideoRecyclerAdapter.VideoViewHolder> {

    private List<Video> videos = new ArrayList<Video>();

    private Context context;
    private Picasso picasso;
    private DownloadManager mDownloadManager;

    private ClipboardManager cm;
    private ClipData mClipData;

    public VideoRecyclerAdapter(List<Video> videos, Context context){
        this.videos = videos;
        this.context = context;
        picasso = AppSimgleUtils.registerPicasso(context);
        mDownloadManager = DownloadService.getDownloadManager(context);

        //获取剪贴板管理器：
        cm = (ClipboardManager) this.context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        mClipData = ClipData.newPlainText("快速剪切面板", "哈哈哈~~~");
        cm.setPrimaryClip(mClipData);
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item_layout, parent, false);
        VideoViewHolder holder = new VideoViewHolder(view, context);
        return holder;
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        Video video = videos.get(position);
        //holder.topIcon == picasso
        if(null != video.getProfile_image() && !"".equals(video.getProfile_image())){
            picasso.load(video.getProfile_image()).transform(new CircleTransform()).into(holder.topIcon);
        }else{
            picasso.load(R.drawable.v_icon).transform(new CircleTransform()).into(holder.topIcon);
        }

//        if(null != video.getProfile_image()){
//            Glide.with(context)
//                .load(video.getProfile_image())
//                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
//                .into(holder.topIcon);
//        }else{
//            Glide.with(context)
//                .load(R.drawable.v_icon)
//                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
//                .into(holder.topIcon);
//        }

        holder.topTitle.setText(video.getName() + (!"".equals(video.getTheme_name())? " - " + video.getTheme_name(): ""));
        holder.title.setText(video.getText());
        holder.jcVideoPlayer.setUp(video.getVideouri(), video.getBimageuri(), "");
        holder.good.setOnClickListener(holder);
        holder.lower.setOnClickListener(holder);
        holder.message.setOnClickListener(holder);
        holder.share.setOnClickListener(holder);
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    /**
     * 视频项
     */
    public class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        /**
         * 用户头像
         */
        private ImageView topIcon;

        /**
         * 用户昵称
         */
        private TextView topTitle;

        /**
         * 视频标题
         */
        private TextView title;

        /**
         * 点赞
         */
        private ImageView good;

        /**
         * 吐槽
         */
        private ImageView lower;

        /**
         * 评论
         */
        private ImageView message;

        /**
         * 分享，下载
         */
        private ImageView share;

        /**
         * 视频播放器
         */
        private JCVideoPlayer jcVideoPlayer;

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.iv_main_good:
                    Toast.makeText(context, "您点击了支持", Toast.LENGTH_LONG).show();
                    break;
                case R.id.iv_main_lower:
                    Toast.makeText(context, "您点击了吐槽", Toast.LENGTH_LONG).show();
                    break;
                case R.id.iv_main_message:
                    Toast.makeText(context, "您点击了评论", Toast.LENGTH_LONG).show();
                    copyUrl(getLayoutPosition());
                    break;
                case R.id.iv_main_share:
                    Toast.makeText(context, "您点击了下载", Toast.LENGTH_LONG).show();
                    download(getLayoutPosition());
                    break;
            }
        }

        /**
         * 复制链接
         * @param layoutPosition
         */
        private void copyUrl(int layoutPosition) {
            Video video = videos.get(layoutPosition);
            if(video != null && null != video.getWeixin_url() && !"".equals(video.getWeixin_url())){
                mClipData = ClipData.newRawUri("智能影音", Uri.parse(video.getWeixin_url()));
                cm.setPrimaryClip(mClipData);
                Toast.makeText(context, "复制成功，快去分享吧！", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context, "复制失败", Toast.LENGTH_SHORT).show();
            }
        }

        private void download(int layoutPosition){
            Video video = videos.get(layoutPosition);
            if(video != null && null != video.getVideouri() && !"".equals(video.getVideouri())){
                //create download info set download uri and save path.
                File path = null;
                if(EmmcSDCard.isSDCard()){
                    path = new File(Environment.getExternalStorageDirectory(), AppConstrant.APP_DIR);
                }else{
                    path = new File(context.getFilesDir(), AppConstrant.APP_DIR);
                }
                if(!path.exists()){
                    path.mkdirs();
                }
                System.out.println(path.getAbsolutePath());
                String videoUrl = video.getVideouri();
                int index = videoUrl.lastIndexOf("/");
                DownloadInfo downloadInfo = new DownloadInfo.Builder().setUrl(videoUrl)
                        .setPath( path.getAbsolutePath() + videoUrl.substring(index))
                        .build();
                //set download callback.
                downloadInfo.setDownloadListener(new DownloadListener() {

                    @Override
                    public void onStart() {
                        Toast.makeText(context, "开始下载", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onWaited() {
                        Toast.makeText(context, "等待中", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPaused() {
                        Toast.makeText(context, "暂停", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDownloading(long progress, long size) {
                        System.out.println("下载中....");
                    }

                    @Override
                    public void onRemoved() {
                        //downloadInfo = null;
                    }

                    @Override
                    public void onDownloadSuccess() {
                        Toast.makeText(context, "下载成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDownloadFailed(DownloadException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "异常", Toast.LENGTH_SHORT).show();
                    }
                });

                //submit download info to download manager.
                mDownloadManager.download(downloadInfo);
            }else {
                Toast.makeText(context, "网络异常，请重新刷新", Toast.LENGTH_LONG).show();
            }
        }

        private Context context;

        public VideoViewHolder(View itemView, Context context) {
            super(itemView);
            topIcon = (ImageView)itemView.findViewById(R.id.v_i_t);
            topTitle = (TextView) itemView.findViewById(R.id.v_t_t);
            title = (TextView) itemView.findViewById(R.id.v_tv_title);
            jcVideoPlayer = (JCVideoPlayer)itemView.findViewById(R.id.v_jcp);
            good = (ImageView) itemView.findViewById(R.id.iv_main_good);
            lower = (ImageView)itemView.findViewById(R.id.iv_main_lower);
            message = (ImageView)itemView.findViewById(R.id.iv_main_message);
            share = (ImageView)itemView.findViewById(R.id.iv_main_share);
            this.context = context;
        }

    }

}
