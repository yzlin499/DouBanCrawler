package top.yzlin.doubancrawler.crawler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 接口拼接类
 */
public final class DouBanApiUrl {

    /**
     * 生成电影信息的接口URL
     * @param movieID
     * @return
     */
    public static String movieInfo(int movieID){
        return "http://api.douban.com/v2/movie/subject/"+movieID;
    }

    /**
     * 搜索接口
     * @param word
     * @return
     */
    public static String search(String word,int start,int count){
        try {
            return "http://api.douban.com/v2/movie/search?" +
                    "apikey=0b2bdeda43b5688921839c8ecb20399b"+"&"+
                    "q="+URLEncoder.encode(word,"UTF-8")+ "&"+
                    "start="+start+"&"+
                    "count="+count;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获得短评
     * @param movieID ID
     * @param start 从哪一条开始
     * @param count 获取多少条
     * @return 拼接结果
     */
    public static String comment(int movieID,int start,int count){
        return "http://api.douban.com/v2/movie/subject/"+movieID+"/comments?" +
                "apikey=0b2bdeda43b5688921839c8ecb20399b"+ "&"+
                "start="+start+"&"+
                "count="+count;
    }
}
