package com.android.flixster.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.flixster.activities.Detailed_Activity;
import com.android.flixster.R;
import com.android.flixster.models.Movie;
import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    List<Movie> movieList;
    Context context;
    boolean portrait;

    public RecyclerAdapter(Context context, List<Movie> movies, boolean portrait) {
        this.context = context;
        this.movieList = movies;
        this.portrait = portrait;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(context).inflate(R.layout.recycler_movie_layout, parent, false);
        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie item = movieList.get(position);
        holder.bind(item);
    }


    @Override
    public int getItemCount() {
        return movieList.size();
    }


    //view holder class
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title_view;
        TextView overview_view;
        ConstraintLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.movie_image);
            title_view = itemView.findViewById(R.id.title_text);
            overview_view = itemView.findViewById(R.id.overview_text);
            container = itemView.findViewById(R.id.container);
        }

        public void bind(Movie movieItem) {
            if (portrait) {
                Glide.with(context).load(movieItem.getPoster_path()).into(image);
            } else {
                Glide.with(context).load(movieItem.getBackdrop_path()).into(image);
            }
            title_view.setText(movieItem.getTitle());
            overview_view.setText(movieItem.getOverview());
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, Detailed_Activity.class);
                    i.putExtra("movieObject", Parcels.wrap(movieItem));
                    context.startActivity(i);
                }
            });
        }

    }
}