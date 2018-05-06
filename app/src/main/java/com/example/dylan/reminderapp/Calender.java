package com.example.dylan.reminderapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CalendarView;



public class Calender extends AppCompatActivity{

    public CalendarView calender;
    private static final String TAG = "CalenderActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calender_layout);
        calender = findViewById(R.id.calendarView);

        Bundle bundle = getIntent().getExtras();
        final int key = bundle.getInt("key");

        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //mm/dd/yyyy
                month++;
                String date = month + "/" + dayOfMonth + "/" + year;
                Log.d(TAG, "onSelectedDayChange: mm/dd/yyyy: " + date);

                Intent intent = new Intent(Calender.this, TitlePopUp.class);
                intent.putExtra("date", date);
                intent.putExtra("key", key);
                intent.putExtra("inputType", "Time   Ex: 12:00 PM");
                startActivity(intent);
            }
        });
    }
}
