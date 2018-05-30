package top.yzlin.doubancrawler.crawler;

import android.os.Handler;

import top.yzlin.doubancrawler.info.SimpleMovieInfo;

import java.util.List;
import java.util.function.Function;

public interface SearchInterface {
    /**
     * 搜索
     * @param searchKey 搜索关键词
     * @param handler 搜索结束之后，会调用这个函数
     */
    void searchMovie(String searchKey,  Handler handler);

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
