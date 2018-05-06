package com.example.dylan.reminderapp;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.app.NotificationManager;


public class AlarmReceiver extends BroadcastReceiver
{

    NotificationCompat.Builder notification;
    private static final int UNIQUE_ID = 56856;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override


    public void onReceive(Context context, Intent intent)
    {

        Bundle bundle = intent.getExtras();
        assert bundle != null;
        String reminderTitle = bundle.getString("reminderTitle");
        String reminderTopic = bundle.getString("reminderTopic");
        String reminderUrgency = bundle.getString("reminderUrgency");
        int reminderIndex = bundle.getInt("reminderIndex");

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        long[] v = {500, 3000};

        // Your code to execute when the alarm triggers
        // and the broadcast is received.
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.remindericon)
                .setContentTitle("REMINDER: " + reminderTitle)
                .setContentText("Reminder Urgency: " + reminderUrgency + ", Topic: " +reminderTopic)
                .setSound(alarmSound)
                .setVibrate(v).setPriority(NotificationManager.IMPORTANCE_HIGH)
                .setAutoCancel(true);



        Intent notificationIntent  = new Intent(context, NotificationAlarm.class);
        notificationIntent.putExtra("reminderIndex", reminderIndex);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,  notificationIntent , PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(contentIntent);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        assert mNotificationManager != null;
        mNotificationManager. notify(UNIQUE_ID, mBuilder.build());


    }
}