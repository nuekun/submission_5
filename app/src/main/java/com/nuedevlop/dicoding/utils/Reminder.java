package com.nuedevlop.dicoding.utils;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nuedevlop.dicoding.MainActivity;
import com.nuedevlop.dicoding.R;
import com.nuedevlop.dicoding.ReminderActivity;
import com.nuedevlop.dicoding.movie.MovieResult;
import com.nuedevlop.dicoding.movie.Result;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class Reminder extends BroadcastReceiver {
    private static final String EXTRA_TYPE = "type";
    private static final String TYPE_DAILY = "daily_reminder";
    private static final String TYPE_RELEASE = "release_reminder";
    private static final int ID_DAILY_REMINDER = 1000;
    private static final int ID_RELEASE_TODAY = 1001;
    private Context context;

    public Reminder(Context context){
        this.context = context;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra(EXTRA_TYPE);
        assert type != null;
        if (type.equals(TYPE_DAILY)) {
            showDailyReminder(context);
        } else if (type.equals(TYPE_RELEASE)) {
            getReleaseToday(context);
        }
    }

    private Calendar getReminderTime(String type) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, type.equals(TYPE_DAILY) ? 7 : 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }

        return calendar;
    }

    private Intent getReminderIntent(String type) {
        Intent intent = new Intent(context, ReminderActivity.class);
        intent.putExtra(EXTRA_TYPE, type);
        return intent;
    }

    public void setReleaseTodayReminder() {




        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE_TODAY, getReminderIntent(TYPE_RELEASE), 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, getReminderTime(TYPE_RELEASE).getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        Toast.makeText(context, "realese reminder active", Toast.LENGTH_SHORT).show();
        getReleaseToday(context);
    }

    public void setDailyReminder() {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY_REMINDER, getReminderIntent(TYPE_DAILY), 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, getReminderTime(TYPE_DAILY).getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(context, "daily reminder active", Toast.LENGTH_SHORT).show();
        showDailyReminder(context);
    }

    private void getReleaseToday(final Context context) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        final String now = dateFormat.format(date);

        String url = "https://api.themoviedb.org/3/discover/movie?api_key="+Resources.api_key+
                "&primary_release_date.gte="+now+"&primary_release_date.lte="+now;


        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    Log.e("api response", response);

                    MovieResult movieResult = (MovieResult) Utils.jsonToPojo(response, MovieResult.class);




                    if (movieResult.getResults() != null &&
                            movieResult.getResults().size() > 0) {

                        List<Result> movies = movieResult.getResults();
                        int id = 2;

                        for (Result result : movies){
                            String title = result.getTitle();
                            String desc = title +" has been release today";
                            showReleaseToday(context,title,desc,id);
                        }


                    }
                }, error -> Log.e("tag", "list empty==")
        );
        queue.add(stringRequest);

    }

    private void showReleaseToday(Context context, String title, String desc, int id) {
        String CHANNEL_ID = "Channel_2";
        String CHANNEL_NAME = "Today release channel";






        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri uriRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_local_movies_black_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_local_movies_black_24dp))
                .setContentTitle(title)
                .setContentText(desc)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(uriRingtone)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            mBuilder.setChannelId(CHANNEL_ID);

            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }
        Notification notification = mBuilder.build();
        if (mNotificationManager != null) {
            mNotificationManager.notify(id, notification);
        }


    }

    private void showDailyReminder(Context context) {
        int NOTIFICATION_ID = 1;
        String CHANNEL_ID = "Channel_1";
        String CHANNEL_NAME = "Daily Reminder channel";





        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri uriRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_local_movies_black_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_local_movies_black_24dp))
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setContentText("See what popular movie today !")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(uriRingtone)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            mBuilder.setChannelId(CHANNEL_ID);

            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }
        Notification notification = mBuilder.build();
        if (mNotificationManager != null) {
            mNotificationManager.notify(NOTIFICATION_ID, notification);
        }

    }

    private void cancelReminder(Context context, String type) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderActivity.class);
        int requestCode = type.equalsIgnoreCase(TYPE_DAILY) ? ID_DAILY_REMINDER : ID_RELEASE_TODAY;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    public void cancelDailyReminder(Context context) {
        cancelReminder(context, TYPE_DAILY);
    }

    public void cancelReleaseToday(Context context) {
        cancelReminder(context, TYPE_RELEASE);
    }
}
