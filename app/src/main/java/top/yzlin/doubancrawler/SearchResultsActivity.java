package top.yzlin.doubancrawler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.NumberPicker;

import java.util.ArrayList;

import top.yzlin.doubancrawler.crawler.SearchInterface;
import top.yzlin.doubancrawler.crawler.SearchListAdapter;
import top.yzlin.doubancrawler.info.SimpleMovieInfo;

/**
 * @author 丁溢
 */

public class SearchResultsActivity extends Activity {
    private ListView lv;
    private ListAdapter listAdapter;
    private SearchInterface searchInterface=new SearchListAdapter();


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg){
            listAdapter=new ListAdapter((ArrayList<SimpleMovieInfo>)msg.obj,SearchResultsActivity.this);
            lv.setAdapter(listAdapter);
        }
    };
    @SuppressLint("HandlerLeak")
    private Handler nextPage=new Handler(){
        public void handleMessage(Message msg){
            listAdapter.notifyDataSetChanged();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_list);

        lv=findViewById(R.id.lv1);

        searchInterface.searchMovie(getIntent().getStringExtra("searchInfo"),handler);
        lv.setOnScrollListener(new AbsListView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState){
                // 当不滚动时
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    // 判断是否滚动到底部
                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
                        searchInterface.nextPage(nextPage);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });

//        点击每个item，跳转到相应界面
        lv.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent ii=new Intent(SearchResultsActivity.this,Movie.class);
            ii.putExtra("movieID",((SimpleMovieInfo)listAdapter.getItem(i)).getMovieID());
            startActivity(ii);
        });
    }
}
