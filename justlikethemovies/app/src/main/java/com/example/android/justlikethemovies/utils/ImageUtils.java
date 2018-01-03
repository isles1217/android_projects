package com.example.android.justlikethemovies.utils;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.net.URI;
import java.net.URL;

/**
 * Created by izilladotcom on 12/12/17.
 */

public class ImageUtils {

  public static ImageView fetchMoviePoster(Context context, URL posterUrl){

    ImageView imageView = null;
    Uri posterUri = Uri.parse(posterUrl.toString());
    Picasso.with(context).load(posterUri).into(imageView);
    return imageView;
  }
}
