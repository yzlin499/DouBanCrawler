package top.yzlin.doubancrawler.crawler;

import android.os.Handler;
import android.os.Message;


/**
 * 图片下载适配安卓
 * @author yzlin
 */
public class ImgDownLandAdapter implements ImgDownLandInterface {
    private ImgDownLand imgDownLand=ImgDownLand.getInstance();
    @Override
    public void getImg(String url, Handler handler){
        new Thread(()->{
            Message message=new Message();
            message.obj=imgDownLand.getImg(url);
            handler.sendMessage(message);
        }).start();
    }
}
