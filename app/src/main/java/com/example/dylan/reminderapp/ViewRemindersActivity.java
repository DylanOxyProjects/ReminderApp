package com.example.dylan.reminderapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.Locale;

public class ViewRemindersActivity extends AppCompatActivity{

    public ArrayList<ReminderObject> reminderList = new ArrayList<>();
    public Button mainMenuButton;
    public Button addMenuButton;
    public Button delete;
    public ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Deserialization
        String filename = "reminderStorage";
        try{
            // Reading the object from a file
            // FileOutputStream file = new FileOutputStream(new File(getFilesDir(), filename));
            FileInputStream file = new FileInputStream(new File(getFilesDir(), filename));
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            reminderList = (ArrayList)in.readObject();
            in.close();
            file.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        setContentView(R.layout.reminder_list_view);
        mainMenuButton = findViewById(R.id.mainMenuBTN);
        addMenuButton = findViewById(R.id.addReminderBTN);
        delete = findViewById(R.id.deleteReminders);
        listView = findViewById(R.id.reminderListView);


        ReminderAdapter reminderAdapter = new ReminderAdapter(this, reminderList);
        listView.setAdapter(reminderAdapter);

        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewRemindersActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        addMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentlol = new Intent(ViewRemindersActivity.this, addReminderActivity.class);
                intentlol.putExtra("testKey", 2000000);
                startActivity(intentlol);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File dir = getFilesDir();
                File file = new File(dir, "reminderStorage");
                boolean deleted = file.delete();

                Intent intent = new Intent(ViewRemindersActivity.this, ViewRemindersActivity.class);
                startActivity(intent);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ViewRemindersActivity.this, EditReminder.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

         System.out.print("f");
        try {
            reminderLooper();
        } catch (ParseException e) {
            e.printStackTrace();
        }
     }

    public void reminderLooper() throws ParseException {

        for (int count = 0; count < reminderList.size(); count++){
            scheduleAlarm(reminderList.get(count), count);
        }

    }

    // this method will need to be called from inside a loop that loops through each reminder
    // this method needs to be in the View Reminders Class because thats the first place the user
    // goes after creating a new reminder
    public void scheduleAlarm(ReminderObject currentReminder, int reminderIndex) throws ParseException {

        int hours = 00;
        int minutes = 00;
        String date = currentReminder.date;
        char[] dateArray = date.toCharArray();
        int day = Character.getNumericValue(dateArray[2]);
        int month = Character.getNumericValue(dateArray[0]);
        int yearDigit0 =Character.getNumericValue(dateArray[4]);
        int yearDigit1 = Character.getNumericValue(dateArray[5]);
        int yearDigit2 = Character.getNumericValue(dateArray[6]);
        int yearDigit3 = Character.getNumericValue(dateArray[7]);
        int[] yearDigits = new int[4];
        yearDigits[0] = yearDigit0;
        yearDigits[1] = yearDigit1;
        yearDigits[2] = yearDigit2;
        yearDigits[3] = yearDigit3;

        StringBuilder strNum = new StringBuilder();

        for (int count : yearDigits) {
            strNum.append(count);
        }
        int year = Integer.parseInt(strNum.toString());

        //    12:00 pm
        String time1 = currentReminder.time;

        if (time1.toLowerCase().contains("pm") && time1.toLowerCase().contains("12:")){
            char[] timeArray = time1.toCharArray();
            char[] fixedTimeArray = new char[8];
            if (timeArray.length != 8){
                for (int count = 0; count < 8; count++){

                    if (count == 0){
                        fixedTimeArray[0] = '0';
                    }
                    else{
                        fixedTimeArray[count] = timeArray[count - 1];
                    }
                }
            }
            else{
                fixedTimeArray = timeArray;
            }
            hours = 12;
            int minuteDigit0 = Character.getNumericValue(fixedTimeArray[3]);
            int minuteDigit1 = Character.getNumericValue(fixedTimeArray[4]);
            StringBuilder minuteBuilder = new StringBuilder();
            minuteBuilder.append(minuteDigit0);
            minuteBuilder.append(minuteDigit1);
            minutes = Integer.parseInt(minuteBuilder.toString());
        }

        else if(time1.toLowerCase().contains("pm") && !time1.toLowerCase().contains("12:")){
            char[] timeArray = time1.toCharArray();
            char[] fixedTimeArray = new char[8];
            if (timeArray.length != 8){
                for (int count = 0; count < 8; count++){

                    if (count == 0){
                        fixedTimeArray[0] = '0';
                    }
                    else{
                        fixedTimeArray[count] = timeArray[count - 1];
                    }
                }
            }
            else{
                fixedTimeArray = timeArray;
            }
            int hourDigit0 = Character.getNumericValue(fixedTimeArray[0]);
            int hourDigit1 = Character.getNumericValue(fixedTimeArray[1]);
            StringBuilder hourBuilder = new StringBuilder();
            hourBuilder.append(hourDigit0);
            hourBuilder.append(hourDigit1);
            hours = Integer.parseInt(hourBuilder.toString()) + 12;

            int minuteDigit0 = Character.getNumericValue(fixedTimeArray[3]);
            int minuteDigit1 = Character.getNumericValue(fixedTimeArray[4]);
            StringBuilder minuteBuilder = new StringBuilder();
            minuteBuilder.append(minuteDigit0);
            minuteBuilder.append(minuteDigit1);
            minutes = Integer.parseInt(minuteBuilder.toString());

    }

        else if(time1.toLowerCase().contains("am") && !time1.toLowerCase().contains("12:")){
            char[] timeArray = time1.toCharArray();
            char[] fixedTimeArray = new char[8];
            if (timeArray.length != 8){
                for (int count = 0; count < 8; count++){

                    if (count == 0){
                        fixedTimeArray[0] = '0';
                    }
                    else{
                        fixedTimeArray[count] = timeArray[count - 1];
                    }
                }
            }
            else{
                fixedTimeArray = timeArray;
            }
            int hourDigit0 = Character.getNumericValue(fixedTimeArray[0]);
            int hourDigit1 = Character.getNumericValue(fixedTimeArray[1]);
            if (hourDigit1 < 0){
                hourDigit1 = 0;
            }
            StringBuilder hourBuilder = new StringBuilder();
            hourBuilder.append(hourDigit0);
            hourBuilder.append(hourDigit1);
            hours = Integer.parseInt(hourBuilder.toString());
            int minuteDigit0 = Character.getNumericValue(fixedTimeArray[3]);
            int minuteDigit1 = Character.getNumericValue(fixedTimeArray[4]);
            StringBuilder minuteBuilder = new StringBuilder();
            minuteBuilder.append(minuteDigit0);
            minuteBuilder.append(minuteDigit1);
            minutes = Integer.parseInt(minuteBuilder.toString());
        }

        else if(time1.toLowerCase().contains("am") && time1.toLowerCase().contains("12:")) {
            char[] timeArray = time1.toCharArray();
            char[] fixedTimeArray = new char[8];
            if (timeArray.length != 8){
                for (int count = 0; count < 8; count++){

                    if (count == 0){
                        fixedTimeArray[0] = '0';
                    }
                    else{
                        fixedTimeArray[count] = timeArray[count - 1];
                    }
                }
            }
            else{
                fixedTimeArray = timeArray;
            }
            int minuteDigit0 = Character.getNumericValue(fixedTimeArray[3]);
            int minuteDigit1 = Character.getNumericValue(fixedTimeArray[4]);
            StringBuilder minuteBuilder = new StringBuilder();
            minuteBuilder.append(minuteDigit0);
            minuteBuilder.append(minuteDigit1);
            minutes = Integer.parseInt(minuteBuilder.toString());
            hours = 0;

        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm z");
        long reminderMiliSecs = 0;
        // reminder date milisecs
        try{
            date = date.replace("/", "-");
            String reminderString = date + " " + hours + ":" + minutes + " PDT";
            Date reminderDate = simpleDateFormat.parse(reminderString);
            String reminderTimeString = reminderDate.toString();
            Date currentTime = Calendar.getInstance().getTime();
            String currentTimeString = currentTime.toString();
            reminderMiliSecs = reminderDate.getTime();
        }catch (ParseException e) {
            e.printStackTrace();
        }

        long currentTime = System.currentTimeMillis();
        long timer = reminderMiliSecs - currentTime;

        double seconds = timer * .001;

        System.out.print("test");


        Intent intentAlarm = new Intent(this, AlarmReceiver.class);
        intentAlarm.putExtra("reminderTitle", currentReminder.reminderTitle);
        intentAlarm.putExtra("reminderTopic", currentReminder.reminderTopic);
        intentAlarm.putExtra("reminderUrgency", currentReminder.reminderUrgency);
        intentAlarm.putExtra("reminderIndex", reminderIndex);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        // Set the alarm for a particular time.
        alarmManager.set(AlarmManager.RTC_WAKEUP, reminderMiliSecs, PendingIntent.getBroadcast(this,
                1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
        Toast.makeText(this, "Alarms Are Scheduled!", Toast.LENGTH_LONG).show();



    }
}


