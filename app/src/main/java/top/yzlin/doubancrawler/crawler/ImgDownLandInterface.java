package top.yzlin.doubancrawler.crawler;

import android.os.Handler;

/**
 * 图片下载接口
 * @author yzlin
 */
public interface ImgDownLandInterface {
    /**
     * 获取图片
     * @param url 图片地址
     * @param handler 事件
     */
    void getImg(String url, Handler handler);
}
