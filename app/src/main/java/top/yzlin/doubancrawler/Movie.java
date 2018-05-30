package top.yzlin.doubancrawler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import top.yzlin.doubancrawler.R;
import top.yzlin.doubancrawler.crawler.ImgDownLandAdapter;
import top.yzlin.doubancrawler.crawler.ImgDownLandInterface;
import top.yzlin.doubancrawler.crawler.MovieInfoAdapter;
import top.yzlin.doubancrawler.crawler.MovieInfoInterface;
import top.yzlin.doubancrawler.info.MovieInfo;

/**
 * Created by Administrator on 2018-05-25.
 */

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;
import java.sql.Types;
import java.util.StringJoiner;

import top.yzlin.doubancrawler.crawler.MovieInfoAdapter;
import top.yzlin.doubancrawler.crawler.MovieInfoInterface;
import top.yzlin.doubancrawler.info.MovieInfo;

/**
 * 电影信息显示界面
 * @author yanTaiQin;ASUS
 */
public class Movie extends Activity {
    TextView title;
    TextView year;
    TextView director;
    TextView actors;
    TextView types;
    TextView summary;
    Button button;
    ImageView imageView;
    ImgDownLandInterface imgDownLandAdapter=new ImgDownLandAdapter();

    int movieID;
    MovieInfoInterface movieInfoInterface=new MovieInfoAdapter();

    Handler handler;
    Handler imgHandler;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie);
        title=findViewById(R.id.txtTitle2);
        year=findViewById(R.id.txtYear2);
        director=findViewById(R.id.txtDirector2);
        actors=findViewById(R.id.txtActors2);
        types=findViewById(R.id.txtTypes2);
        summary=findViewById(R.id.txtSummary2);
        button=findViewById(R.id.btn_Comment);
        imageView=findViewById(R.id.movieCover);

        button.setOnClickListener(this::clickComment);
        imgHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                imageView.setImageBitmap((Bitmap)msg.obj);
            }
        };

        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                MovieInfo movieInfo=(MovieInfo)msg.obj;

                title.setText(movieInfo.getMovieTitle());

                year.setText(Integer.toString(movieInfo.getYear()));
                //导演
                director.setText(stringJoin(movieInfo.getDirectors()));
                //类型
                types.setText(stringJoin(movieInfo.getTypes()));
                //主演
                actors.setText(stringJoin(movieInfo.getMainActors()));
                //简介
                summary.setText(movieInfo.getSummary());

                imgDownLandAdapter.getImg(movieInfo.getCover(),imgHandler);

            }
        };


    }

    @SuppressLint("HandlerLeak")
    @Override
    protected void onStart() {
        super.onStart();
        movieID=getIntent().getIntExtra("movieID",0);
        movieInfoInterface.getMovieInfo(movieID,handler);
    }

    private String stringJoin(String[] strings){
        if(strings==null){
            return "";
        }
        StringJoiner joiner=new StringJoiner("/");
        for (String t:strings) {
            joiner.add(t);
        }
        return joiner.toString();
    }

    private void clickComment(View V){
        Intent i=new Intent(this,RemarkActivity.class);
        i.putExtra("movieID",movieID);
        startActivity(i);
    }
}
