package top.yzlin.doubancrawler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.StringJoiner;

import top.yzlin.doubancrawler.crawler.ImgDownLandAdapter;
import top.yzlin.doubancrawler.crawler.ImgDownLandInterface;
import top.yzlin.doubancrawler.info.SimpleMovieInfo;

/**
 * Created by ASUS on 2018/5/25.
 */

public class ListAdapter extends BaseAdapter {
    private ImgDownLandInterface imgDownLandAdapter=new ImgDownLandAdapter();
    ArrayList<SimpleMovieInfo> arrayList;
    Context context;
    public ListAdapter(ArrayList<SimpleMovieInfo> arrayList, Context context){
        this.context=context;
        this.arrayList=arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("HandlerLeak")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1= LayoutInflater.from(context).inflate(R.layout.item , viewGroup, false);
        //电影标题
        TextView Title=view1.findViewById(R.id.txtTitle);
        Title.setText(arrayList.get(i).getMovieTitle());
        //年份
        TextView Year=view1.findViewById(R.id.txtYear);
        Year.setText(Integer.toString(arrayList.get(i).getYear()));
        //导演
        StringJoiner joiner=new StringJoiner("/");
        for (String t:arrayList.get(i).getDirectors()) {
            joiner.add(t);
        }
        TextView Director=view1.findViewById(R.id.txtDirector);
        Director.setText(joiner.toString());
        //演员
        joiner=new StringJoiner("/");
        for (String t:arrayList.get(i).getMainActors()) {
            joiner.add(t);
        }
        TextView Actors=view1.findViewById(R.id.txtActors);
        Actors.setText(joiner.toString());
        //类型
        TextView Types=view1.findViewById(R.id.txtTypes);
        joiner=new StringJoiner("/");
        for (String t:arrayList.get(i).getTypes()) {
            joiner.add(t);
        }
        Types.setText(joiner.toString());

        ImageView imageView=view1.findViewById(R.id.listCover);
        imgDownLandAdapter.getImg(arrayList.get(i).getCover(),new Handler(){
            @Override
            public void handleMessage(Message msg) {
                imageView.setImageBitmap((Bitmap) msg.obj);
            }
        });

        return view1;
    }
}
