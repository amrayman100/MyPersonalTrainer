package com.example.mypersonaltrainer;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class WorkoutRepository {

    private WorkoutDao workoutDao;
    private LiveData<List<Workout>> workouts;

    public WorkoutRepository(Application application){
        WorkoutDatabase workoutDatabase = WorkoutDatabase.getDatabase(application);
        workoutDao = workoutDatabase.workDao();
        workouts = workoutDao.getWorkouts();
    }

    public LiveData<List<Workout>> getAllWorkouts() {
        return workouts;
    }

    public LiveData<Workout> getWorkout(int id){
        return workoutDao.getWorkout(id);
    }


    public void insert (Workout w) {
        new insertAsyncTask(workoutDao).execute(w);
    }

    private static class insertAsyncTask extends AsyncTask<Workout, Void, Void> {

        private WorkoutDao mAsyncTaskDao;

        insertAsyncTask(WorkoutDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Workout... params) {

            mAsyncTaskDao.insert(params[0]);
            return null;
        }

    }
}
