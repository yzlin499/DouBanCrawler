package top.yzlin.doubancrawler.crawler;

import android.os.Handler;

/**
 * 评论获取接口
 * @author yzlin
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
     * @param handler 事件
     */
    void nextPage(Handler handler);

    /**
     * 设置一次获取多少评论
     * @param count 数量
     */
    void setCount(int count);

    /**
     * 获取多少
     * @return 数量
     */
    int getCount();
}
