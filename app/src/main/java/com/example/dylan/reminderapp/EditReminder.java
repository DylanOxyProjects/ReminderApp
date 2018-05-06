package com.example.dylan.reminderapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class EditReminder extends AppCompatActivity{

    Toolbar toolbar;
    TextView viewTitle;
    TextView viewDate;
    TextView viewTime;
    TextView viewUrgency;
    TextView viewTopic;
    EditText editTitle;
    EditText editDate;
    EditText editTime;
    EditText editUrgency;
    EditText editTopic;
    Button save;
    Button delete;
    ArrayList<ReminderObject> reminderList;
    ReminderObject currentReminder;
    String filename = "reminderStorage";




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.edit_reminder);

        viewTitle = findViewById(R.id.titleTextview);
        viewDate = findViewById(R.id.dateTextview);
        viewTime = findViewById(R.id.timeTextView);
        viewUrgency = findViewById(R.id.urgencyTextView);
        viewTopic = findViewById(R.id.topicTextView);
        editTitle = findViewById(R.id.editTitle);
        editDate = findViewById(R.id.editDate);
        editTime = findViewById(R.id.editTime);
        editUrgency = findViewById(R.id.editUrgency);
        editTopic = findViewById(R.id.editTopic);
        save = findViewById(R.id.saveButtton);
        delete = findViewById(R.id.deleteButton);
        toolbar = findViewById(R.id.toolbar);

        Bundle recieveData = getIntent().getExtras();

        assert recieveData != null;
        final int position = recieveData.getInt("position");



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

        currentReminder = reminderList.get(position);


        toolbar.setTitle("Edit Your Reminder! :)");
        editTitle.setHint(currentReminder.reminderTitle);
        editTitle.setText(currentReminder.reminderTitle);
        editDate.setHint(currentReminder.date);
        editDate.setText(currentReminder.date);
        editTime.setHint(currentReminder.time);
        editTime.setText(currentReminder.time);
        editUrgency.setHint(currentReminder.reminderUrgency);
        editUrgency.setText(currentReminder.reminderUrgency);
        editTopic.setHint(currentReminder.reminderTopic);
        editTopic.setText(currentReminder.reminderTopic);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentReminder.reminderTitle = editTitle.getText().toString();
                currentReminder.date = editDate.getText().toString();
                currentReminder.time = editTime.getText().toString();
                currentReminder.reminderDateTime = editDate.getText().toString() + " "+ editTime.getText().toString();
                currentReminder.reminderDateTimeSentence = "Your Reminder Date is: " + editDate.getText().toString() + " and Your Time is: " + editTime.getText().toString();
                currentReminder.reminderUrgency = editUrgency.getText().toString();
                currentReminder.reminderTopic = editTopic.getText().toString();


                reminderList.remove(position);
                reminderList.add(position, currentReminder);

                try{
                    //Saving of object in a file
                    FileOutputStream file = new FileOutputStream(new File(getFilesDir(), filename));
                    ObjectOutputStream out = new ObjectOutputStream(file);

                    // Method for serialization of object
                    out.writeObject(reminderList);

                    out.close();
                    file.close();

                    System.out.println("Reminder Has Been Updated");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(EditReminder.this, ViewRemindersActivity.class);
                startActivity(intent);
            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reminderList.remove(position);

                try{
                    //Saving of object in a file
                    FileOutputStream file = new FileOutputStream(new File(getFilesDir(), filename));
                    ObjectOutputStream out = new ObjectOutputStream(file);

                    // Method for serialization of object
                    out.writeObject(reminderList);

                    out.close();
                    file.close();

                    System.out.println("Reminder Has Been Deleted");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(EditReminder.this, ViewRemindersActivity.class);
                startActivity(intent);

            }
        });




    }
}
