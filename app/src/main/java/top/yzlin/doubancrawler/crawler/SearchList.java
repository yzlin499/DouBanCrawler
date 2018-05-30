package top.yzlin.doubancrawler.crawler;

import top.yzlin.doubancrawler.info.MovieInfo;
import top.yzlin.doubancrawler.info.SimpleMovieInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class SearchList {
    private int page=0;
    private Function<Integer,Collection<SimpleMovieInfo>> getNewPage;
    private List<SimpleMovieInfo> searchList=new ArrayList<>();
    private boolean noFull=true;

    public SearchList(Function<Integer,Collection<SimpleMovieInfo>> getNewPage){
        this.getNewPage=getNewPage;
        get(0);
    }


    public SimpleMovieInfo get(int index){
        if (searchList==null){
            return null;
        }
        while(noFull && index>=searchList.size()){
            Collection<SimpleMovieInfo> temp=getNewPage.apply(page++);
            if(temp.size()==0){
                noFull=false;
                break;
            }
            searchList.addAll(temp);
        }
        if(index<size()){
            return searchList.get(index);
        }else{
            return null;
        }
    }

    /**
     * 获得一个区间
     * @param start 包含
     * @param end 不包含
     * @return
     */
    public SimpleMovieInfo[] get(int start,int end){
        if (searchList==null){
            return null;
        }
        SimpleMovieInfo[] movieInfo=new SimpleMovieInfo[end-start];
        for(int i=0,j=movieInfo.length;i<j;i++){
            movieInfo[i]=get(start++);
            if(movieInfo[i]==null){
                return Arrays.copyOf(movieInfo,i);
            }
        }
        return movieInfo;
    }

    /**
     * 更新一页数据
     */
    public void updatePage(){
        if(noFull){
            Collection<SimpleMovieInfo> temp=getNewPage.apply(page++);
            if(temp.size()==0){
                noFull=false;
            }
            searchList.addAll(temp);
        }
    }

    public void clear(){
        searchList.clear();
        getNewPage=null;
        searchList=null;
    }

    public int size(){
        return searchList!=null?searchList.size():-1;
    }
}
