package top.yzlin.doubancrawler.crawler;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import top.yzlin.doubancrawler.info.SimpleMovieInfo;
import top.yzlin.doubancrawler.tools.NetTools;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 用来创建与控制搜索结果
 * @author yzlin
 */
public class SearchListCreate {
    private static class Instance{
        private static SearchListCreate newInstance=new SearchListCreate();
    }
    private SearchListCreate(){}
    /**
     * 获取单例对象
     * @return
     */
    public static SearchListCreate getInstance(){
        return Instance.newInstance;
    }

    private String keyWord;
    private SearchList searchList;
    private int count=20;

    public void setCount(int count){
        this.count=count;
    }

    public int getCount(){
        return count;
    }

    public SearchList searchMovie(String keyWord){
        this.keyWord=keyWord;
        if(searchList!=null){
            searchList.clear();
        }
        searchList =new SearchList(this::newPage);
        return searchList;
    }


    /**
     * 获取下一页
     * @param page
     * @return
     */
    private List<SimpleMovieInfo> newPage(Integer page){
        try {
            JSONObject data = new JSONObject(NetTools.sendGet(DouBanApiUrl.search(keyWord, page * count, count)));
            JSONArray subjectsJA = data.getJSONArray("subjects");
            JSONObject[] subjects=new JSONObject[subjectsJA.length()];
            for(int i=0;i<subjects.length;i++){
                subjects[i]=subjectsJA.getJSONObject(i);
            }
            return Stream.of(subjects)
                    .map(j -> {
                        try {
                            JSONArray ja = j.getJSONArray("genres");
                            String[] types = new String[ja.length()];
                            for(int i=0;i<types.length;i++){
                                types[i]=ja.getString(i);
                            }

                            ja = j.getJSONArray("casts");
                            String[] mainActors = new String[ja.length()];
                            for (int i = 0, size = mainActors.length; i < size; i++) {
                                mainActors[i] = ja.getJSONObject(i).getString("name");
                            }

                            ja = j.getJSONArray("directors");
                            String[] directors = new String[ja.length()];
                            for (int i = 0, size = directors.length; i < size; i++) {
                                directors[i] = ja.getJSONObject(i).getString("name");
                            }

                            SimpleMovieInfo simpleMovieInfo = new SimpleMovieInfo();
                            simpleMovieInfo.setMovieID(j.getInt("id"));
                            simpleMovieInfo.setAverage(j.getJSONObject("rating").getString("average"));
                            simpleMovieInfo.setYear(j.getInt("year"));
                            simpleMovieInfo.setCover(j.getJSONObject("images").getString("large"));
                            simpleMovieInfo.setMovieTitle(j.getString("title"));
                            simpleMovieInfo.setTypes(types);
                            simpleMovieInfo.setDirectors(directors);
                            simpleMovieInfo.setMainActors(mainActors);
                            return simpleMovieInfo;
                        }catch (JSONException e){
                            return null;
                        }
                    }).collect(Collectors.toList());
        }catch (JSONException e){
            return null;
        }
    }

}
