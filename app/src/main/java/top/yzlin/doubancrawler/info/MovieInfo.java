package top.yzlin.doubancrawler.info;

import java.util.Arrays;

public class MovieInfo extends SimpleMovieInfo {
    private String[] scriptWriters;
    private String[] releaseTimes;
    private int filmLength;
    private String summary;

    public MovieInfo(){}

    public MovieInfo(SimpleMovieInfo simpleMovieInfo){
        this.movieID=simpleMovieInfo.movieID;
        this.movieTitle=simpleMovieInfo.movieTitle;
        this.year=simpleMovieInfo.year;
        this.cover=simpleMovieInfo.cover;
        this.directors=simpleMovieInfo.directors;
        this.mainActors=simpleMovieInfo.mainActors;
        this.types=simpleMovieInfo.types;
        this.average=simpleMovieInfo.average;
    }

    public String[] getScriptWriters() {
        return scriptWriters;
    }

    public void setScriptWriters(String[] scriptWriters) {
        this.scriptWriters = scriptWriters;
    }

    public String[] getReleaseTimes() {
        return releaseTimes;
    }

    public void setReleaseTimes(String[] releaseTimes) {
        this.releaseTimes = releaseTimes;
    }

    public int getFilmLength() {
        return filmLength;
    }

    public void setFilmLength(int filmLength) {
        this.filmLength = filmLength;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "MovieInfo{" +
                "\nmovieID=" + movieID +
                ", \nmovieTitle='" + movieTitle + '\'' +
                ", \nyear=" + year +
                ", \ncover='" + cover + '\'' +
                ", \ndirectors=" + Arrays.toString(directors) +
                ", \nscriptWriters=" + Arrays.toString(scriptWriters) +
                ", \nmainActors=" + Arrays.toString(mainActors) +
                ", \ntypes=" + Arrays.toString(types) +
                ", \nreleaseTimes=" + Arrays.toString(releaseTimes) +
                ", \nfilmLength=" + filmLength +
                ", \naverage='" + average + '\'' +
                ", \nsummary='" + summary + '\'' +
                '}';
    }
}
