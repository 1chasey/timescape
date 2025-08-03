package com.example.timescape;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class activity_main extends AppCompatActivity {

    private TimePicker timer;
    private Button button_pick, button_stop;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        timePicker = findViewById(R.id.clock);
        buttonSet = findViewById(R.id.set);
        buttonStop = findViewById(R.id.Stop);

        buttonSet.setOnClickListener(v -> setAlarm());
        buttonStop.setOnClickListener(v -> cancelAlarm());
    }

        public void setAlarm() {
            int timeInSeconds = 10; // You can later get this from EditText input
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            Intent intent = new Intent(this, Alarm.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

            long triggerAtMillis = System.currentTimeMillis() + (timeInSeconds * 1000);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);

            Toast.makeText(this, "Alarm set for " + timeInSeconds + " seconds", Toast.LENGTH_SHORT).show();
        }

        public void stopAlarm(View v) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            Intent intent = new Intent(this, Alarm.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
            alarmManager.cancel(pendingIntent);

            Toast.makeText(this, "Alarm canceled", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View v) {
        Calendar cal=Calendar.getInstance();
        int hour=timer.getHour();
        int minute=timer.getMinute();

        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        if (cal.before(Calendar.getInstance())) {
            cal.add(Calendar.DATE, 1);
        }
    }

    private void alarm_set(long timeInMillis) {
        AlarmManager alarmManage = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(this, Alarm.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        long millisTrigger=System.currentTimeMillis() + (setTimer * 1000);
        alarmManage.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(this, "Your alarm has been set", Toast.LENGTH_LONG).show();
    }



}
