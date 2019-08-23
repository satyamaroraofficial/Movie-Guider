package com.example.movie_guider.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.example.movie_guider.R;
import com.example.movie_guider.adapters.MovieRecyclerViewAdapter;
import com.example.movie_guider.model.MovieRecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class MainActivity extends AppCompatActivity implements MovieRecyclerViewAdapter.ItemClickListener {
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 13;
    @BindView(R.id.search_view)
    FloatingSearchView searchView;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;
    MovieRecyclerView mRecyclerView;
    private MovieRecyclerViewAdapter mAdapter;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));

        mContext = getApplicationContext();

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_popular:
                    mRecyclerView.smoothScrollToPosition(0);
                    //TODO
                    break;
                case R.id.action_rated:
                    mRecyclerView.smoothScrollToPosition(0);
                    //TODO
                    break;
                case R.id.action_upcoming:
                    mRecyclerView.smoothScrollToPosition(0);
                    //TODO
                    break;
                case R.id.action_now:
                    mRecyclerView.smoothScrollToPosition(0);
                    //TODO
                    break;
                case R.id.action_favorites:
                    mRecyclerView.smoothScrollToPosition(0);
                    //TODO
                    break;
                default:
                    //TODO
            }
            return true;
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) { // Scrolled up
                    bottomNavigationView.setVisibility(View.GONE);
                } else {
                    bottomNavigationView.setVisibility(View.VISIBLE);
                }
            }
        });

        searchView = findViewById(R.id.search_view);
        searchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

            }

            @Override
            public void onSearchAction(String currentQuery) {
                mRecyclerView.smoothScrollToPosition(0);
                //TODO
                searchView.clearQuery();
            }
        });
        searchView.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_about_me:
                    Intent intentToAboutMe = new Intent(MainActivity.this, AboutMeActivity.class);
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(this, android.R.anim.fade_in, android.R.anim.fade_out);
                    startActivity(intentToAboutMe, options.toBundle());
                    break;
                case R.id.action_voice_search:
                    startVoiceRecognition();
            }
        });
    }

    private void startVoiceRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Voice searching...");
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if(matches != null) {
                if(!matches.isEmpty()) {
                    String query = matches.get(0);
                    //TODO
                    Toast.makeText(this, "Searching for " + query + "...", Toast.LENGTH_LONG).show();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchBarFocused()) {
            searchView.clearQuery();
        } else {
            super.onBackPressed();
        }
    }
}
