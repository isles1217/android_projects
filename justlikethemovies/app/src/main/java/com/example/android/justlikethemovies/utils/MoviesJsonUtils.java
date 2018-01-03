package com.example.android.justlikethemovies.utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by izilladotcom on 11/13/17.
 */

public class MoviesJsonUtils {

  public static List<Movie> getMovieListFromJson(String rawJsonString)
    throws JSONException {

    List<Movie> movieList = new ArrayList<Movie>();

    final String MOVIE_RESULTS = "results";
    final String MOVIE_ID = "id";
    final String MOVIE_TITLE = "title";
    final String MOVIE_POPULARITY = "popularity";
    final String MOVIE_POSTER_PATH = "poster_path";
    final String MOVIE_BACKDROP_PATH = "backdrop_path";
    final String MOVIE_VOTE_COUNT = "vote_count";
    final String MOVIE_VOTE_AVG = "vote_average";
    final String MOVIE_ORIG_LANGUAGE = "original_language";
    final String MOVIE_ORIG_TITLE =  "original_title";
    final String MOVIE_GENRE_IDS = "genre_ids";
    final String MOVIE_IS_ADULT = "adult";
    final String MOVIE_OVERVIEW = "overview";
    final String MOVIE_RELEASE_DATE = "release_date";

    String[] parsedMovieData = null;

    JSONObject jsonObject = new JSONObject(rawJsonString);

    JSONArray resultsArray = jsonObject.getJSONArray(MOVIE_RESULTS);

//    parsedMovieData = new String[parsedMovieData.length];

    for(int i = 0; i < resultsArray.length(); i++) {
      JSONObject jsonMovie = resultsArray.getJSONObject(i);

      Movie movie = new Movie();

      movie.setId(jsonMovie.getString(MOVIE_ID));
      movie.setTitle(jsonMovie.getString(MOVIE_TITLE));
      movie.setOrigTitle(jsonMovie.getString(MOVIE_ORIG_TITLE));
      movie.setPopularity(jsonMovie.getString(MOVIE_POPULARITY));
      movie.setPosterPath(jsonMovie.getString(MOVIE_POSTER_PATH));
      movie.setBackdropPath(jsonMovie.getString(MOVIE_BACKDROP_PATH));

      try{
        Long voteCount = Long.parseLong(jsonMovie.getString(MOVIE_VOTE_COUNT));
        movie.setVoteCount(voteCount);
      } catch(NumberFormatException e){
        e.printStackTrace();
      }

      try{
        Double voteAvg = Double.parseDouble(jsonMovie.getString(MOVIE_VOTE_AVG));
        movie.setVoteAvg(voteAvg);
      } catch(NumberFormatException e) {
        e.printStackTrace();
      }


      movie.setOrigLanguage(jsonMovie.getString(MOVIE_ORIG_LANGUAGE));

      String genres = jsonMovie.getString(MOVIE_GENRE_IDS);
      if(!"".equals(genres)){
        if(genres.contains(",")){
          String[] genreArray = genres.split(",");
          movie.setGenres(genreArray);
        } else {
          String[] singleGenreArray = new String[]{genres};
          movie.setGenres(singleGenreArray);
        }
      }

      movie.setAdult(Boolean.parseBoolean(jsonMovie.getString(MOVIE_IS_ADULT)));
      movie.setOverview(jsonMovie.getString(MOVIE_OVERVIEW));

      String releaseDate = jsonMovie.getString(MOVIE_RELEASE_DATE);
      movie.setReleaseDate(releaseDate);


      movieList.add(movie);

      /**
       * movie.setId(jsonMovie.getString(MOVIE_ID));
       movie.setTitle(jsonMovie.getString(MOVIE_TITLE));
       movie.setOrigTitle(jsonMovie.getString(MOVIE_ORIG_TITLE));
       movie.setPopularity(jsonMovie.getString(MOVIE_POPULARITY));
       String posterPath = jsonMovie.getString(MOVIE_POSTER_PATH);
       String backdropPath = jsonMovie.getString(MOVIE_BACKDROP_PATH);
       String voteCount = jsonMovie.getString(MOVIE_VOTE_COUNT);
       String voteAvg = jsonMovie.getString(MOVIE_VOTE_AVG);
       String origLanguage = jsonMovie.getString(MOVIE_ORIG_LANGUAGE);
       String genres = jsonMovie.getString(MOVIE_GENRE_IDS);
       String isAdult = jsonMovie.getString(MOVIE_IS_ADULT);
       String overview = jsonMovie.getString(MOVIE_OVERVIEW);
       String releaseDate = jsonMovie.getString(MOVIE_RELEASE_DATE);
       */
    }

    if(movieList.size() > 0){
      return movieList;
    } else {
      return null;
    }
  }

}
