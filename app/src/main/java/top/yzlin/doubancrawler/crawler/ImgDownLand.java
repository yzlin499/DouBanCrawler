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
 * Created by yzlin on 2018/5/30.
 */

public class ImgDownLand {
    private static class Instance{
        private static ImgDownLand newInstance=new ImgDownLand();
    }
    private ImgDownLand(){}

    public static ImgDownLand getInstance(){
        return Instance.newInstance;
    }

    private Map<String,Bitmap> imgMap=new Hashtable<>();

    public Bitmap getImg(String url){
        if(!imgMap.containsKey(url)){
            imgMap.put(url,downLandImg(url));
        }
        return imgMap.get(url);
    }

    private Bitmap downLandImg(String url){
        try (InputStream i=new URL(url).openStream()){
            return BitmapFactory.decodeStream(i);
        } catch (IOException e) {
            return null;
        }
    }
}

