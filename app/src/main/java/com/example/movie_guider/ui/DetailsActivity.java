package com.example.movie_guider.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movie_guider.R;
import com.example.movie_guider.model.MovieRecyclerView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

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
    //TODO Add ArrayLists
    //TODO Add REALME DATA SOURCES

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
    }
}
