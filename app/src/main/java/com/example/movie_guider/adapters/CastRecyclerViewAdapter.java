package com.example.movie_guider.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movie_guider.R;
import com.example.movie_guider.model.Cast;
import com.example.movie_guider.network.RetrofitAPI;

import java.util.ArrayList;

public class CastRecyclerViewAdapter extends RecyclerView.Adapter<CastRecyclerViewAdapter.CastViewHolder> {
    private ArrayList<Cast> mCastList;
    private Context mContext;
    private OnActorClickHandler mOnActorClickHandler;

    public CastRecyclerViewAdapter(Context context, ArrayList<Cast> cast, OnActorClickHandler onActorClickHandler) {
        this.mContext = context;
        this.mCastList = cast;
        this.mOnActorClickHandler = onActorClickHandler;
    }

    @NonNull
    @Override
    public CastRecyclerViewAdapter.CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_cast, parent, false);
        return new CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {
//        holder.mCastTextView.setText(mCastList.get(position).getName());
        Glide.with(mContext)
                .load(RetrofitAPI.POSTER_BASE_URL + mCastList.get(position).getProfilePath())
                .into(holder.mCastImageView);
    }

    @Override
    public int getItemCount() {
        return mCastList.size();
    }

    public interface OnActorClickHandler {
        void onActorClickHandler(String actorName);
    }

    public class CastViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mCastTextView;
        public ImageView mCastImageView;

        public CastViewHolder(@NonNull View itemView) {
            super(itemView);
            mCastTextView = itemView.findViewById(R.id.cast_rv);
            mCastImageView = itemView.findViewById(R.id.cast_iv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnActorClickHandler.onActorClickHandler(mCastTextView.getText().toString());
        }
    }
}
