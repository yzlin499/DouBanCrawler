package top.yzlin.doubancrawler.info;

import java.util.Arrays;

/**
 * 电影的简单实体类
 * @author yzlin
 */
public class SimpleMovieInfo {
    protected int movieID;
    protected String movieTitle;
    protected int year;
    protected String cover;
    protected String[] directors;
    protected String[] mainActors;
    protected String[] types;
    protected String average;

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String[] getDirectors() {
        return directors;
    }

    public void setDirectors(String[] directors) {
        this.directors = directors;
    }

    public String[] getMainActors() {
        return mainActors;
    }

    public void setMainActors(String[] mainActors) {
        this.mainActors = mainActors;
    }

    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }
}
