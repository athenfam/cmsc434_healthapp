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
    String name, gender;
    int age, feet, goalCalorie,goalCalorieBurned, inches;
    double currWeight, goalWeight;
    EditText nameInput, genderInput, ageInput, feetInput, inchesInput, currWeightInput, goalWeightInput, goalCalorieInput, goalCalorieBurnedInput;
    Button submitButton;


    public int getGoalCalories(int feet, double inches, double currWeight, int age){
        double height = ((feet * 12) + (inches) ) * 2.54;
        double weight = (0.453 * currWeight);
        return (int) ((10 * weight) + (6.25 * height) - (5 * age) + 5);
    }
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

        genderInput = (EditText) findViewById(R.id.gender);
        nameInput = (EditText) findViewById(R.id.name);
        ageInput = (EditText) findViewById(R.id.age);
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
                if(ageInput.getText().toString().equals("")){
                    ageInput.setError("Please enter you age");
                    ageInput.setHintTextColor(getResources().getColor(R.color.red));
                    return;
                }
                if(genderInput.getText().toString().equals("")){
                    genderInput.setError("Please enter your gender");
                    genderInput.setHintTextColor(getResources().getColor(R.color.red));
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
                gender= genderInput.getText().toString();
                age=Integer.valueOf(ageInput.getText().toString());
                feet=Integer.valueOf(feetInput.getText().toString());
                inches = Integer.valueOf(inchesInput.getText().toString());
                currWeight=Double.valueOf(currWeightInput.getText().toString());

                // Optional entries
                if(inchesInput.getText().toString().equals("")){
                    inches = 0;
                } else {
                    inches=Integer.valueOf(inchesInput.getText().toString());
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
                    goalCalorie = 200;
                } else {
                    goalCalorie=Integer.valueOf(goalCalorieInput.getText().toString());
                }

                if(goalCalorieBurnedInput.getText().toString().equals("")){
                    goalCalorie = getGoalCalories(feet, inches, currWeight, age);
                    goalCalorieBurned=500;
                } else {
                    goalCalorieBurned=Integer.valueOf(goalCalorieBurnedInput.getText().toString());
                }

                Toast.makeText(getApplicationContext(), "Profile Changes Saved", Toast.LENGTH_LONG).show();
                Intent resultIntent = new Intent();

                resultIntent.putExtra("name", name);
                resultIntent.putExtra("gender",gender);
                resultIntent.putExtra("age", age);
                resultIntent.putExtra("feet", feet);
                resultIntent.putExtra("inches", inches);
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
