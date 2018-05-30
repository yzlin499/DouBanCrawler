package top.yzlin.doubancrawler.crawler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import top.yzlin.doubancrawler.info.Comment;
import top.yzlin.doubancrawler.info.SimpleMovieInfo;
import top.yzlin.doubancrawler.tools.NetTools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommentList {
    private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private int page=0;
    private List<Comment> commentList =new ArrayList<>();
    private boolean noFull=true;
    private int count=20;
    private int movieID;

    public CommentList(SimpleMovieInfo simpleMovieInfo){
        this(simpleMovieInfo.getMovieID());
    }

    public CommentList(int movieID){
        this.movieID=movieID;
        get(0);
    }

    public void setCount(int count){
        this.count=count;
    }

    public int getCount(){
        return count;
    }

    /**
     * 获得数据
     * @param index
     * @return
     */
    public Comment get(int index){
        if (commentList ==null){
            return null;
        }
        while(noFull && index>= commentList.size()){
            Collection<Comment> temp=newPage(page++);
            if(temp.size()==0){
                noFull=false;
                break;
            }
            commentList.addAll(temp);
        }
        if(index<size()){
            return commentList.get(index);
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
    public Comment[] get(int start, int end){
        if (commentList ==null){
            return null;
        }
        Comment[] comments=new Comment[end-start];
        for(int i=0,j=comments.length;i<j;i++){
            comments[i]=get(start++);
            if(comments[i]==null){
                return Arrays.copyOf(comments,i);
            }
        }
        return comments;
    }

    /**
     * 更新一页数据
     */
    public void updatePage(){
        if(noFull){
            Collection<Comment> temp=newPage(page++);
            if(temp.size()==0){
                noFull=false;
            }
            commentList.addAll(temp);
        }
    }

    public void clear(){
        commentList.clear();
        commentList =null;
    }

    public int size(){
        return commentList !=null? commentList.size():-1;
    }

    private List<Comment> newPage(Integer page){
        try {
            JSONObject data = new JSONObject(NetTools.sendGet(DouBanApiUrl.comment(movieID,page * count, count)));
            JSONArray commentJA = data.getJSONArray("comments");
            JSONObject[] comments=new JSONObject[commentJA.length()];
            for(int i=0;i<comments.length;i++){
                comments[i]=commentJA.getJSONObject(i);
            }
            return Stream.of(comments)
                    .map(c->{
                        try {
                            Comment comment=new Comment();
                            comment.setRating(c.getJSONObject("rating").getInt("value"));
                            comment.setContent(c.getString("content"));
                            comment.setName(c.getJSONObject("author").getString("name"));
                            comment.setCreatedTime(df.parse(c.getString("created_at")));
                            return comment;
                        }catch (JSONException e){
                            return null;
                        } catch (ParseException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }).collect(Collectors.toList());
        }catch (JSONException e){
            return null;
        }
    }
}
