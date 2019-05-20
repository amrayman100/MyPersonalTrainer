package com.example.mypersonaltrainer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddWorkout extends AppCompatActivity {
    EditText name;
    EditText routine;
    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";
    public static final String EXTRA_REPLY2 = "com.example.android.wordlistsql.REPLY2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout);
        name = findViewById(R.id.workout_name);
        routine = findViewById(R.id.routine);


        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(name.getText())||TextUtils.isEmpty(routine.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String word = name.getText().toString();
                    String r = routine.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY, word);
                    replyIntent.putExtra(EXTRA_REPLY2, r);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
