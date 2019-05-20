package com.example.mypersonaltrainer;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.util.concurrent.Executors;

@Database(entities = {Workout.class}, version = 1)
public abstract class WorkoutDatabase extends RoomDatabase {

    public abstract WorkoutDao workDao();
    public static final String PREF_NAME = "Database";
    public static final String EXTRA_PREF_FIRST_LAUNCH = "FirstLaunch_";

    private static volatile WorkoutDatabase INSTANCE;

    static WorkoutDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WorkoutDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WorkoutDatabase.class, "workout_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();


                }
            }
        }
        return INSTANCE;
    }







    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final WorkoutDao mDao;

        PopulateDbAsync(WorkoutDatabase db) {
            mDao = db.workDao();


        }

        @Override
        protected Void doInBackground(final Void... params) {


            if(mDao.count()==0){
                String r1 = "Bench Press 4x10 \nIncline Dumbbell press 3x10\nCable Chest Fly 3x10\n Dips Superset Pushups 4x10 ";
                String r2 = "DeadLifts 4x10 \nBarbell bent over row 3x10\nWide Grip Pullups 3x10\nWide grip lat pulldown 4x10\nSeated Rows 3x10";
                Workout word = new Workout("Chest Workout",r1);
                Workout back = new Workout("Back Workout",r2);
                mDao.insert(word);

                mDao.insert(back);
            }

            return null;
        }
    }
}