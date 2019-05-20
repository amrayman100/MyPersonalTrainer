package com.example.mypersonaltrainer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    public String x;
    public SharedPreferences sp;

    Button button;
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences("your_prefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        /*editor.putFloat("Cal",0);
        editor.putFloat("Carbs",0);
        editor.putFloat("Protein",0);
        editor.putFloat("Fats",0);
        editor.commit();*/
        sp.getFloat("Cal",0);
        sp.getFloat("Carbs",0);
        sp.getFloat("Protein",0);
        sp.getFloat("Fats",0);

        button = (Button) findViewById(R.id.btn);
        button2 = (Button) findViewById(R.id.btn2);


        /*AlarmManager alarmMgr = (AlarmManager)MainActivity.this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);


// Set the alarm to start at approximately 00:00 h(24h format).
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY ,00);


//repeteat alarm every 24hours
        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, alarmIntent);*/
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 00); // For 1 PM or 2 PM
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        PendingIntent pi = PendingIntent.getService(this, 0,
                new Intent(this,  AlarmReceiver.class),PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pi);




        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,WorkoutsList.class);
               startActivity(intent);


            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, NutritionActivity.class);
                startActivity(intent);


            }
        });



    }



    }
