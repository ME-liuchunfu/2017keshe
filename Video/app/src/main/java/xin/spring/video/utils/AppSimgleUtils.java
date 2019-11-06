package xin.spring.video.utils;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.lzy.okgo.model.HttpHeaders;
import com.squareup.picasso.Picasso;

/**
 * 工具类
 */
public class AppSimgleUtils {


    public static Picasso registerPicasso(Context context){
        Picasso picasso = new Picasso.Builder(context).build();
        return picasso;
    };

    public static Picasso registerPicasso(Context context, ImageView view, String uri){
        Picasso picasso = new Picasso.Builder(context).build();
        picasso.load(uri).into(view);
        return picasso;
    };


    public static HttpHeaders registerHttpHeaders(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put("Upgrade-Insecure-Requests", "1");
        httpHeaders.put("Connection", "Keep-Alive");
        httpHeaders.put("Content-Type", "application/json");
        httpHeaders.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
        httpHeaders.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.70 Safari/537.36");
        return httpHeaders;
    }
}
