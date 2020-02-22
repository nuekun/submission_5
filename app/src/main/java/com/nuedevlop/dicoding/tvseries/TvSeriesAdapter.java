package com.nuedevlop.dicoding.tvseries;


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
import com.nuedevlop.dicoding.R;
import com.nuedevlop.dicoding.TvSeriesActivity;
import com.nuedevlop.dicoding.utils.Resources;

import java.util.List;

public class TvSeriesAdapter extends RecyclerView.Adapter<TvSeriesAdapter.ViewHolder> {


    private Context context;
    private List<Result> results;


    TvSeriesAdapter(Context context, List<Result> results) {
        this.context = context;
        this.results = results;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_tvseries, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        String poster = results.get(position).getPosterPath();


        holder.txtOverview.setText(results.get(position).getOverview());
        holder.txtTittle.setText(results.get(position).getName());
        String URL = Resources.BASE_URL_IMAGE_W_500+poster;
        Glide.with(context).load(URL).into(holder.gbrPoster);
        holder.relativeLayout.setOnClickListener(v->{

            Result tv = new Result();
            tv.setName(results.get(position).getName());
            tv.setFirstAirDate(results.get(position).getFirstAirDate());
            tv.setOverview(results.get(position).getOverview());
            tv.setPosterPath(results.get(position).getPosterPath());
            tv.setPopularity(results.get(position).getPopularity());
            tv.setOriginalLanguage(results.get(position).getOriginalLanguage());
            tv.setId(results.get(position).getId());

            Intent intent = new Intent(context, TvSeriesActivity.class);
            intent.putExtra(TvSeriesActivity.ExtraTvSeries, tv);
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
            txtTittle = view.findViewById(R.id.txtListTvSeresTittle);
            txtOverview = view.findViewById(R.id.txtListTvSeresOverview);
            gbrPoster = view.findViewById(R.id.gbrListTvSeresPoster);
            relativeLayout = view.findViewById(R.id.listTVseries);
        }
    }

}
