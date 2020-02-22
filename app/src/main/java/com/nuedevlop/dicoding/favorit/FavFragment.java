package com.nuedevlop.dicoding.favorit;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nuedevlop.dicoding.R;

import java.util.List;


public class FavFragment extends Fragment {
    private Context context;
    private ProgressDialog dialog;
    private View view;

    public FavFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav, container, false);



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view=view;



        getData();
    }

    private void getData() {
        showProgress();




        List<Favorit> favoritsMovie = loadFavMovies();
        RecyclerView recMovie = view.findViewById(R.id.recFavMovie);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recMovie.setLayoutManager(llm);
        FavMovieAdapter favMovieAdapter = new FavMovieAdapter(context, favoritsMovie);
        recMovie.setAdapter(favMovieAdapter);

        TextView txtMovie = view.findViewById(R.id.txtFavMovieCount);
        txtMovie.setText("movie ("+favoritsMovie.size()+")");

        List<Favorit> favoritsTv = loadFavTV();
        RecyclerView recTV = view.findViewById(R.id.recFavTV);
        LinearLayoutManager llmtv = new LinearLayoutManager(context);
        llmtv.setOrientation(LinearLayoutManager.HORIZONTAL);
        recTV.setLayoutManager(llmtv);
        FavTvAdapter favTvAdapter = new FavTvAdapter(context,favoritsTv);
        recTV.setAdapter(favTvAdapter);

        TextView txtTV = view.findViewById(R.id.txtFavTvCount);
        txtTV.setText("tv series ("+loadFavTV().size()+")");

        hideProgress();

    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    private void showProgress() {
        dialog = ProgressDialog.show(context, "downloading data",
                "Loading. Please wait...", true);

        dialog.show();
    }
    private void hideProgress() {
        dialog.hide();
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }


    private List<Favorit> loadFavMovies() {
        FavDB database = Room.databaseBuilder(context, FavDB.class, "db_fav")
                .allowMainThreadQueries()
                .build();
        FavDAO favDAO = database.getFavDAO();
                return favDAO.getFavoritByType("movie");
    }

    private List<Favorit> loadFavTV() {
        FavDB database = Room.databaseBuilder(context, FavDB.class, "db_fav")
                .allowMainThreadQueries()
                .build();
        FavDAO favDAO = database.getFavDAO();
        return favDAO.getFavoritByType("tv");
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
}
