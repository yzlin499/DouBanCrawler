package top.yzlin.doubancrawler.crawler;

import android.os.Handler;
import android.os.Message;


/**
 * Created by yzlin on 2018/5/30.
 */

public class ImgDownLandAdapter implements ImgDownLandInterface {
    ImgDownLand imgDownLand=ImgDownLand.getInstance();

    @Override
    public void getImg(String url, Handler handler){
        new Thread(()->{
            Message message=new Message();
            message.obj=imgDownLand.getImg(url);
            handler.sendMessage(message);
        }).start();
    }
}
