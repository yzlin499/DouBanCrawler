package top.yzlin.doubancrawler.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import top.yzlin.doubancrawler.info.MovieInfo;
import top.yzlin.doubancrawler.info.SimpleMovieInfo;
import top.yzlin.doubancrawler.tools.NetTools;

import java.util.HashMap;
import java.util.Map;

/**
 * 电影信息解析
 * @author yzlin
 */
public class MovieParsing {
    private static class Instance{
        private static MovieParsing newInstance=new MovieParsing();
    }
    private MovieParsing(){}

    private Map<Integer,MovieInfo> movieInfoMap=new HashMap<>();

    /**
     * 获取单例对象
     * @return
     */
    public static MovieParsing getInstance(){
        return Instance.newInstance;
    }

    /**
     * 如果在信息池里面有就返回一个原先生成的信息实例，如果没有就返回一个新解析的实例
     * @param movieID
     * @return
     */
    public MovieInfo getMovieInfo(int movieID){
        return movieInfoMap.containsKey(movieID) ? movieInfoMap.get(movieID) : updateMovieInfo(movieID);
    }

    /**
     * 如果在信息池里面有就返回一个原先生成的信息实例，如果没有就返回一个新解析的实例
     * @param movie
     * @return
     */
    public MovieInfo getMovieInfo(SimpleMovieInfo movie){
        if(movieInfoMap.containsKey(movie.getMovieID())){
            return movieInfoMap.get(movie.getMovieID());
        }else{
            Document doc = Jsoup.parse(NetTools.sendGet("https://movie.douban.com/subject/"+movie.getMovieID()+"/"));
            MovieInfo movieInfo=new MovieInfo(movie);
            Element content=doc.getElementById("content");
            Element info=content.getElementById("info");
            generalParsingFunction(content,info,movieInfo);
            return movieInfo;
        }
    }


    /**
     * 解析函数，将一部电影封装成bean
     * @param movieID 电影ID
     * @return
     */
    private MovieInfo parsingFunction(int movieID){
        Document doc = Jsoup.parse(NetTools.sendGet("https://movie.douban.com/subject/"+movieID+"/"));
        MovieInfo movieInfo=new MovieInfo();

        Element content=doc.getElementById("content");
        Element info=content.getElementById("info");

        generalParsingFunction(content,info,movieInfo);

        //电影标题
        String movieTitle=content.child(1).child(0).text();

        //电影年份
        int year=Integer.parseInt(content.child(1).child(1).text().substring(1,5));

        //电影封面
        String cover=content.getElementsByClass("nbgnbg").get(0).child(0).attr("src");

        //设置类型
        String[] types=info.getElementsByAttributeValue("property","v:genre").stream()
                .map(Element::text)
                .toArray(String[]::new);

        //设置评分
        String average=content.getElementsByAttributeValue("property","v:average").get(0).text();

        movieInfo.setMovieID(movieID);
        movieInfo.setMovieTitle(movieTitle);
        movieInfo.setYear(year);
        movieInfo.setCover(cover);
        movieInfo.setTypes(types);
        movieInfo.setAverage(average);
        return movieInfo;
    }


    /**
     * 设置几项属性
     * @param content
     * @param info
     * @param movieInfo
     */
    private void generalParsingFunction(Element content,Element info,MovieInfo movieInfo) {
        //设置导演，编剧和主演
        for(Element pl:info.getElementsByClass("pl")){
            String p=pl.text();
            Elements e=pl.nextElementSibling().getElementsByTag("a");
            String peoples[]=new String[e.size()];
            for(int i=0,j=e.size();i<j;i++){
                peoples[i]=e.get(i).text();
            }
            switch (p){
                case "导演":movieInfo.setDirectors(peoples);break;
                case "编剧":movieInfo.setScriptWriters(peoples);break;
                case "主演":movieInfo.setMainActors(peoples);break;
            }
        }

        //设置上映时间
        String[] initialReleaseDate=info.getElementsByAttributeValue("property","v:initialReleaseDate").stream()
                .map(Element::text)
                .toArray(String[]::new);

        //设置电影时长
        int filmLength=0;
        try{
            filmLength=Integer.parseInt(info.getElementsByAttributeValue("property","v:runtime").get(0).attr("content"));
        }catch (IndexOutOfBoundsException e){
            filmLength=0;
        }

        //设置简介
        String summary="";
        try {
            summary = content.getElementsByAttributeValue("property", "v:summary").get(0).text();
        }catch (IndexOutOfBoundsException e){
            summary=content.getElementsByAttributeValue("property", "v:summary").text();
        }

        movieInfo.setReleaseTimes(initialReleaseDate);
        movieInfo.setFilmLength(filmLength);
        movieInfo.setSummary(summary);
    }


    /**
     * 更新电影，用于更新一部电影的缓存，并且获得更新后的电影
     * @param movieID
     */
    public MovieInfo updateMovieInfo(int movieID){
        MovieInfo tmp=parsingFunction(movieID);
        movieInfoMap.put(movieID,tmp);
        return tmp;
    }

    /**
     * 清除缓存池
     */
    public void clearAll(){
        movieInfoMap.clear();
    }


}
