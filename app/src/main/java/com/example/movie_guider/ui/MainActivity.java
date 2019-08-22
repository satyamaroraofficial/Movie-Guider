package com.example.movie_guider.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.movie_guider.R;
import com.example.movie_guider.adapters.MovieRecyclerViewAdapter;

public class MainActivity extends AppCompatActivity implements MovieRecyclerViewAdapter.ItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
