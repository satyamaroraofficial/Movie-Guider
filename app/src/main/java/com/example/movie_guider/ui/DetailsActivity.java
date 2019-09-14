package com.example.movie_guider.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.movie_guider.R;
import com.example.movie_guider.adapters.MovieRecyclerViewAdapter;
import com.example.movie_guider.adapters.TrailerRecyclerViewAdapter;
import com.example.movie_guider.model.Movie;
import com.example.movie_guider.model.MovieRecyclerView;
import com.example.movie_guider.network.RetrofitAPI;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity implements TrailerRecyclerViewAdapter.ItemClickListener{

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
        mContext = getApplicationContext();

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
    }

    @Override
    public void onItemClick(String stringUrlTrailerClicked) {
        //TODO open youtube on clicking item
    }


}








