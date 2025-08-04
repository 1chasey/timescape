package com.example.timescape;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

//activity class for stopping alarm
public class stopping extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //parent class call
        super.onCreate(savedInstanceState);
        //implementing the stopping.xml layout
        setContentView(R.layout.stopping);

        //getting the label string from intent
        String label = getIntent().getStringExtra("label");
        TextView textView = findViewById(R.id.timesUpMessage);
        textView.setText(label);

        //allows the stop button to be found
        Button stopButton=findViewById(R.id.stopButton);
        //onClickListener allows the action to proceed when the button is clicked
        stopButton.setOnClickListener(v -> {
            //returning back to the main activity
            Intent intent=new Intent(stopping.this, activity_main.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        });
    }
}

