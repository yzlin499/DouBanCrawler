package top.yzlin.doubancrawler.crawler;

import android.os.Handler;
import android.os.Message;

import top.yzlin.doubancrawler.info.MovieInfo;
import top.yzlin.doubancrawler.info.SimpleMovieInfo;

import java.util.function.Function;

public class MovieInfoAdapter implements MovieInfoInterface {
    private MovieParsing movieParsing=MovieParsing.getInstance();

    @Override
    public void getMovieInfo(int movieID, Handler handler) {
        new Thread(()-> {
            Message message=new Message();
            message.obj=movieParsing.getMovieInfo(movieID);
            handler.sendMessage(message);
        }).start();
    }

    @Override
    public void getMovieInfo(SimpleMovieInfo simpleMovieInfo,Handler handler) {
        new Thread(()-> {
            Message message=new Message();
            message.obj=movieParsing.getMovieInfo(simpleMovieInfo);
            handler.sendMessage(message);
        }).start();
    }

    @Override
    public void updateMovieInfo(int movieID,Handler handler) {
        new Thread(()-> {
            Message message=new Message();
            message.obj=movieParsing.updateMovieInfo(movieID);
            handler.sendMessage(message);
        }).start();
    }

    @Override
    public void clear() {
        movieParsing.clearAll();
    }
}
