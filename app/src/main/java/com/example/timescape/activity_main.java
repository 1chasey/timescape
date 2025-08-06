package com.example.timescape;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;
import java.util.Calendar;
import java.util.Locale;

//main activity class where the magic happens
public class activity_main extends AppCompatActivity {
//private variable declaration
    private EditText alarmLabel;
    private TimePicker timePicker;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        //UI variables/constituents
        timePicker = findViewById(R.id.clock);
        alarmLabel =findViewById(R.id.alarmLabel);
        Button buttonSet = findViewById(R.id.set);
        Button buttonStop = findViewById(R.id.Stop);

        //set and stop button activators
        buttonSet.setOnClickListener(v -> setAlarm());
        buttonStop.setOnClickListener(v -> cancelAlarm());

        //android 12+ permission request for alarm
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            if (!alarmManager.canScheduleExactAlarms()) {
                Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivity(intent);
            }
        }


    }

    //method to set alarm
    private void setAlarm() {

        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();
        String label = alarmLabel.getText().toString();

        //calendar is implemented by user's choice
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
        calendar.set(Calendar.MINUTE, timePicker.getMinute());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // If time is in the past, schedule it for the next day
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }

        //allowing the intent to broadcast alarm manager
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, Alarm.class);
        intent.putExtra("label", label);

        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        //small message to show alarm is set or if it is unavailable
        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            Toast.makeText(this, "Alarm set for " + hour + ":" + String.format(Locale.US,"%02d", minute), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "AlarmManager not available", Toast.LENGTH_SHORT).show();
        }
    }
    //method to cancel alarm
    private void cancelAlarm() {
        if (pendingIntent == null) {
            Intent intent = new Intent(this, Alarm.class);
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        }

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
            Toast.makeText(this, "Alarm canceled", Toast.LENGTH_SHORT).show();
        }
    }
}
