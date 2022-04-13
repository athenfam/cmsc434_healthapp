package com.example.healthapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

public class SettingsFragment extends Fragment {
    String name;
    int birthday;
    int feet;
    int goalCalorie, goalCalorieBurned;
    double inches, currWeight, goalWeight, bmi;
    private long mLastClickTime = 0;
    SettingsFragment(){
        name="John Doe";
        feet=5;
        inches=8;
        currWeight=160;
        goalWeight=145;
        goalCalorie=2500;
        goalCalorieBurned=800;
        birthday=01012000;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Display personal info
        displayValues();

        // Button Listener for "CHANGE PERSONAL INFO"
        Button button = (Button) getView().findViewById(R.id.changeInfo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // mis-clicking prevention, using threshold of 1000 ms
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                startActivityForResult(new Intent(getActivity(),ProfilePopUp.class),1);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // If someone clicks off the popup window, exits here
        if(data == null){
            return;
        }

        // Return entry data after hitting submit
        if(requestCode == 1){
            name = data.getStringExtra("name");
            feet = data.getIntExtra("feet",-1);
            inches = data.getDoubleExtra("inches",0);
            currWeight = data.getDoubleExtra("currWeight",-1);
            goalWeight = data.getDoubleExtra("goalWeight",-1);
            birthday = data.getIntExtra("birthday",-1);
            goalCalorie = data.getIntExtra("goalCalorie", 2000);
            goalCalorieBurned = data.getIntExtra("goalCalorieBurned",500);
            displayValues();
        }
    }

    public void displayValues() {
        TextView nameTextView = (TextView) getView().findViewById(R.id.nameDisplay);
        nameTextView.setText("Name: " + name);

        TextView feetTextView = (TextView) getView().findViewById(R.id.heightDisplay);
        feetTextView.setText("Height: " + feet + " feet " + inches + " inches");

        TextView birthdayTextView = (TextView) getView().findViewById(R.id.birthdayDisplay);
        birthdayTextView.setText("Birthday: " + birthday);

        TextView currWeightTextView = (TextView) getView().findViewById(R.id.currWeightDisplay);
        currWeightTextView.setText("Weight: " + currWeight + " lbs");

        TextView goalWeightTextView = (TextView) getView().findViewById(R.id.goalWeightDisplay);
        goalWeightTextView.setText("Goal Weight: " + goalWeight + " lbs");

        TextView goalCalorieTextView = (TextView) getView().findViewById(R.id.calorieGoalDisplay);
        goalCalorieTextView.setText("Daily Calorie Intake Goal: " + goalCalorie + " cal");

        TextView goalCalorieBurnedTextView = (TextView) getView().findViewById(R.id.calorieBurnedGoalDisplay);
        goalCalorieBurnedTextView.setText("Daily Calorie Burned From Exercise: " + goalCalorieBurned + " cal");

        TextView bmiTextView = (TextView) getView().findViewById(R.id.bmi);
        bmi = (currWeight*0.453592)/Math.pow(0.0254*(feet*12+inches),2);
        bmi = Math.round(bmi*100.0)/100.0;

        if(bmi<18.5){
            bmiTextView.setText("BMI: " + bmi+ " (underweight)");
        } else if (bmi<24.9){
            bmiTextView.setText("BMI: " + bmi+ " (normal weight)");
        } else if (bmi<29.9){
            bmiTextView.setText("BMI: " + bmi+ " (overweight)");
        } else{
            bmiTextView.setText("BMI: " + bmi+ " (obese)");
        }
    }

    public int getCalorieGoal(){
        return goalCalorie;
    }
    public String getName(){return name;}
    public int getCalorieBurnedGoal(){return goalCalorieBurned;}
    public double getCurrWeight(){return currWeight;}
    public double getGoalWeight(){return goalWeight;}
}
