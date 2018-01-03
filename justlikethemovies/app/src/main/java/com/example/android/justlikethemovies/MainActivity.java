package com.example.android.justlikethemovies;

import android.content.Context;
import android.content.DialogInterface;

import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.justlikethemovies.utils.Movie;
import com.example.android.justlikethemovies.utils.MoviesJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
implements MoviesAdapter.MoviesAdapterOnClickHandler{

  private RecyclerView mRecyclerView;
  private RecyclerView.Adapter adapter;
  private GridLayoutManager mLayoutManager;
  private MoviesAdapter mMovieAdapter;
  private TextView mTextDisplay;
  private ScrollView mMovieData;
  private TextView mNetworkError;
  private ProgressBar mLoadingIndicator;
  private Boolean sortByPopularity = Boolean.TRUE;
  private List<Movie> movieList;
  private AlertDialog.Builder builder;
  private ImageView mImageError;
  private ImageView mTMDBLogo;
  private TextView mTMDBDisclaimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      NetworkUtils.setApiKey(getString(R.string.api_key));

      mTextDisplay = (TextView) findViewById(R.id.tv_loading);
      mMovieData = (ScrollView) findViewById(R.id.sv_movie_data);
      mImageError = (ImageView) findViewById(R.id.iv_error);
      mNetworkError = (TextView) findViewById(R.id.network_error);
      mNetworkError.setVisibility(View.GONE);
      mTMDBLogo = (ImageView) findViewById(R.id.tmdb_logo);
      mTMDBDisclaimer = (TextView) findViewById(R.id.tmdb_disclaimer);

      movieList = new ArrayList<>();



      mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

      mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
      mRecyclerView.setHasFixedSize(true);

      mLayoutManager = new GridLayoutManager(this, 3);
      mRecyclerView.setLayoutManager(mLayoutManager);

      mMovieAdapter = new MoviesAdapter(movieList, this);
      mMovieAdapter.setMovieData(movieList);

      String key = NetworkUtils.getApiKey();

      if(!"YOUR_API_KEY_HERE".equals(key) && key != null && !"".equals(key)){
        loadMovieData();
      } else {
        mTextDisplay.setVisibility(View.GONE);
        mImageError.setVisibility(View.VISIBLE);
        mNetworkError.setText(getString(R.string.api_error));
        mNetworkError.setVisibility(View.VISIBLE);
      }


      mRecyclerView.setAdapter(mMovieAdapter);

      mRecyclerView.setVisibility(View.VISIBLE);

      final String sortPop = getString(R.string.sort_by_pop);
      String sortRate = getString(R.string.sort_by_rating);
      CharSequence sortOptions[] = new CharSequence[] {sortPop, sortRate};

      builder = new AlertDialog.Builder(this);
      builder.setTitle(R.string.action_sort);
      builder.setItems(sortOptions, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int which) {
          if(which == 0){
              sortByPopularity = Boolean.TRUE;
              loadMovieData();
              Toast.makeText(MainActivity.this, R.string.sorted_by_popularity, Toast.LENGTH_SHORT).show();
            } else if(which == 1) {
            sortByPopularity = Boolean.FALSE;
            loadMovieData();
            Toast.makeText(MainActivity.this, R.string.sorted_by_rating, Toast.LENGTH_SHORT).show();
          }
        }
      });

    }

  @Override
  public void onClick(Movie movieDetail) {
    onClick(movieDetail.getTitle());

  }

  @Override
  public void onClick(String movieDetail){
    Context context = this;
    Toast.makeText(context, movieDetail, Toast.LENGTH_SHORT).show();
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();


    if(id == R.id.action_sort){
      builder.show();
      return true;
    }
    if (id == R.id.action_refresh){
      loadMovieData();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  private void loadMovieData(){
    FetchMovieData task = new FetchMovieData(mMovieAdapter);
    task.execute(sortByPopularity);
  }


    private void unpackMovies(){
      for(Movie m : movieList){
        String name = m.getTitle();
        String id = m.getId();
        String pop = m.getPopularity();
        String rating = m.getVoteAvg().toString();

        String stringResponse =
                name + ": \n"
                        + id + ", "
                        + pop + ", "
                        + rating + "\n"
                        + "\n\n";
        ;
//        mMovieDetails.append(stringResponse);
      }



    }



  public class FetchMovieData extends AsyncTask<Boolean, Void, String>{

    private MoviesAdapter mAdapter;

    public FetchMovieData(MoviesAdapter moviesAdapter){
      this.mAdapter = moviesAdapter;
    }

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(Boolean... params) {
      Boolean sortByPopularity = params[0];
      URL queryUrl = NetworkUtils.buildUrl(sortByPopularity);
      try{
        String response =  NetworkUtils.getResponseFromHttpUrl(queryUrl);
        return response;
      } catch (IOException e){
        e.printStackTrace();
        //TODO: show network error
        return null;
      }

    }

    @Override
    protected void onPostExecute(String movieData) {
      if(movieData != null){
        try{
          movieList = MoviesJsonUtils.getMovieListFromJson(movieData);

          if(!movieList.isEmpty()){
            mAdapter.setMovieData(movieList);
//            unpackMovies();
          }


        } catch(JSONException e){
          e.printStackTrace();
        }
        mLoadingIndicator.setVisibility(View.GONE);
        mTextDisplay.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);

      } else {
        mTextDisplay.setVisibility(View.GONE);
        mImageError.setVisibility(View.VISIBLE);
        mNetworkError.setVisibility(View.VISIBLE);
        mLoadingIndicator.setVisibility(View.GONE);
      }
    }
  }
}
