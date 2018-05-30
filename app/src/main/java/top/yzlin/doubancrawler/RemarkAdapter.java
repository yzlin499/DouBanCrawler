package top.yzlin.doubancrawler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import top.yzlin.doubancrawler.info.Comment;

/**
 * Created by Administrator on 2018/5/25.
 */


public class RemarkAdapter extends BaseAdapter{
    private List<Comment> remarkList;
    private Context context;
    private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    public RemarkAdapter(Context context, List<Comment> remarkList){
        this.context=context;
        this.remarkList=remarkList;
    }
    @Override
    public int getCount(){
        return remarkList.size();
    }

    @Override
    public Object getItem(int i){
        return remarkList.get(i);
    }

    @Override
    public long getItemId(int i){
        return i;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v=LayoutInflater.from(context).inflate(R.layout.remark_item,viewGroup,false);
        ((TextView)v.findViewById(R.id.username)).setText(remarkList.get(i).getName());
        ((TextView)v.findViewById(R.id.time)).setText(sdf.format(remarkList.get(i).getCreatedTime()));
        ((TextView)v.findViewById(R.id.remark)).setText(remarkList.get(i).getContent());
        return v;
    }


}
