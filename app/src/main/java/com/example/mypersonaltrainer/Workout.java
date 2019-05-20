package com.example.mypersonaltrainer;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.arch.persistence.room.ColumnInfo;

@Entity(tableName = "workout_table")
public class Workout {
    
    @NonNull
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "Name")
    private String Name;
    @ColumnInfo(name = "Routine")
    private String Routine;

    public Workout(String Name , String Routine){
        this.Name = Name;
        this.Routine = Routine;
    }

    public String getName(){return this.Name;}
    public String getRoutine(){return this.Routine;}

}
