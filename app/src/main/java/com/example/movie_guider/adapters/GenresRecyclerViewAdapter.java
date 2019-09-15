package com.example.movie_guider.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie_guider.R;
import com.example.movie_guider.model.GenresItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GenresRecyclerViewAdapter extends RecyclerView.Adapter<GenresRecyclerViewAdapter.GenreViewHolder> {
    private Context mContext;
    private ArrayList<GenresItem> mGenres;

    public GenresRecyclerViewAdapter(Context context, ArrayList<GenresItem> genres) {
        this.mContext = context;
        this.mGenres = genres;
    }

    @NonNull
    @Override
    public GenresRecyclerViewAdapter.GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_genre_item, parent, false);
        return new GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenresRecyclerViewAdapter.GenreViewHolder holder, int position) {
        Typeface hammersmithOne = ResourcesCompat.getFont(mContext, R.font.hammersmith_one);
        holder.mGenreTextView.setText(mGenres.get(position).getName());
        holder.mGenreTextView.setTypeface(hammersmithOne);
    }

    @Override
    public int getItemCount() {
        if(mGenres != null) {
            return mGenres.size();
        } else{
            return 0;
        }
    }

    public class GenreViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.genre_tv)
        TextView mGenreTextView;

        public GenreViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
