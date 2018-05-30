package top.yzlin.doubancrawler.crawler;

import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.Arrays;

import top.yzlin.doubancrawler.info.Comment;


/**
 * 用于控制类与接口对接
 * @author yzlin
 */
public class CommentAdapter implements CommentListInterface {
    private int movieID=-2147483648;
    private CommentList commentList;

    private int count=20;
    private ArrayList<Comment> commentResultList;
    private Message message=new Message();

    @Override
    public void getCommentList(int movieID, Handler handler) {
        if(this.movieID==movieID){
            handler.sendMessage(message);
        }else{
            this.movieID=movieID;
            new Thread(()->{
                commentList=new CommentList(movieID);
                commentResultList=new ArrayList<>(count);
                synchronized (commentResultList) {
                    commentResultList.addAll(Arrays.asList(commentList.get(0, count)));
                    message.obj=commentResultList;
                    handler.sendMessage(message);
                }
                count=getCount();
            }).start();
        }

    }

    @Override
    public void nextPage(Handler handler) {
        new Thread(()->{
            synchronized (commentResultList) {
                if(commentList!=null){
                    commentList.updatePage();
                    commentResultList.addAll(Arrays.asList(commentList.get(commentResultList.size(), commentList.size())));
                    handler.sendMessage(new Message());
                }
            }
        }).start();
    }

    @Override
    public void setCount(int count) {
        this.count=count;
        commentList.setCount(count);
    }

    @Override
    public int getCount() {
        return commentList.getCount();
    }
}
