package com.nuedevlop.dicoding.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.android.material.tabs.TabLayout;
import com.nuedevlop.dicoding.R;
import com.nuedevlop.dicoding.favorit.FavFragment;
import com.nuedevlop.dicoding.movie.MovieFragment;
import com.nuedevlop.dicoding.tvseries.TvSeriesFragment;

public class PagerAdapter extends FragmentPagerAdapter {
    Context context;
    public PagerAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context = context;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new MovieFragment();
        }else if (position == 1) {
            return new TvSeriesFragment();
        } else return new FavFragment();

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return context.getString(R.string.tab_movie);
        }else if (position == 1){
            return context.getString(R.string.tab_tv);
        }  return "";
    }



    @Override
    public int getCount() {
        return 3;
    }
}
