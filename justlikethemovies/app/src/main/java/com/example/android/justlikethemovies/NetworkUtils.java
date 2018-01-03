package com.example.android.justlikethemovies;

import android.app.Application;
import android.content.res.Resources;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PortUnreachableException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by itorres on 11/3/17.
 */

public class NetworkUtils {

  private static final String MOVIES_BASE_URL = "http://api.themoviedb.org/3/movie";
  private static final String SORT_BY_POPULAR = "popular";
  private static final String SORT_BY_TOP_RATED = "top_rated";
  //TODO: hide API_KEY value before submitting!
  private static String API_KEY;


  private static final String MOVIE_DB_IMAGE_HOST = "image.tmdb.org/t/p";
  private static final String MOVIE_DB_IMAGE_SIZE_W185 = "w185";
  private static final String MOVIE_DB_IMAGE_SIZE_ORIGINAL = "original";

  public static void setApiKey(String apiKey){
    API_KEY = apiKey;
  }

  public static String getApiKey(){
    return API_KEY;
  }

  public static URL buildUrl(boolean sortByPopular){
    String querySort = SORT_BY_POPULAR;

    if (!sortByPopular) {
      querySort = SORT_BY_TOP_RATED;
    }

      Uri queryUri = Uri.parse(MOVIES_BASE_URL)
              .buildUpon()
              .appendPath(querySort)
              .appendQueryParameter("api_key", API_KEY)
              .build();
      try{
        URL queryURL = new URL(queryUri.toString());
        return queryURL;
        } catch(MalformedURLException e){
        e.printStackTrace();
        return null;
      }
  }

  public static URL buildUrl(){
    return buildUrl(true);
  }

  public static Uri builderMoviePosterUrl(String imagePath, boolean isOriginalSize){
    String imageSize = MOVIE_DB_IMAGE_SIZE_ORIGINAL;

    if(!isOriginalSize){
      imageSize = MOVIE_DB_IMAGE_SIZE_W185;
    }

    Uri uri = new Uri.Builder()
            .scheme("http")
            .encodedAuthority(MOVIE_DB_IMAGE_HOST)
            .appendEncodedPath(imageSize + imagePath)
//            .appendEncodedPath(imagePath)
            .build();

    return uri;
    /**try{
      URL url = new URL(uri.toString());
      return url;
    } catch(MalformedURLException e ){
      e.printStackTrace();
      return null;
    }*/
  }

  public static String getResponseFromHttpUrl(URL url) throws IOException {
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

    try{
      InputStream in = connection.getInputStream();

      Scanner scanner = new Scanner(in);
      scanner.useDelimiter("\\A");

      boolean hasInput = scanner.hasNext();
      if(hasInput){
        return scanner.next();
      } else {
        return null;
      }
    } finally {
      connection.disconnect();
    }
  }



}
