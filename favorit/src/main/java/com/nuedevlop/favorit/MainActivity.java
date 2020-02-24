package com.nuedevlop.favorit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
RecyclerView recyclerView;
private adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recMain);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new adapter(getApplicationContext());
        recyclerView.setAdapter(adapter);

        getSupportLoaderManager().initLoader(1, null, this);

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        switch (id) {
            case 1:
                return new CursorLoader(getApplicationContext(), Utils.CONTENT_URI, null, null, null, null);
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == 1) {
            try {

                adapter.setData(data);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        if (loader.getId() == 1) {
            adapter.setData(null);
        }
    }
}
