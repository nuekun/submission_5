package com.nuedevlop.dicoding.movie;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nuedevlop.dicoding.MovieActivity;
import com.nuedevlop.dicoding.R;
import com.nuedevlop.dicoding.utils.Resources;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {


    private Context context;
    private List<Result> results;


    MovieAdapter(Context context, List<Result> results) {
        this.context = context;
        this.results = results;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_movie, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String tittle,date,overview,poster;
        int id;
        Double popular;
        tittle = results.get(position).getTitle();
        overview = results.get(position).getOverview();
        poster = results.get(position).getPosterPath();
        popular = results.get(position).getPopularity();
        date = results.get(position).getReleaseDate();
        id = results.get(position).getId();
        holder.txtOverview.setText(overview);
        holder.txtTittle.setText(tittle);
        String URL = Resources.BASE_URL_IMAGE_W_500+poster;
        Glide.with(context).load(URL).into(holder.gbrPoster);
        holder.relativeLayout.setOnClickListener(v->{
                Result movie = new Result();
                movie.setTitle(tittle);
                movie.setOverview(overview);
                movie.setPosterPath(poster);
                movie.setPopularity(popular);
                movie.setReleaseDate(date);
                movie.setId(id);
                movie.setAdult(results.get(position).getAdult());

            Intent intent = new Intent(context, MovieActivity.class);
            intent.putExtra(MovieActivity.ExtraMovie, movie);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return results.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTittle,txtOverview;
        private ImageView gbrPoster;
        private RelativeLayout relativeLayout;

        ViewHolder(@NonNull View view) {

            super(view);
            txtTittle = view.findViewById(R.id.txtListMovieTittle);
            txtOverview = view.findViewById(R.id.txtListMovieOverview);
            gbrPoster = view.findViewById(R.id.gbrListMoviePoster);
            relativeLayout = view.findViewById(R.id.listMovie);
        }
    }

}
