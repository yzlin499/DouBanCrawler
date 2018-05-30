package top.yzlin.doubancrawler.crawler;

import android.os.Handler;

import top.yzlin.doubancrawler.info.MovieInfo;
import top.yzlin.doubancrawler.info.SimpleMovieInfo;

import java.util.function.Function;

public interface MovieInfoInterface {
    /**
     * 获得电影完整信息
     * @param movieID
     */
    void getMovieInfo(int movieID, Handler handler);

    /**
     * 获得电影完整信息
     * @param simpleMovieInfo
     */
    void getMovieInfo(SimpleMovieInfo simpleMovieInfo, Handler handler);

    /**
     * 更新一部电影的信息
     * @param movieID
     * @return
     */
    void updateMovieInfo(int movieID,Handler handler);

    /**
     * 清除整个缓存池
     */
    void clear();
}
