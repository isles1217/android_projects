package com.example.android.justlikethemovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.android.justlikethemovies.utils.Movie;
import com.squareup.picasso.Picasso;

/**
 * Created by izilladotcom on 1/2/18.
 */

public class PosterActivity extends AppCompatActivity{

  private ImageView mImageView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_poster);

    mImageView = (ImageView) findViewById(R.id.image_poster_full);

    Intent intent = getIntent();
    Movie movie = (Movie) intent.getSerializableExtra("movie");

    Context context = this;
    String posterPath = movie.getPosterPath();
    Uri imageUri = NetworkUtils.builderMoviePosterUrl(posterPath, true);
    Picasso.with(context).load(imageUri)
            .placeholder(R.drawable.ic_action_name)
            .error(R.drawable.ic_action_name)
            .into(mImageView);

  }
}
