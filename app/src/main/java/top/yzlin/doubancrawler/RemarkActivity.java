package top.yzlin.doubancrawler;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.List;

import top.yzlin.doubancrawler.crawler.CommentAdapter;
import top.yzlin.doubancrawler.crawler.CommentListInterface;
import top.yzlin.doubancrawler.info.Comment;

/**
 * 评论界面
 * @author huke
 */
public class RemarkActivity extends AppCompatActivity {
    CommentListInterface commentListInterface=new CommentAdapter();
    RemarkAdapter remarkadapter;
    ListView listView;
    Handler handler;
    Handler nextPage;
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remark_activity);
        listView= (ListView) findViewById(R.id.remarkList);
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg){
                remarkadapter=new RemarkAdapter(RemarkActivity.this,(List<Comment>)msg.obj) ;
                listView.setAdapter(remarkadapter);
                remarkadapter.notifyDataSetChanged();
            }
        };

        nextPage=new Handler(){
            @Override
            public void handleMessage(Message msg){
                remarkadapter.notifyDataSetChanged();
            }
        };

        listView.setOnScrollListener(new AbsListView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState){
                // 当不滚动时
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    // 判断是否滚动到底部
                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
                        commentListInterface.nextPage(nextPage);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        commentListInterface.getCommentList(getIntent().getIntExtra("movieID",0),handler);
    }
}
