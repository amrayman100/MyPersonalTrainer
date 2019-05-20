package com.example.mypersonaltrainer;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class WorkoutsList extends AppCompatActivity {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private WorkoutViewModel workView;

    Button button;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts_list);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final WorkoutAdapter adapter = new WorkoutAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        workView = ViewModelProviders.of(this).get(WorkoutViewModel.class);
        workView.getAllWords().observe(this, new Observer<List<Workout>>() {
            @Override
            public void onChanged(@Nullable final List<Workout> words) {

                adapter.setWords(words);
            }
        });
        button = (Button) findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(WorkoutsList.this,AddWorkout.class);

                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);



            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG", "insid");
        Toast.makeText(
                getApplicationContext(),
                "inside",
                Toast.LENGTH_LONG).show();

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Workout word = new Workout(data.getStringExtra(AddWorkout.EXTRA_REPLY),data.getStringExtra(AddWorkout.EXTRA_REPLY2));
            Toast.makeText(
                    getApplicationContext(),
                    "Added",
                    Toast.LENGTH_LONG).show();
            workView.insert(word);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "Please enter the details",
                    Toast.LENGTH_LONG).show();
        }
    }
}
