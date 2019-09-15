package com.example.movie_guider.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.movie_guider.BuildConfig;
import com.example.movie_guider.R;
import com.example.movie_guider.adapters.CastRecyclerViewAdapter;
import com.example.movie_guider.adapters.GenresRecyclerViewAdapter;
import com.example.movie_guider.adapters.MovieRecyclerViewAdapter;
import com.example.movie_guider.adapters.TrailerRecyclerViewAdapter;
import com.example.movie_guider.model.Cast;
import com.example.movie_guider.model.Crew;
import com.example.movie_guider.model.GenresItem;
import com.example.movie_guider.model.Movie;
import com.example.movie_guider.model.MovieRecyclerView;
import com.example.movie_guider.model.TMDBCreditsResponse;
import com.example.movie_guider.model.TMDBDetailsResponse;
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
    private GenresRecyclerViewAdapter mGenreAdapter;

    //TODO Add ArrayLists
    private ArrayList<String> mTrailerTitles = new ArrayList<>();
    private ArrayList<String> mTrailerPaths = new ArrayList<>();
    private ArrayList<GenresItem> mGenres = new ArrayList<>();
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


        mRatingTextView.setText(mMovie.getRating()); //Setting rating
        mTitleTextView.setText(mMovie.getTitle()); //Setting title
        mPlotTextView.setText(mMovie.getPlot()); //Setting plot
        //TODO Add favourite button method

        //Setting release date
        if(mMovie.getDate() != null && !mMovie.getDate().equals(""))
            mDateTextView.setText(prettifyDate(mMovie.getDate()));

        //Loading backdrop images
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

        if(!NetworkUtils.hasNetwork(mContext)) {
            (findViewById(R.id.tagline_tv)).setVisibility(View.GONE);
            (findViewById(R.id.similar_label_tv)).setVisibility(View.GONE);
            (findViewById(R.id.cast_label_tv)).setVisibility(View.GONE);
            (findViewById(R.id.votes_label_tv)).setVisibility(View.GONE);
            (findViewById(R.id.votes_value_tv)).setVisibility(View.GONE);
            (findViewById(R.id.minutes_label_tv)).setVisibility(View.GONE);
            (findViewById(R.id.minutes_value_tv)).setVisibility(View.GONE);
            (findViewById(R.id.imdb_label_tv)).setVisibility(View.GONE);
            (findViewById(R.id.imdb_value_tv)).setVisibility(View.GONE);
            (findViewById(R.id.director_label_tv)).setVisibility(View.GONE);
            (findViewById(R.id.director_value_tv)).setVisibility(View.GONE);
            (findViewById(R.id.genres_label_tv)).setVisibility(View.GONE);
            (findViewById(R.id.trailers_hint_tv)).setVisibility(View.GONE);
            (findViewById(R.id.trailer_label_tv)).setVisibility(View.GONE);
            (findViewById(R.id.reviews_label_tv)).setVisibility(View.GONE);
        } else {
            //TODO Add review, genres and similar movies
            fetchCredits();
            fetchMoreDetails();

            mTrailerRecyclerView.setLayoutManager(new LinearLayoutManager(DetailsActivity.this, RecyclerView.HORIZONTAL, false));
            mTrailerAdapter = new TrailerRecyclerViewAdapter(mContext, mTrailerTitles, mTrailerPaths, this);
            mTrailerRecyclerView.setAdapter(new ScaleInAnimationAdapter(mTrailerAdapter));

            mGenresRecyclerView.setLayoutManager(new LinearLayoutManager(DetailsActivity.this, RecyclerView.HORIZONTAL, false));
            mGenreAdapter = new GenresRecyclerViewAdapter(mContext, mGenres);
            mGenresRecyclerView.setAdapter(new ScaleInAnimationAdapter(mGenreAdapter));

            fetchDetails(mMovie.getId(), TRAILER_DETAILS_TYPE);
        }

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

    private void fetchCredits() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        mCastRecyclerView.setLayoutManager(layoutManager);

        final ArrayList<Cast> castArrayList = new ArrayList<>();
        final CastRecyclerViewAdapter mCastAdapter = new CastRecyclerViewAdapter(this, castArrayList, actorName -> {
            try{
                Uri uri = Uri.parse("https:www.google.com/search?q=" + actorName + " movies");
                Intent actorMoviesIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(actorMoviesIntent);
            }catch (Exception e) {
                e.printStackTrace();
            }
        });
        mCastRecyclerView.setAdapter(new ScaleInAnimationAdapter(mCastAdapter));

        RetrofitAPI retrofitAPI = NetworkUtils.getCacheEnabledRetrofit(getApplicationContext()).create(RetrofitAPI.class);
        final Call<TMDBCreditsResponse> creditsResponseCall = retrofitAPI.getCredits(mMovie.getId(), BuildConfig.TMDB_API_TOKEN);
        creditsResponseCall.enqueue(new Callback<TMDBCreditsResponse>() {
            @Override
            public void onResponse(Call<TMDBCreditsResponse> call, Response<TMDBCreditsResponse> response) {
                TMDBCreditsResponse creditsResponse = response.body();

                //Get cast info
                castArrayList.clear();
                if(creditsResponse != null && creditsResponse.getCast().size() != 0) {
                    castArrayList.addAll(creditsResponse.getCast());
                    mCastAdapter.notifyDataSetChanged();
                } else {
                    (findViewById(R.id.cast_label_tv)).setVisibility(View.GONE);
                    mCastRecyclerView.setVisibility(View.GONE);
                }

                //GEt director info
                if(creditsResponse != null) {
                    for(Crew crew : creditsResponse.getCrew()) {
                        if(crew.getJob().equals("Director")) {
                            mDirectorTextView.setText(crew.getName());
                            break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<TMDBCreditsResponse> call, Throwable t) {
                //Nothing
            }
        });
    }


    private void fetchMoreDetails() {
        RetrofitAPI retrofitAPI = NetworkUtils.getCacheEnabledRetrofit(getApplicationContext()).create(RetrofitAPI.class);
        Call<TMDBDetailsResponse> detailsResponseCall = retrofitAPI.getDetails(mMovie.getId(), BuildConfig.TMDB_API_TOKEN, "en-US");
        detailsResponseCall.enqueue(new Callback<TMDBDetailsResponse>() {
            @Override
            public void onResponse(Call<TMDBDetailsResponse> call, Response<TMDBDetailsResponse> response) {
                final TMDBDetailsResponse tmdbDetailsResponse = response.body();
                String tagline = null;
                if(tmdbDetailsResponse != null) {
                    tagline = tmdbDetailsResponse.getTagline();
                }
                if(tagline != null && !tagline.equals("")) {
                    mTaglineTextView.setText(tagline);
                } else {
                    mTaglineTextView.setVisibility(View.GONE);
                }
                //TODO
                mVotesTextView.setText(String.valueOf(tmdbDetailsResponse.getVoteCount()));
                mMinutesTextView.setText(String.valueOf(tmdbDetailsResponse.getRuntime()));
                mImdbButton.setOnClickListener(view -> {
                    String imdbId = tmdbDetailsResponse.getImdbId();
                    try{
                        Uri uri;
                        if(imdbId != null && !imdbId.equals(""))
                            uri = Uri.parse("http://www.imdb.com/title/" + imdbId + "/");
                        else {
                            Toast.makeText(mContext, "Movie is'nt there on IMDB. Here is a Google search for it instead!", Toast.LENGTH_LONG).show();
                            uri = Uri.parse("https://www.google.com/search?q=" + tmdbDetailsResponse.getTitle());
                        }
                        Intent imdbIntent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(imdbIntent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                //Fetching GENRES details
                if(tmdbDetailsResponse.getGenres() != null && tmdbDetailsResponse.getGenres().size() != 0) {
                    mGenres.clear();
                    mGenres.addAll(tmdbDetailsResponse.getGenres());
                    mGenreAdapter.notifyDataSetChanged();
                } else {
                    (findViewById(R.id.genres_label_tv)).setVisibility(View.GONE);
                    mGenresRecyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<TMDBDetailsResponse> call, Throwable t) {
                //Nothing
            }
        });
    }

    @Override
    public void onItemClick(String stringUrlTrailerClicked) {
        Uri youtubeUri  = Uri.parse("https://www.youtube.com//watch?v=" + stringUrlTrailerClicked);
        Intent openYouTube = new Intent(Intent.ACTION_VIEW, youtubeUri);
        startActivity(openYouTube);
    }

    //Fetching release date
    private String prettifyDate(String jsonDate) {
        DateFormat sourceDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try{
            date = sourceDateFormat.parse(jsonDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat destDateFormat = new SimpleDateFormat("MMM dd\nyyyy");
        String dateStr = destDateFormat.format(date);
        return dateStr;
    }


}








