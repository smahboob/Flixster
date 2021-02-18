package com.android.flixster.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.flixster.R;
import com.android.flixster.models.Movie;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    List<Movie> movieList;

    public RecyclerAdapter(List<Movie> movies) {
        this.movieList = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View todoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_movie_layout, parent, false);
        return new ViewHolder(todoView);
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


    //viewholder class
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title_view;
        TextView overview_view;

        private List<Movie> mMovies;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.movie_image);
            title_view = itemView.findViewById(R.id.title_text);
            overview_view = itemView.findViewById(R.id.overview_text);
        }

        public void bind(Movie movieItem) {
            title_view.setText(movieItem.getTitle());
            overview_view.setText(movieItem.getOverview());
        }
    }
}