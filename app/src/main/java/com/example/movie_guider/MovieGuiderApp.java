package com.example.movie_guider;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MovieGuiderApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this );
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("movieguider.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfig);
    }
}
