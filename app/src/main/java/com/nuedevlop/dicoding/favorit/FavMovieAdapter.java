package com.nuedevlop.dicoding.favorit;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nuedevlop.dicoding.MovieActivity;
import com.nuedevlop.dicoding.R;
import com.nuedevlop.dicoding.movie.Result;
import com.nuedevlop.dicoding.utils.Resources;

import java.util.List;

public class FavMovieAdapter extends RecyclerView.Adapter<FavMovieAdapter.ViewHolder> {


    private Context context;
    private List<Favorit> favorits;


    FavMovieAdapter(Context context, List<Favorit> favorits) {
        this.context = context;
        this.favorits = favorits;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_fav, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String poster = favorits.get(position).getPoster();
        String URL = Resources.BASE_URL_IMAGE_W_500+poster;
        Glide.with(context).load(URL).into(holder.gbrPoster);

        holder.gbrPoster.setOnClickListener(v-> {
                Result movie = new Result();
                movie.setTitle(favorits.get(position).getTittle());
                movie.setOverview(favorits.get(position).getOverview());
                movie.setPosterPath(poster);
                movie.setPopularity(Double.valueOf(favorits.get(position).getThird()));
                movie.setReleaseDate(favorits.get(position).getFirst());
                if (favorits.get(position).getSecond().equals("true")){
                    movie.setAdult(true);
                }else {movie.setAdult(false);}
                movie.setId(favorits.get(position).getIdFav()-1010);
                Intent intent = new Intent(context, MovieActivity.class);
                intent.putExtra(MovieActivity.ExtraMovie, movie);
                context.startActivity(intent);
        });



    }

    @Override
    public int getItemCount() {
        return favorits.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView gbrPoster;

        ViewHolder(@NonNull View view) {

            super(view);
            gbrPoster = view.findViewById(R.id.gbrFavlist);
        }
    }

}
