package com.example.movie_guider.adapters;

import android.content.Context;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie_guider.R;
//import com.example.movie_guider.model.Movie;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.MovieViewHolder> {
    private ArrayList<Movie> mMovieArrayList;
    private Context mContext;
    private ItemClickListener mClickListener;

    public MovieRecyclerViewAdapter(Context context, ArrayList<Movie> movieArrayList, ItemClickListener itemClickListener) {
        this.mContext = context;
        this.mMovieArrayList = movieArrayList;
        this.mClickListener = itemClickListener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie theMovie = mMovieArrayList.get(position);
        //TODO
        //        if(theMovie.getPosterByte() != null) {
//        }
    }

    @Override
    public int getItemCount() {
        if(mMovieArrayList == null) {
            return 0;
        }else {
            return mMovieArrayList.size();
        }
    }

    public interface ItemClickListener {
        void onItemClick(int position, ImageView posterImageView);
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.poster_image_view)
        ImageView mPosterImageView;

        MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mClickListener != null) {
                mClickListener.onItemClick(getAdapterPosition(), mPosterImageView);
            }
        }
    }
}
