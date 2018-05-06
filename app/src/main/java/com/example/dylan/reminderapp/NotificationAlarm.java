package com.example.dylan.reminderapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class NotificationAlarm extends AppCompatActivity {

    public ArrayList<ReminderObject> reminderList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();

        assert bundle != null;
        int reminderIndex = bundle.getInt("reminderIndex");

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


        reminderList.remove(reminderIndex);
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

        Intent intent = new Intent(NotificationAlarm.this, ViewRemindersActivity.class);
        startActivity(intent);
    }
}



