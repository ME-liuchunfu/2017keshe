package xin.spring.video.aip;

/**
 * app api接口
 */
public class AppApi {

    /**
     * 基础
     */
    public static final String BASE_URL = "http://api.budejie.com/api/api_open.php?";

    /**
     * 首页
     * type=1 : 全部
     * type=41 : 视频
     * type=10 : 图片
     * type=29 : 段子
     * type=31 : 声音
     * 加载更多 : 添加两个字段
     *      page : 页码 (加载下一页需要)
     *      maxtime : 获取到的最后一条数据的maxtime字段 (加载下一页需要)
     */
    public static final String LIST_URL = BASE_URL + "a=list&c=data&type=";

    public static final String PAGE_KEY = "&page=";

    public static final String MAXTIME_KEY = "&maxtime=";

    /**
     * 评论列表http://api.budejie.com/api/api_open.php?a=dataList&c=comment&data_id=22062938&hot=1
     * data_id : 帖子ID
     * hot : 获取到最热评论需要这个字段
     * page : 页码 (加载下一页需要)
     * lastcid : 获取到的最后一条评论的ID(加载下一页需要)
     */
    public static final String COMMENT_URL = BASE_URL + "a=dataList&hot=1&c=comment&data_id=";

    /**
     * 左侧列表 - 推荐关注http://api.budejie.com/api/api_open.php?a=category&c=subscribe
     */
    public static final String LEFT_SUBSCRIBE = BASE_URL + "a=category&c=subscribe";

    /**
     * category_id : 左侧栏目 ID
     * page : 当前页码 ,请求第一页数据的时候可不填
     * 右侧列表 - 推荐关注http://api.budejie.com/api/api_open.php?a=list&c=subscribe&category_id=35
     */
    public static final String RIGHT_SUBSCRIBE = BASE_URL + "a=list&c=subscribe&category_id=";

}
