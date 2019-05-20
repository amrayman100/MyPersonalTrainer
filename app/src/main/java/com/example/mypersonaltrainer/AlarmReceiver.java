package com.example.mypersonaltrainer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class AlarmReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        SharedPreferences sp = context.getSharedPreferences("your_prefs",context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putFloat("Cal",0);
        editor.putFloat("Carbs",0);
        editor.putFloat("Protein",0);
        editor.putFloat("Fats",0);
        editor.commit();
    }


}