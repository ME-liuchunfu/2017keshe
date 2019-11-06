package xin.spring.video.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import xin.spring.video.R;
import xin.spring.video.bean.Video;
import xin.spring.video.utils.AppSimgleUtils;
import xin.spring.video.utils.CircleTransform;

/**
 * 视频RecyclerView适配器
 */
public class VideoRecyclerAdapter extends RecyclerView.Adapter<VideoRecyclerAdapter.VideoViewHolder> {

    private List<Video> videos = new ArrayList<Video>();

    private Context context;
    private Picasso picasso;

    public VideoRecyclerAdapter(List<Video> videos, Context context){
        this.videos = videos;
        this.context = context;
        picasso = AppSimgleUtils.registerPicasso(context);
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item_layout, parent, false);
        VideoViewHolder holder = new VideoViewHolder(view);
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
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    /**
     * 视频项
     */
    public static class VideoViewHolder extends RecyclerView.ViewHolder{

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
         * 视频播放器
         */
        private JCVideoPlayer jcVideoPlayer;

        public VideoViewHolder(View itemView) {
            super(itemView);
            topIcon = (ImageView)itemView.findViewById(R.id.v_i_t);
            topTitle = (TextView) itemView.findViewById(R.id.v_t_t);
            title = (TextView) itemView.findViewById(R.id.v_tv_title);
            jcVideoPlayer = (JCVideoPlayer)itemView.findViewById(R.id.v_jcp);
        }

    }

}
