package com.example.android.justlikethemovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.justlikethemovies.utils.Movie;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

  private ImageView mImageView;
  private TextView mMovieHeader;
  private TextView mMovieDetails;
  private Movie mMovie;
  private TextView mMovieYear;
  private TextView mMovieRating;
  private Button mVideoButton;
  private final String YOUTUBE_QUERY = "https://www.youtube.com/results?search_query=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
      mImageView = (ImageView) findViewById(R.id.iv_movie_image);

      mImageView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          Context context = view.getContext();
          Class posterActivity = PosterActivity.class;
          Intent posterIntent = new Intent(context, posterActivity);
          posterIntent.putExtra("movie", mMovie);
          context.startActivity(posterIntent);
        }
      });

      mMovieHeader = (TextView) findViewById(R.id.tv_movie_header);
      mMovieYear = (TextView) findViewById(R.id.tv_year);
      mMovieRating = (TextView) findViewById(R.id.tv_rating);

      mVideoButton = (Button) findViewById(R.id.video_button);
      mVideoButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          String title = mMovie.getTitle();
          String year = mMovie.getReleaseDate().substring(0,4);

          String url = YOUTUBE_QUERY + title + "+" + year + "+trailer";
          openWebPage(url);
        }
      });


      mMovieDetails = (TextView) findViewById(R.id.tv_loading);

      Intent intent = getIntent();
      mMovie = (Movie) intent.getSerializableExtra("movie");
      unpackMovie();
    }

    public void unpackMovie() {


      Context context = this;
      String title = mMovie.getTitle();

      getSupportActionBar().setTitle(title);

      String detail = mMovie.getOverview();
      String year =  mMovie.getReleaseDate()
              .substring(0, mMovie.getReleaseDate().indexOf("-"));
      String rating = mMovie.getVoteAvg().toString() + " / 10";

      String posterPath = mMovie.getPosterPath();
      Uri imageUri = NetworkUtils.builderMoviePosterUrl(posterPath, false);

      mMovieHeader.setText(title);
      mMovieYear.append(year);
      mMovieRating.append(rating);
      mMovieDetails.setText(detail);

      Picasso.with(context).load(imageUri).into(mImageView);
    }


    public void openWebPage(String url){
      Uri webpage = Uri.parse(url);

      Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

      if(intent.resolveActivity(getPackageManager()) != null){
        startActivity(intent);
      }
    }


}
