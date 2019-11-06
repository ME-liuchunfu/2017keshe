package xin.spring.video.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 视频列表数据
 */
public class VideoResult implements Serializable {

    private ResultInfo info;

    private List<Video> list;


    public ResultInfo getInfo() {
        return info;
    }

    public void setInfo(ResultInfo info) {
        this.info = info;
    }

    public List<Video> getList() {
        return list;
    }

    public void setList(List<Video> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "VideoResult{" +
                "info=" + info +
                ", list=" + list +
                '}';
    }
}
