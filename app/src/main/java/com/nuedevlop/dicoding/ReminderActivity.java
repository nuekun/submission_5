package com.nuedevlop.dicoding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.nuedevlop.dicoding.utils.Reminder;

import java.util.Objects;

public class ReminderActivity extends AppCompatActivity {
    SwitchCompat swDaily,swRelease;
    private Reminder reminder;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferenceEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Reminder");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
        pref();

        swDaily.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sharedPreferenceEditor = sharedPreferences.edit();
            sharedPreferenceEditor.putBoolean("daily", isChecked);
            sharedPreferenceEditor.apply();

            if (isChecked){
                reminder.setDailyReminder();
            }else reminder.cancelDailyReminder(getApplicationContext());


        });

        swRelease.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sharedPreferenceEditor = sharedPreferences.edit();
            sharedPreferenceEditor.putBoolean("realease", isChecked);
            sharedPreferenceEditor.apply();
            if (isChecked){
                reminder.setReleaseTodayReminder();
            }else reminder.cancelReleaseToday(getApplicationContext());


        });


    }

    private void pref() {
        Boolean dailyStat = sharedPreferences.getBoolean("daily", false);
        Boolean realeaseStat = sharedPreferences.getBoolean("realease", false);
        swRelease.setChecked(realeaseStat);
        swDaily.setChecked(dailyStat);
    }

    private void init() {
        sharedPreferences = getSharedPreferences("reminder", Context.MODE_PRIVATE);
        reminder = new Reminder(this);

        swDaily = findViewById(R.id.swDaily);
        swRelease = findViewById(R.id.swRelease);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return true;
    }

}
