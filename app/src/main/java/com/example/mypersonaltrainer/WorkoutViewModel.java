package com.example.mypersonaltrainer;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;


public class WorkoutViewModel extends AndroidViewModel {



    private WorkoutRepository mRepository;

    private LiveData<List<Workout>> workouts;

    public WorkoutViewModel (Application application) {
        super(application);
        mRepository = new WorkoutRepository(application);
        workouts = mRepository.getAllWorkouts();
    }

    LiveData<List<Workout>> getAllWords() { return workouts; }

    public void insert(Workout word) { mRepository.insert(word); }
}