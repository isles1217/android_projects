package com.example.android.justlikethemovies.utils;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by izilladotcom on 11/14/17.
 */

public class Movie implements Serializable {

  public Movie() {
  }

  private String id;
  private String title;
  private String origTitle;
  private String popularity;
  private String posterPath;
  private String backdropPath;
  private Long voteCount;
  private Double voteAvg;
  private String origLanguage;
  private String[] genres;
  private Boolean isAdult;
  private Boolean hasVideo;
  private String overview;
  private String releaseDate;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getPopularity() {
    return popularity;
  }

  public void setPopularity(String popularity) {
    this.popularity = popularity;
  }

  public String getPosterPath() {
    return posterPath;
  }

  public void setPosterPath(String posterPath) {
    this.posterPath = posterPath;
  }

  public String getBackdropPath() {
    return backdropPath;
  }

  public void setBackdropPath(String backdropPath) {
    this.backdropPath = backdropPath;
  }

  public Long getVoteCount() {
    return voteCount;
  }

  public void setVoteCount(Long voteCount) {
    this.voteCount = voteCount;
  }

  public Double getVoteAvg() {
    return voteAvg;
  }

  public void setVoteAvg(Double voteAvg) {
    this.voteAvg = voteAvg;
  }

  public String getOrigLanguage() {
    return origLanguage;
  }

  public void setOrigLanguage(String origLanguage) {
    this.origLanguage = origLanguage;
  }

  public String[] getGenres() {
    return genres;
  }

  public void setGenres(String[] genres) {
    this.genres = genres;
  }

  public Boolean getAdult() {
    return isAdult;
  }

  public void setAdult(Boolean adult) {
    isAdult = adult;
  }

  public Boolean getHasVideo() {
    return hasVideo;
  }

  public void setHasVideo(Boolean hasVideo) {
    this.hasVideo = hasVideo;
  }

  public String getOverview() {
    return overview;
  }

  public void setOverview(String overview) {
    this.overview = overview;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
  }

  public String getOrigTitle() {
    return origTitle;
  }

  public void setOrigTitle(String origTitle) {
    this.origTitle = origTitle;
  }
}
