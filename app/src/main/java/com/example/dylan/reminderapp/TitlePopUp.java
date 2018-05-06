package com.example.dylan.reminderapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.dylan.reminderapp.R.color.colorAccent;
import static com.example.dylan.reminderapp.R.color.white;


public class TitlePopUp extends Activity {

    public EditText editText;
    public Button button;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_layout);
        button = findViewById(R.id.button);
        button.setTextColor(getResources().getColor(white));
        editText = findViewById(R.id.input);


        Bundle inputType = getIntent().getExtras();
        String inputT = inputType.getString("inputType");
        final int key = inputType.getInt("key");
        final String date = inputType.getString("date");
        editText.setHint(inputT);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width), (int) (height * 0.1));


        /*
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_NEXT){
                    String inputText = editText.getText().toString();
                    Toast.makeText(TitlePopUp.this, inputText, Toast.LENGTH_SHORT).show();
                }
                return handled;
            }
        });
        */

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText = editText.getText().toString();
                Intent intent = new Intent(TitlePopUp.this, addReminderActivity.class);
                intent.putExtra("userInput", inputText);
                intent.putExtra("key", key);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });
    }
}
