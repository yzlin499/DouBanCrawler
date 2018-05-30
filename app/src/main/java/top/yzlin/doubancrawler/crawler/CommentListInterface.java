package top.yzlin.doubancrawler.crawler;

import android.os.Handler;

/**
 * Created by yzlin on 2018/5/16.
 */

public interface CommentListInterface {
    /**
     * 搜索
     * @param movieID 搜索关键词
     * @param handler 搜索结束之后，会调用这个函数
     */
    void getCommentList(int movieID,  Handler handler);

    /**
     * 搜索下一页
     * @param handler
     */
    void nextPage(Handler handler);

    /**
     * 设置一次搜索多少
     * @param count
     */
    void setCount(int count);

    /**
     * 获取搜索多少
     * @return
     */
    int getCount();
}
