package com.nuedevlop.dicoding.utils;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.nuedevlop.dicoding.favorit.FavDAO;
import com.nuedevlop.dicoding.favorit.FavDB;

import java.util.Objects;

public class MyContentProvider extends ContentProvider {
    public MyContentProvider() {}

    private FavDAO favDAO;
    private static final String DBNAME = "db_fav";
    private static final String DB_TABLE = "Favorit";
    private static final String AUTHORITY = "com.nuedevlop.dicoding";
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, DB_TABLE, 1);
        uriMatcher.addURI(AUTHORITY, DB_TABLE + "/#", 2);
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(@NonNull Uri uri) {
       return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public boolean onCreate() {
        FavDB favDB = Room.databaseBuilder(Objects.requireNonNull(getContext()), FavDB.class, DBNAME).build();
        favDAO = favDB.getFavDAO();
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
       final int code = uriMatcher.match(uri);
       if (code ==1 || code == 2){
           final Context context = getContext();
           if(context==null)
               return null;
           final Cursor cursor;
           if (code == 1)
               cursor = favDAO.getALL();
           else
               cursor = favDAO.selectByid(ContentUris.parseId(uri));
           cursor.setNotificationUri(context.getContentResolver(),uri);
           return cursor;
       } else {
           throw new IllegalArgumentException("Uri?" +uri);
       }

    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }
}
