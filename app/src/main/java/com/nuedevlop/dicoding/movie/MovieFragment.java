package com.nuedevlop.dicoding.movie;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nuedevlop.dicoding.MainActivity;
import com.nuedevlop.dicoding.R;
import com.nuedevlop.dicoding.utils.Resources;
import com.nuedevlop.dicoding.utils.Utils;

import static android.content.Context.MODE_PRIVATE;


public class MovieFragment extends Fragment {
    private Context context;
    private SharedPreferences local;
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;
    private View view;
    private ProgressBar progressBar;


    public MovieFragment() {

        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        local = context.getSharedPreferences("local", MODE_PRIVATE);
        progressBar = view.findViewById(R.id.progressBar);
        this.view = view;
        getData();



    }

    private void getData() {
        if (!Utils.isNetworkAvailable(context)) {
            Toast.makeText(context,
                    "No internet ..Please connect to internet and start app again",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        showProgress();



        String url = Resources.BASE_URL_APPLICATION+Resources.MOVIE+"?api_key=" + Resources.api_key + "&language="+local.getString("api", "en-US");

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    Log.e("api response", response);

                    MovieResult movieResult = (MovieResult) Utils.jsonToPojo(response, MovieResult.class);


                    if (movieResult.getResults() != null &&
                            movieResult.getResults().size() > 0) {

                        RecyclerView recyclerView = view.findViewById(R.id.recMovie);
                        LinearLayoutManager llm = new LinearLayoutManager(context);
                        llm.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(llm);
                        MovieAdapter adapter = new MovieAdapter(context, movieResult.getResults());
                        recyclerView.setAdapter(adapter);
                        hideProgress();

                    } else {
                        Log.e("tag", "list empty==");
                        Toast.makeText(context, "Movie Not Found", Toast.LENGTH_SHORT).show();
                        hideProgress();
                    }

                }, error ->
                Toast.makeText(context,
                        "That didn't work! :(",
                        Toast.LENGTH_SHORT).show()
        );

// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);

    }
    private void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.main_menu, menu);
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.menuSearch);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.i("onQueryTextChange", newText);

                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);
                    searchView.clearFocus();
                    if (query.length()== 0 ){
                        getData();
                    }else {search(query);}


                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void search(String tittle) {

        if (!Utils.isNetworkAvailable(context)) {
            Toast.makeText(context,
                    "No internet ..Please connect to internet and start app again",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        showProgress();


        String url = "https://api.themoviedb.org/3/search/movie?api_key="+Resources.api_key+"&language="+local.getString("api", "en-US")+"&query="+tittle;
        //String url = Resources.BASE_URL_APPLICATION+Resources.MOVIE+"?api_key=" + Resources.api_key + "&language="+local.getString("api", "en-US");

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    Log.e("api response", response);

                    MovieResult movieResult = (MovieResult) Utils.jsonToPojo(response, MovieResult.class);


                    if (movieResult.getResults() != null &&
                            movieResult.getResults().size() > 0) {

                        RecyclerView recyclerView = view.findViewById(R.id.recMovie);
                        LinearLayoutManager llm = new LinearLayoutManager(context);
                        llm.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(llm);
                        MovieAdapter adapter = new MovieAdapter(context, movieResult.getResults());
                        recyclerView.setAdapter(adapter);
                        hideProgress();

                    } else {
                        Log.e("tag", "list empty==");
                        Toast.makeText(context, "Movie Not Found", Toast.LENGTH_SHORT).show();
                        hideProgress();
                    }

                }, error ->
                Toast.makeText(context,
                        "That didn't work! :(",
                        Toast.LENGTH_SHORT).show()
        );

// Add the request to the RequestQueue.
        queue.add(stringRequest);




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSearch:
                // Not implemented here
                searchView.setIconifiedByDefault(false);
                searchView.setQueryHint("Movie Tittle");
                return false;
            default:
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


}

