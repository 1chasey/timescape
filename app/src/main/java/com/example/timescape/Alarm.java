package com.example.timescape;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import android.util.Log;

//activity class for the alarm
public class Alarm extends BroadcastReceiver {

    @Override
    //method called when intent is received
    public void onReceive(Context context, Intent intent) {
        //small messages to show alarm is going off
        Log.d("AlarmReceiver", "Alarm triggered");

        Toast.makeText(context, "Alarm triggered", Toast.LENGTH_SHORT).show();

        //gives the application the ability to stop the alarm when rung
        String label = intent.getStringExtra("label");
        Intent alarmIntent = new Intent(context, stopping.class);
        alarmIntent.putExtra("label", label);
        alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(alarmIntent);
    }
}
