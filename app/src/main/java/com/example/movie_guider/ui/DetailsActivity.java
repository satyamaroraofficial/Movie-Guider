package com.example.movie_guider.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.movie_guider.BuildConfig;
import com.example.movie_guider.R;
import com.example.movie_guider.adapters.MovieRecyclerViewAdapter;
import com.example.movie_guider.adapters.TrailerRecyclerViewAdapter;
import com.example.movie_guider.model.Movie;
import com.example.movie_guider.model.MovieRecyclerView;
import com.example.movie_guider.model.TMDBTrailerResponse;
import com.example.movie_guider.model.Trailer;
import com.example.movie_guider.network.RetrofitAPI;
import com.example.movie_guider.utils.NetworkUtils;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity implements TrailerRecyclerViewAdapter.ItemClickListener{
    private static final int TRAILER_DETAILS_TYPE = 0;
    Movie mMovie, tempMovie;
    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.trailer_label_tv)
    TextView mTrailersLabel0;
    @BindView(R.id.trailers_hint_tv)
    TextView mTrailersLabel1;
    @BindView(R.id.reviews_label_tv)
    TextView mReviewsLabel0;
    @BindView(R.id.rating_value_tv)
    TextView mRatingTextView;
    @BindView(R.id.date_value_tv)
    TextView mDateTextView;
    @BindView(R.id.title_tv)
    TextView mTitleTextView;
    @BindView(R.id.plot_tv)
    TextView mPlotTextView;
    @BindView(R.id.poster_image_view)
    ImageView mPosterImageView;
    @BindView(R.id.rv_trailers)
    MovieRecyclerView mTrailerRecyclerView;
    @BindView(R.id.rv_reviews)
    MovieRecyclerView mReviewRecyclerView;
    @BindView(R.id.fav_button)
    FloatingActionButton mFavoriteButton;
    @BindView(R.id.backdrop_iv)
    ImageView mBackdropImageView;
    @BindView(R.id.director_value_tv)
    TextView mDirectorTextView;
    @BindView(R.id.cast_rv)
    RecyclerView mCastRecyclerView;
    @BindView(R.id.tagline_tv)
    TextView mTaglineTextView;
    @BindView(R.id.votes_value_tv)
    TextView mVotesTextView;
    @BindView(R.id.minutes_value_tv)
    TextView mMinutesTextView;
    @BindView(R.id.imdb_value_tv)
    ImageButton mImdbButton;
    @BindView(R.id.genres_rv)
    RecyclerView mGenresRecyclerView;
    @BindView(R.id.similar_movies_rv)
    RecyclerView mSimilarMoviesRecyclerView;

    //TODO Add Adapters
    private TrailerRecyclerViewAdapter mTrailerAdapter;
    private MovieRecyclerViewAdapter mSimilarMoviesAdapter;

    //TODO Add ArrayLists
    private ArrayList<String> mTrailerTitles = new ArrayList<>();
    private ArrayList<String> mTrailerPaths = new ArrayList<>();
    private ArrayList<Movie> mSimilarMovies = new ArrayList<>();

    private Context mContext;
    private byte[] imageBytes;
    //TODO Add REALME DATA SOURCES

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
        mContext = getApplicationContext();
        if(Build.VERSION.SDK_INT >= 21) {
            Slide slide = new Slide(Gravity.BOTTOM);
            getWindow().setEnterTransition(slide);
            postponeEnterTransition();
        }

//        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mMovie = getIntent().getParcelableExtra("movie");
        collapsingToolbarLayout.setTitle(mMovie.getTitle());
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.black));
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.black));
        appBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffSet) -> {
            if(Math.abs(verticalOffSet) - appBarLayout.getTotalScrollRange() == 0)
                mPosterImageView.setVisibility(View.GONE);
            else
                mPosterImageView.setVisibility(View.VISIBLE);
        });

        //Setting release date
        if(mMovie.getDate() != null && !mMovie.getDate().equals(""))
            mDateTextView.setText(pretiffyDate(mMovie.getDate()));

        //Loading backdrop images
        mTitleTextView.setText(mMovie.getTitle());
        Glide.with(getApplicationContext())
                .load(RetrofitAPI.BACKDROP_BASE_URL + mMovie.getBackdropPath())
                .centerCrop()
                .placeholder(R.drawable.tmdb_placeholder_land)
                .error(R.drawable.tmdb_placeholder_land)
                .fallback(R.drawable.tmdb_placeholder_land)
                .transition(new DrawableTransitionOptions().crossFade())
                .into(mBackdropImageView);

        //Loading poster images
        if(mMovie.getPosterBytes() != null) {
            Glide.with(getApplicationContext())
                    .load(mMovie.getPosterBytes())
                    .centerCrop()
                    .error(R.drawable.tmdb_placeholder)
                    .fallback(R.drawable.tmdb_placeholder)
                    .transition(new DrawableTransitionOptions().crossFade())
                    .into(mPosterImageView);
        } else {
            Glide.with(getApplicationContext())
                    .load(RetrofitAPI.POSTER_BASE_URL + mMovie.getPosterPath())
                    .centerCrop()
                    .error(R.drawable.tmdb_placeholder)
                    .fallback(R.drawable.tmdb_placeholder)
                    .transition(new DrawableTransitionOptions().crossFade())
                    .into(mPosterImageView);
        }

//        if(NetworkUtils.hasNetwork(mContext)) {
//            (findViewById(R.id.tagline_tv)).setVisibility(View.GONE);
//            (findViewById(R.id.similar_label_tv)).setVisibility(View.GONE);
//            (findViewById(R.id.cast_label_tv)).setVisibility(View.GONE);
//            (findViewById(R.id.votes_label_tv)).setVisibility(View.GONE);
//            (findViewById(R.id.votes_value_tv)).setVisibility(View.GONE);
//            (findViewById(R.id.minutes_label_tv)).setVisibility(View.GONE);
//            (findViewById(R.id.minutes_value_tv)).setVisibility(View.GONE);
//            (findViewById(R.id.imdb_label_tv)).setVisibility(View.GONE);
//            (findViewById(R.id.imdb_value_tv)).setVisibility(View.GONE);
//            (findViewById(R.id.director_label_tv)).setVisibility(View.GONE);
//            (findViewById(R.id.director_value_tv)).setVisibility(View.GONE);
//            (findViewById(R.id.genres_label_tv)).setVisibility(View.GONE);
//            (findViewById(R.id.trailers_hint_tv)).setVisibility(View.GONE);
//            (findViewById(R.id.trailer_label_tv)).setVisibility(View.GONE);
//            (findViewById(R.id.reviews_label_tv)).setVisibility(View.GONE);
//        } else {
//            //TODO
//
//            mTrailerRecyclerView.setLayoutManager(new LinearLayoutManager(DetailsActivity.this, RecyclerView.HORIZONTAL, false));
//            mTrailerAdapter = new TrailerRecyclerViewAdapter(mContext, mTrailerTitles, mTrailerPaths, this);
//            mTrailerRecyclerView.setAdapter(new ScaleInAnimationAdapter(mTrailerAdapter));
//
//            fetchDetails(mMovie.getId(), TRAILER_DETAILS_TYPE);
//        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startPostponedEnterTransition();
        }
    }

    //TODO Fetch Similar movies

    private void fetchDetails(int movieId, int detailsType) {
        RetrofitAPI retrofitAPI = NetworkUtils.getCacheEnabledRetrofit(getApplicationContext()).create(RetrofitAPI.class);
        switch (detailsType) {
            case TRAILER_DETAILS_TYPE:
                mTrailerRecyclerView.setVisibility(View.GONE);
                mTrailersLabel0.setVisibility(View.GONE);
                mTrailersLabel1.setVisibility(View.GONE);
                Call<TMDBTrailerResponse> trailerResponseCall = retrofitAPI.getTrailers(movieId, BuildConfig.TMDB_API_TOKEN, "en-US");
                trailerResponseCall.enqueue(new Callback<TMDBTrailerResponse>() {
                    @Override
                    public void onResponse(Call<TMDBTrailerResponse> call, Response<TMDBTrailerResponse> response) {
                        TMDBTrailerResponse tmdbTrailerResponse = response.body();
                        if(tmdbTrailerResponse != null && tmdbTrailerResponse.getResults().size() != 0) {
                            mTrailerTitles.clear();
                            mTrailerPaths.clear();
                            for(Trailer trailer : tmdbTrailerResponse.getResults()) {
                                mTrailerTitles.add(trailer.getName());
                                mTrailerPaths.add(trailer.getKey());
                            }
                            mTrailerAdapter.notifyDataSetChanged();
                            mTrailerRecyclerView.setVisibility(View.VISIBLE);
                            mTrailersLabel0.setVisibility(View.VISIBLE);
                            mTrailersLabel1.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<TMDBTrailerResponse> call, Throwable t) {

                    }
                });
                break;
                //TODO ADD case for review details type
        }
    }

    @Override
    public void onItemClick(String stringUrlTrailerClicked) {
        //TODO open youtube on clicking item
    }

    //Fetching release date
    private String pretiffyDate(String jsonDate) {
        DateFormat sourceDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try{
            date = sourceDateFormat.parse(jsonDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat destDateFormat = new SimpleDateFormat("MM dd\nyyyy");
        String dateStr = destDateFormat.format(date);
        return dateStr;
    }


}








