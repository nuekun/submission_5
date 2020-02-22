package com.nuedevlop.dicoding;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.nuedevlop.dicoding.utils.PagerAdapter;

import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences local;
    ViewPager pageMain;
    PagerAdapter pagerAdapter;
    TabLayout tabMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        pageMain = findViewById(R.id.mainPage);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(),MainActivity.this);
        pageMain.setAdapter(pagerAdapter);
        tabMain = findViewById(R.id.mainTab);
        tabMain.setupWithViewPager(pageMain);
        Objects.requireNonNull(tabMain.getTabAt(2)).setIcon(R.drawable.ic_favorite_black_24dp);
        LinearLayout layout = ((LinearLayout) ((LinearLayout) tabMain.getChildAt(0)).getChildAt(2));
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) layout.getLayoutParams();
        layoutParams.weight = 0.4f; // e.g. 0.5f
        layout.setLayoutParams(layoutParams);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.app_name);
        getSupportActionBar().getThemedContext().setTheme(R.style.Appbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_alarm_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        local = getSharedPreferences("local", MODE_PRIVATE);




    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_menu,menu);
//        searchView = (SearchView) menu.findItem(R.id.menuSearch).getActionView();
//        return true;
//    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       switch (item.getItemId()){
           case R.id.menuEnglish:
               Toast.makeText(MainActivity.this, "english", Toast.LENGTH_SHORT).show();
               local.edit().putString("api", "en-US").apply();
               local.edit().putString("language", "en-us").apply();
               finish();
               updateResources(this,"en-us");
               break;
           case R.id.menuIndo:
               Toast.makeText(MainActivity.this, "indonesia", Toast.LENGTH_SHORT).show();
               local.edit().putString("api", "id").apply();
               local.edit().putString("language", "in").apply();
               finish();
               updateResources(this,"in");
               break;
           case android.R.id.home:
               Intent intent = new Intent(this,ReminderActivity.class);
               startActivity(intent);
               break;
            }

        return super.onOptionsItemSelected(item);
    }
    private static void updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();

        configuration.setLocale(locale);

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        Intent intent = new Intent(context , MainActivity.class);
        context.startActivity(intent);



    }

    @Override
    protected void onStart() {
        super.onStart();

        Locale locale = new Locale(local.getString("language", "en-us"));
        Locale.setDefault(locale);

        Resources resources = MainActivity.this.getResources();

        Configuration configuration = resources.getConfiguration();

        configuration.setLocale(locale);



    }

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


}
