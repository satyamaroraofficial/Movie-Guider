package com.example.movie_guider.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TrailerRecyclerViewAdapter extends RecyclerView.Adapter<TrailerRecyclerViewAdapter.TrailerViewHolder> {
    private ArrayList<String> mTrailerTitles, mTrailerPaths;
    private Context mContext;
    private ItemClickListener mCLickListener;

    public TrailerRecyclerViewAdapter(Context context, ArrayList<String> trailerTitles, ArrayList<String> trailerPaths, ItemClickListener itemClickListener) {
        this.mContext = context;
        this.mTrailerTitles = trailerTitles;
        this.mTrailerPaths = trailerPaths;
        this.mCLickListener = itemClickListener;
    }

    @NonNull
    @Override
    public TrailerRecyclerViewAdapter.TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerRecyclerViewAdapter.TrailerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public interface ItemClickListener {
        void onItemClick(String stringUrlTrailerClicked);
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TrailerViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
