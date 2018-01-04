package com.example.android.justlikethemovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.justlikethemovies.utils.Movie;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by izilladotcom on 11/12/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesAdapterViewHolder>{

  private List<Movie> mMovieList = new ArrayList<>();
  private Context mContext;

  private final MoviesAdapterOnClickHandler mClickHandler;


  public interface MoviesAdapterOnClickHandler {
    void onClick(String movieDetail);

    void onClick(Movie movieDetail);
  }

  // MoviesAdapterOnClickHandler clickHandler was in constructor

  public MoviesAdapter(List<Movie> movieList, Context context){
    this.mMovieList = movieList;
//    setMovieData(movieList);
    this.mContext = context;

    mClickHandler = null;
  }

  public class MoviesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private TextView mTextView;
    private ImageView mImageView;
    private TextView mId;

    public MoviesAdapterViewHolder(View view){
      super(view);
//      mTextView = (TextView) view.findViewById(R.id.itemTitle);
      mImageView = (ImageView) view.findViewById(R.id.itemImage);
//      mId = (TextView) view.findViewById(R.id.itemId);
      view.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      int adapterPosition = getAdapterPosition();
      Movie movieDetail = mMovieList.get(adapterPosition);
      Class detailActivity = DetailActivity.class;
      Intent intent = new Intent(mContext, detailActivity);

      intent.putExtra("movie", movieDetail);
      mContext.startActivity(intent);
//      mClickHandler.onClick(movieDetail);

    }
  }

  @Override
  public MoviesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int position) {
    Context context = parent.getContext();

    int layoutIdForListItem = R.layout.recyclerview_item;
    LayoutInflater inflater = LayoutInflater.from(context);
    boolean shouldAttachToParentImmediately = false;

    View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
    return new MoviesAdapterViewHolder(view);
  }

  @Override
  public void onBindViewHolder(MoviesAdapterViewHolder holder, int position) {
    Context context = holder.mImageView.getContext();
//    holder.mImageView = (ImageView) holder.mImageView.set(R.drawable.ic_action_name);
    Movie movie = mMovieList.get(position);
//    String movieDetail = mMovieData[position];
//    holder.mTextView.setText(movie.getTitle());



    String posterPath = movie.getPosterPath();
//    URL imageURL = NetworkUtils.builderMoviePosterUrl(posterPath, false);
    Uri imageUri = NetworkUtils.builderMoviePosterUrl(posterPath, false);

    Picasso.with(context).load(imageUri)
            .placeholder(R.drawable.ic_action_name)
            .error(R.drawable.ic_action_name)
            .into(holder.mImageView);
    holder.mImageView.setVisibility(View.VISIBLE);
  }

  @Override
  public int getItemCount() {
    if(!mMovieList.isEmpty()){
      return mMovieList.size();
    } else {
      return 0;
    }
  }

  public void setMovieData(List<Movie> movieList){
    this.mMovieList = movieList;
    notifyDataSetChanged();
  }

}
