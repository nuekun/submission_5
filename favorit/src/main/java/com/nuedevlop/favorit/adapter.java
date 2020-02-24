package com.nuedevlop.favorit;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class adapter extends RecyclerView.Adapter<adapter.MovieViewHolder> {
    private Cursor cursor;
    private Context context;

    public adapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_main, viewGroup, false);
        return new MovieViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        movieViewHolder.bind(cursor.moveToPosition(i));
    }

    @Override
    public int getItemCount() {
        return cursor == null ? 0 : cursor.getCount();
    }

    public void setData(Cursor cursor) {
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        TextView txttittle;
        TextView txtOverview;
        ImageView gbrPoster;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            txttittle = itemView.findViewById(R.id.txtTittle);
            txtOverview = itemView.findViewById(R.id.txtOverview);
            gbrPoster = itemView.findViewById(R.id.gbrPriview);

        }

        public void bind(boolean moveToPosition) {
            if (moveToPosition) {
                txttittle.setText(cursor.getString(cursor.getColumnIndexOrThrow(Utils.COLUMN_TITLE)));
                txtOverview.setText(cursor.getString(cursor.getColumnIndexOrThrow(Utils.COLUMN_DESCRIPTION)));
                Glide.with(context).load(Utils.POSTER_BASE_URL + cursor.getString(cursor.getColumnIndexOrThrow(Utils.COLUMN_POSTER))).into(gbrPoster);
            }
        }
    }
}
