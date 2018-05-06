package com.example.dylan.reminderapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import static com.example.dylan.reminderapp.R.color.colorAccent;
import static com.example.dylan.reminderapp.R.color.grey;

public class addReminderActivity extends AppCompatActivity {

    public TextView textView;
    public Button titleBTN;
    public Button dateBTN;
    public Button urgencyBTN;
    public Button topicBTN;
    public Button createBTN;
    public Button backButton;
    public TextView titleView;
    public TextView calenderView;
    public TextView urgencyView;
    public TextView topicView;
    static ReminderObject currentReminder;
    static ArrayList<ReminderObject> reminderList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_reminder_layout);
        textView = findViewById(R.id.textView);
        textView.setBackgroundColor(getResources().getColor(grey));
        titleBTN = findViewById(R.id.addTitle);
        dateBTN = findViewById(R.id.addDate);
        urgencyBTN = findViewById(R.id.addUrgency);
        topicBTN = findViewById(R.id.addTopic);
        createBTN = findViewById(R.id.addButton);
        backButton = findViewById(R.id.backButton);

        //receive data to put into views
        Bundle recieveData = getIntent().getExtras();

        //use test key to determine if we should create a new reminderobject or not
        if (recieveData != null && recieveData.getInt("testKey") == 2000000) {
            currentReminder = new ReminderObject();
        }

        int key = 0;
        if (recieveData != null) {
            key = recieveData.getInt("key");
        }

        String userInput;
        if (recieveData != null && key == 4) {
            userInput = recieveData.getString("userInput");
            currentReminder.reminderTitle = userInput;
            currentReminder.reminderTitleSentence = "Your Reminder Title is "+ userInput;
        }

        else if (recieveData != null && key == 1) {
            userInput = recieveData.getString("userInput");
            String date = recieveData.getString("date");
            currentReminder.reminderDateTime = date + "SEPERATOR" + userInput;
            currentReminder.reminderDateTimeSentence = "Your Reminder Date is: " + date + " and Your Time is: " + userInput;
            currentReminder.date = date;
            currentReminder.time = userInput;
        }

        else if (recieveData != null && key == 2) {
            userInput = recieveData.getString("userInput");
            currentReminder.reminderUrgency = userInput;
            currentReminder.reminderUrgencySentence = "Your Reminder Urgency is: " + userInput;
        }

        else if (recieveData != null && key == 3) {
            userInput = recieveData.getString("userInput");
            currentReminder.reminderTopic = userInput;
            currentReminder.reminderTopicSentence = "Your Reminder Topic is: " + userInput;
        }

        // set reminder views
        titleView = findViewById(R.id.titleView);
        titleView.setText(currentReminder.reminderTitleSentence);
        titleView.setBackgroundColor(getResources().getColor(colorAccent));
        titleView.setTextSize(25);
        calenderView = findViewById(R.id.calenderView);
        calenderView.setText(currentReminder.reminderDateTimeSentence);
        calenderView.setBackgroundColor(getResources().getColor(colorAccent));
        calenderView.setTextSize(25);
        urgencyView = findViewById(R.id.urgencyView);
        urgencyView.setBackgroundColor(getResources().getColor(colorAccent));
        urgencyView.setText(currentReminder.reminderUrgencySentence);
        urgencyView.setTextSize(25);
        topicView = findViewById(R.id.topicView);
        topicView.setText(currentReminder.reminderTopicSentence);
        topicView.setBackgroundColor(getResources().getColor(colorAccent));
        topicView.setTextSize(25);

        titleBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(addReminderActivity.this, TitlePopUp.class);
                intent.putExtra("inputType", "Reminder Title");
                intent.putExtra("key", 4);
                startActivity(intent);

            }
        });

        dateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(addReminderActivity.this, Calender.class);
                intent.putExtra("key", 1);
                startActivity(intent);
            }
        });

        urgencyBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(addReminderActivity.this, TitlePopUp.class);
                intent.putExtra("inputType", "High, Medium, or Low");
                intent.putExtra("key", 2);
                startActivity(intent);
            }
        });

        topicBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(addReminderActivity.this, TitlePopUp.class);
                intent.putExtra("inputType", "Topic");
                intent.putExtra("key", 3);
                startActivity(intent);
            }
        });

        createBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(addReminderActivity.this, ViewRemindersActivity.class);
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


                reminderList.add(currentReminder);

                // serilization
                try{
                    //Saving of object in a file
                    FileOutputStream file = new FileOutputStream(new File(getFilesDir(), filename));
                    ObjectOutputStream out = new ObjectOutputStream(file);

                    // Method for serialization of object
                    out.writeObject(reminderList);

                    out.close();
                    file.close();

                    System.out.println("Object has been serialized");
                } catch (IOException e) {
                    e.printStackTrace();
                }


                startActivity(intent);


            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(addReminderActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

}
