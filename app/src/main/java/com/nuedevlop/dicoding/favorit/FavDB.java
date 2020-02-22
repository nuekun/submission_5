package com.nuedevlop.dicoding.favorit;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Favorit.class}, version = 1)
public abstract class FavDB extends RoomDatabase {
    public abstract FavDAO getFavDAO();
}
