package top.yzlin.doubancrawler.crawler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * 图片下载类
 * @author yzlin
 */
public class ImgDownLand {
    private static class Instance{
        private static ImgDownLand newInstance=new ImgDownLand();
    }
    private ImgDownLand(){}

    /**
     * 单例
     * @return 获取实例
     */
    public static ImgDownLand getInstance(){
        return Instance.newInstance;
    }

    private Map<String,Bitmap> imgMap=new Hashtable<>();//图片容器

    /**
     * 获取图片
     * @param url 图片地址
     * @return 图片
     */
    public Bitmap getImg(String url){
        if(!imgMap.containsKey(url)){
            imgMap.put(url,downLandImg(url));
        }
        return imgMap.get(url);
    }

    /**
     * 下载图片
     * @param url 图片地址
     * @return 图片
     */
    private Bitmap downLandImg(String url){
        try (InputStream i=new URL(url).openStream()){
            return BitmapFactory.decodeStream(i);
        } catch (IOException e) {
            return null;
        }
    }
}

