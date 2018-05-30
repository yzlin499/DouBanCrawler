package top.yzlin.doubancrawler.crawler;

import android.os.Handler;
import android.os.Message;

import top.yzlin.doubancrawler.info.SimpleMovieInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class SearchListAdapter implements SearchInterface{
    private SearchListCreate searchListCreate=SearchListCreate.getInstance();
    private SearchList searchList;
    private int count=getCount();
    private ArrayList<SimpleMovieInfo> searchResultList=new ArrayList<>(count);

    @Override
    public void searchMovie(String searchKey, Handler handler) {
        new Thread(()->{
            searchList=searchListCreate.searchMovie(searchKey);
            synchronized (searchResultList) {
                searchResultList.addAll(Arrays.asList(searchList.get(0, count)));
                Message message=new Message();
                message.obj=searchResultList;
                handler.sendMessage(message);
            }
        }).start();
    }

    @Override
    public void nextPage(Handler handler) {
        new Thread(()->{
            synchronized (searchResultList) {
                if(searchList!=null){
                    searchList.updatePage();
                    searchResultList.addAll(Arrays.asList(searchList.get(searchResultList.size(), searchList.size())));
                    Message message=new Message();
                    message.obj=searchResultList;
                    handler.sendMessage(message);
                }
            }
        }).start();
    }

    @Override
    public void setCount(int count) {
        this.count=count;
        searchListCreate.setCount(count);
    }

    @Override
    public int getCount() {
        return searchListCreate.getCount();
    }

}
