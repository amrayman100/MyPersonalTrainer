package com.example.mypersonaltrainer;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;


@Dao
public interface WorkoutDao {

    @Insert
    void insert(Workout workout);
    @Update
    void update(Workout workout);
    @Delete
    void delete(Workout workout);
    @Query("SELECT * FROM workout_table")
    LiveData<List<Workout>> getWorkouts();
    @Query("SELECT * FROM workout_table WHERE id=:id")
    LiveData<Workout> getWorkout(int id);

    @Query("DELETE FROM workout_table")
    void deleteAll();

    @Query("SELECT COUNT(*) FROM  workout_table")
    int count();
}
