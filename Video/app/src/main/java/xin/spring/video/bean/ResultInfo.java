package xin.spring.video.bean;

import java.io.Serializable;

/**
 * 数据信息
 */
public class ResultInfo implements Serializable {

    /**
     * vendor : six.jie.c
     * count : 2000
     * page : 100
     * maxid : 1573014422
     * maxtime : 1573014422
     */

    private String vendor;
    private Integer count;
    private Integer page;
    private String maxid;
    private String maxtime;

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getMaxid() {
        return maxid;
    }

    public void setMaxid(String maxid) {
        this.maxid = maxid;
    }

    public String getMaxtime() {
        return maxtime;
    }

    public void setMaxtime(String maxtime) {
        this.maxtime = maxtime;
    }

    @Override
    public String toString() {
        return "ResultInfo{" +
                "vendor='" + vendor + '\'' +
                ", count=" + count +
                ", page=" + page +
                ", maxid='" + maxid + '\'' +
                ", maxtime='" + maxtime + '\'' +
                '}';
    }
}
