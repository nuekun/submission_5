package com.nuedevlop.dicoding.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.nuedevlop.dicoding.R;
import com.nuedevlop.dicoding.favorit.FavDAO;
import com.nuedevlop.dicoding.favorit.FavDB;
import com.nuedevlop.dicoding.favorit.Favorit;
import com.nuedevlop.dicoding.utils.Resources;

import java.util.ArrayList;
@GlideModule
public class MovieFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    private ArrayList<Favorit> favorits = new ArrayList<>();
    private FavDB favDB;

    MovieFactory(Context context){
        this.context = context;
    }

    @Override
    public void onCreate() {
        final long identityToken = Binder.clearCallingIdentity();
        favDB = Room.databaseBuilder(context.getApplicationContext(), FavDB.class, "db_fav")
                .allowMainThreadQueries()
                .build();
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDataSetChanged() {
        try {
            FavDAO favDAO = favDB.getFavDAO();
            favorits.clear();
            favorits.addAll(favDAO.getAllFav());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        favDB.close();
    }

    @Override
    public int getCount() {
        return favorits.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.item_widget);
        try{
            Bitmap bitmap = Glide.with(context)
                    .asBitmap()
                    .load(Resources.BASE_URL_IMAGE_W_500+"/"+favorits.get(position).getPoster())
                    .apply(new RequestOptions().fitCenter())
                    .submit(500,750)
                    .get();
            remoteViews.setImageViewBitmap(R.id.gbrItemMovie,bitmap);

        }catch (Exception e){
            e.printStackTrace();
        }

        Bundle bundle = new Bundle();
        bundle.putInt(MovieWidget.EXTRA_ITEM,position);
        Intent intent = new Intent();
        intent.putExtras(bundle);

        remoteViews.setOnClickFillInIntent(R.id.gbrItemMovie,intent);
        return remoteViews;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
