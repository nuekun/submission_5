package com.nuedevlop.dicoding.favorit;

import android.database.Cursor;
import android.graphics.Movie;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
@Dao
public interface FavDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Favorit... favorits);

    @Query("DELETE FROM Favorit WHERE idFav = :idFav")
    void deleteByidFav(int idFav);

    @Query("SELECT * FROM Favorit WHERE idFav = :idFav")
    Cursor selectByid(long idFav);

    @Query("SELECT COUNT(idFav) FROM Favorit WHERE tittle = :title")
    int getMovieByTitle(String title);

    @Query("SELECT * FROM Favorit WHERE type = :type")
    List<Favorit> getFavoritByType(String type);

    @Query("SELECT * FROM Favorit")
    List<Favorit> getAllFav();

    @Query("SELECT * FROM Favorit")
    Cursor getALL();

}
