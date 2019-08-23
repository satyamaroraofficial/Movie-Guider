package com.example.movie_guider.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.movie_guider.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutMeActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        ButterKnife.bind(this);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        (findViewById(R.id.play_iv)).setOnClickListener(v -> {
            Uri uri = Uri.parse("https://play.google.com/store/apps/dev?id=7368032842071222295");
            Intent intentToPlayStore = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intentToPlayStore);
        });
        (findViewById(R.id.github_iv)).setOnClickListener(v -> {
            Uri uri = Uri.parse("https://github.com/sat0004");
            Intent intentToGithub = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intentToGithub);
        });
        Glide.with(this)
                .load("https://i2.wp.com/www.andreasreiterer.at/wp-content/uploads/2017/11/react-logo.jpg?resize=825%2C510&ssl=1")
                .into((ImageView)findViewById(R.id.satyam_iv));
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
