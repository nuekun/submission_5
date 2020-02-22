package com.nuedevlop.dicoding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nuedevlop.dicoding.favorit.FavDAO;
import com.nuedevlop.dicoding.favorit.FavDB;
import com.nuedevlop.dicoding.favorit.Favorit;
import com.nuedevlop.dicoding.movie.Result;
import com.nuedevlop.dicoding.utils.Resources;

import java.util.Objects;

public class MovieActivity extends AppCompatActivity {
    String tittle,popular,date,overview,poster,adult;
    int id;
    TextView txtTittle,txtPopular,txtDate,txtOverview,txtAdult;
    ImageView gbrPoster;
    FavDAO favDAO;
    Favorit favorit;
    public static final String ExtraMovie = "movie";
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Result result = getIntent().getParcelableExtra(ExtraMovie);

        assert result != null;
        poster = result.getPosterPath();
        tittle = result.getTitle();
        popular = result.getPopularity().toString();
        date = result.getReleaseDate();
        overview = result.getOverview();
        adult = result.getAdult().toString();
        id = result.getId()+1010;

        favorit = new Favorit();
        favorit.setIdFav(id);
        favorit.setTittle(tittle);
        favorit.setFirst(date);
        favorit.setSecond(adult);
        favorit.setThird(popular);
        favorit.setOverview(overview);
        favorit.setPoster(poster);
        favorit.setType("movie");

        txtAdult = findViewById(R.id.txtMovieAdult);
        gbrPoster = findViewById(R.id.gbrMoviePoster);
        txtDate = findViewById(R.id.txtMovieDate);
        txtTittle = findViewById(R.id.txtMovieTittle);
        txtPopular = findViewById(R.id.txtMoviePopular);
        txtOverview = findViewById(R.id.txtMovieOverview);

        txtAdult.setText(adult);
        txtDate.setText(date);
        txtOverview.setText(overview);
        txtPopular.setText(popular);
        txtTittle.setText(tittle);


        String URL = Resources.BASE_URL_IMAGE_W_500+poster;
        Glide.with(this).load(URL).into(gbrPoster);

        Objects.requireNonNull(getSupportActionBar()).setTitle(tittle);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        favDAO = Room.databaseBuilder(this, FavDB.class, "db_fav")
                .allowMainThreadQueries()
                .build()
                .getFavDAO();





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (favDAO.getMovieByTitle(favorit.getTittle()) > 0){
            getMenuInflater().inflate(R.menu.remove_menu, menu);
            this.menu = menu;
        }else
        getMenuInflater().inflate(R.menu.add_menu, menu);
        this.menu = menu;
        return true;
    }



    //    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true;
//    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
            case R.id.menuFav:
                try {
                    if (favDAO.getMovieByTitle(favorit.getTittle()) > 0) {
                        favDAO.deleteByidFav(id);
                        Toast.makeText(this, "deleted from fav", Toast.LENGTH_SHORT).show();
                        menu.clear();
                        getMenuInflater().inflate(R.menu.add_menu, menu);
                    } else {
                        favDAO.insert(favorit);
                        setResult(RESULT_OK);
                       Toast.makeText(this, "sucsess", Toast.LENGTH_SHORT).show();
                        menu.clear();
                        getMenuInflater().inflate(R.menu.remove_menu, menu);
                    }
                } catch (SQLiteConstraintException e) {
                    Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
        }
        return true;
    }

}
