package com.example.healthapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;


public class AddFitnessPopUp extends Activity {
    int caloriesBurned;
    String exerciseName, exerciseComment;
    EditText exerciseNameInput, caloriesBurnedInput, exerciseCommentInput;
    Button submitButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        

        setContentView(R.layout.add_exercise);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        // change title of popup
        setTitle("Add Exercise Data");
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*0.8), (int)(height*0.8));

        exerciseNameInput = (EditText) findViewById(R.id.exerciseName);
        caloriesBurnedInput = (EditText) findViewById(R.id.caloriesBurned);
        exerciseCommentInput = (EditText) findViewById(R.id.exercise_comments);

        submitButton = (Button) findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                // maybe add clear and cancel button?
                if(exerciseNameInput.getText().toString().equals("")){
                    exerciseNameInput.setError("Please enter Exercise");
                    exerciseNameInput.setHintTextColor(getResources().getColor(R.color.red));
                    return;
                }
                if(caloriesBurnedInput.getText().toString().equals("")){
                    caloriesBurnedInput.setError("Please Enter Calories Burned");
                    caloriesBurnedInput.setHintTextColor(getResources().getColor(R.color.red));
                    return;
                }
                exerciseName = exerciseNameInput.getText().toString();
                caloriesBurned = Integer.valueOf(caloriesBurnedInput.getText().toString());
                exerciseComment = exerciseCommentInput.getText().toString();

                Toast.makeText(getApplicationContext(), "Burned " + caloriesBurned + " Calories", Toast.LENGTH_LONG).show();
                Intent resultIntent = new Intent();


                resultIntent.putExtra("caloriesBurned", caloriesBurned);
                resultIntent.putExtra("exerciseName", exerciseName);
                resultIntent.putExtra("comment",exerciseComment);

                setResult(RESULT_OK,resultIntent);


                finish();
            }
        });
    }
}
