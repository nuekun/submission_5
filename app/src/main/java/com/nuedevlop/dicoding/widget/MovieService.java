package com.nuedevlop.dicoding.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class MovieService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new MovieFactory(this.getApplicationContext());
    }
}
