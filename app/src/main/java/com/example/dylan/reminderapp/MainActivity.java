package com.example.dylan.reminderapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.GregorianCalendar;

import static com.example.dylan.reminderapp.R.color.grey;

public class MainActivity extends AppCompatActivity {

    public TextView textView;
    public Button addButton;
    public Button viewButton;
    public Button settingsButton;
    public Button aboutButton;
    public ImageView imageView;
    public String title;
    public String dateTime;
    public String urgency;
    public String topic;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        textView.setBackgroundColor(getResources().getColor(grey));
        addButton = findViewById(R.id.addButton);
        viewButton = findViewById(R.id.viewButton);
        aboutButton = findViewById(R.id.aboutButton);
        settingsButton = findViewById(R.id.settingsdButton);
        imageView = findViewById(R.id.imageView);



        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listIntent = new Intent(MainActivity.this, addReminderActivity.class);
                listIntent.putExtra("testKey", 2000000);
                startActivity(listIntent);
            }
        });


        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ViewRemindersActivity.class);
                int reminderKey = 325632;
                intent.putExtra("reminderKey", reminderKey);
                startActivity(intent);

            }
        });







    }





}






/*
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {







            }
        });
        */





























