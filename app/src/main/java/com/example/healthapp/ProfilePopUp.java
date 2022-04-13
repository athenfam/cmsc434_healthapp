package com.example.healthapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;


public class ProfilePopUp extends Activity {
    String name;
    int birthday, feet, goalCalorie,goalCalorieBurned;
    double inches, currWeight, goalWeight;
    EditText nameInput, birthdayInput, feetInput, inchesInput, currWeightInput, goalWeightInput, goalCalorieInput, goalCalorieBurnedInput;
    Button submitButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.edit_personal_info);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        // change tile of popup
        setTitle("Change Personal Info");
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*0.8), (int)(height*0.8));

        nameInput = (EditText) findViewById(R.id.name);
        birthdayInput = (EditText) findViewById(R.id.birthday);
        feetInput = (EditText) findViewById(R.id.feet);
        inchesInput = (EditText) findViewById(R.id.inches);
        currWeightInput = (EditText) findViewById(R.id.currWeight);
        goalWeightInput = (EditText) findViewById(R.id.goalWeight);
        goalCalorieInput=(EditText) findViewById(R.id.goalCalorie);
        goalCalorieBurnedInput=(EditText) findViewById(R.id.goalCalorieBurned);

        submitButton = (Button) findViewById(R.id.save);
        submitButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                // maybe add clear and cancel button?
                // Code to have required entries

                if(nameInput.getText().toString().equals("")){
                    nameInput.setError("Please enter you name");
                    nameInput.setHintTextColor(getResources().getColor(R.color.red));
                    return;
                }
                if(birthdayInput.getText().toString().equals("")){
                    birthdayInput.setError("Please enter you birthday");
                    birthdayInput.setHintTextColor(getResources().getColor(R.color.red));
                    return;
                }
                if(feetInput.getText().toString().equals("")){
                    feetInput.setError("Please enter you height in feet");
                    feetInput.setHintTextColor(getResources().getColor(R.color.red));
                    return;
                }
                if(currWeightInput.getText().toString().equals("")){
                    currWeightInput.setError("Please enter you current weight");
                    currWeightInput.setHintTextColor(getResources().getColor(R.color.red));
                    return;
                }




                // Required entries
                name = nameInput.getText().toString();
                birthday=Integer.valueOf(birthdayInput.getText().toString());
                feet=Integer.valueOf(feetInput.getText().toString());
                currWeight=Double.valueOf(currWeightInput.getText().toString());

                // Optional entries
                if(inchesInput.getText().toString().equals("")){
                    inches = 0;
                } else {
                    inches=Double.valueOf(inchesInput.getText().toString());
                }
                if(goalWeightInput.getText().toString().equals("")){
                    // Auto generated weight
                    // If men, ideal body weight = 22*height^2, height in meters
                    goalWeight = 2.20462*22*Math.pow(0.0254*(12*feet + inches),2);
                    // Women, ideal body weight = 22*(height-10cm)^2, height in meters
                    // goalWeight = 2.20462*22*Math.pow(0.0254*(12*feet + inches) - 10,2);

                    goalWeight = Math.floor(goalWeight * 100) / 100;
                } else {
                    goalWeight=Double.valueOf(goalWeightInput.getText().toString());
                }

                if(goalCalorieInput.getText().toString().equals("")){
                    goalCalorie = 2000;
                } else {
                    goalCalorie=Integer.valueOf(goalCalorieInput.getText().toString());
                }

                if(goalCalorieBurnedInput.getText().toString().equals("")){
                    goalCalorie = 500;
                } else {
                    goalCalorieBurned=Integer.valueOf(goalCalorieBurnedInput.getText().toString());
                }

                Toast.makeText(getApplicationContext(), "Profile Changes Saved", Toast.LENGTH_LONG).show();
                Intent resultIntent = new Intent();

                resultIntent.putExtra("name", name);
                resultIntent.putExtra("birthday", birthday);
                resultIntent.putExtra("feet", feet);
                resultIntent.putExtra("currWeight", currWeight);
                resultIntent.putExtra("goalWeight", goalWeight);
                resultIntent.putExtra("goalCalorie", goalCalorie);
                resultIntent.putExtra("goalCalorieBurned",goalCalorieBurned);
                setResult(RESULT_OK,resultIntent);

                finish();
            }
        });
    }
}
