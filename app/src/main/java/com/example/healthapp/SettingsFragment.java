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
    int age;
    int feet;
    int goalCalorie, goalCalorieBurned;
    String gender;
    double inches, currWeight, goalWeight, bmi;
    private long mLastClickTime = 0;
    Boolean isSetup = false;
    SettingsFragment(){

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
            inches = data.getIntExtra("inches",0);
            currWeight = data.getDoubleExtra("currWeight",-1);
            goalWeight = data.getDoubleExtra("goalWeight",-1);
            age = data.getIntExtra("age",-1);
            goalCalorie = data.getIntExtra("goalCalorie", 100);
            goalCalorieBurned = data.getIntExtra("goalCalorieBurned",155);
            gender = data.getStringExtra("gender");
            isSetup = true;
            displayValues();
        }
    }


    public void displayValues() {
        Button button = (Button) getView().findViewById(R.id.changeInfo);
        if (isSetup){
            button.setText("CHANGE PERSONAL INFO");
        }else{
            button.setText("SETUP");
        }
        TextView nameTextView = (TextView) getView().findViewById(R.id.nameDisplay);
        TextView feetTextView = (TextView) getView().findViewById(R.id.heightDisplay);
        TextView genderTextView = (TextView) getView().findViewById(R.id.genderDisplay);
        TextView ageTextView = (TextView) getView().findViewById(R.id.ageDisplay);
        TextView currWeightTextView = (TextView) getView().findViewById(R.id.currWeightDisplay);
        TextView goalWeightTextView = (TextView) getView().findViewById(R.id.goalWeightDisplay);
        TextView goalCalorieTextView = (TextView) getView().findViewById(R.id.calorieGoalDisplay);
        TextView goalCalorieBurnedTextView = (TextView) getView().findViewById(R.id.calorieBurnedGoalDisplay);
        TextView bmiTextView = (TextView) getView().findViewById(R.id.bmi);
        TextView initialSettings = (TextView) getView().findViewById(R.id.initialSetup);
        bmi = (currWeight*0.453592)/Math.pow(0.0254*(feet*12+inches),2);
        bmi = Math.round(bmi*100.0)/100.0;

        if (isSetup){
            nameTextView.setText("Name: " + name);
            feetTextView.setText("Height: " + feet + " feet " + inches + " inches");
            genderTextView.setText("Gender: "+gender);
            ageTextView.setText("Age: " + age);
            currWeightTextView.setText("Weight: " + currWeight + " lbs");
            goalWeightTextView.setText("Goal Weight: " + goalWeight + " lbs");
            goalCalorieTextView.setText("Daily Calorie Intake Goal: " + goalCalorie + " cal");
            goalCalorieBurnedTextView.setText("Daily Calorie Burned From Exercise: " + goalCalorieBurned + " cal");
            if(bmi<18.5){
                bmiTextView.setText("BMI: " + bmi+ " (underweight)");
            } else if (bmi<24.9){
                bmiTextView.setText("BMI: " + bmi+ " (normal weight)");
            } else if (bmi<29.9){
                bmiTextView.setText("BMI: " + bmi+ " (overweight)");
            } else{
                bmiTextView.setText("BMI: " + bmi+ " (obese)");
            }
            nameTextView.setVisibility(View.VISIBLE);
            feetTextView.setVisibility(View.VISIBLE);
            genderTextView.setVisibility(View.VISIBLE);
            ageTextView.setVisibility(View.VISIBLE);
            currWeightTextView.setVisibility(View.VISIBLE);
            goalWeightTextView.setVisibility(View.VISIBLE);
            goalCalorieTextView.setVisibility(View.VISIBLE);
            goalCalorieBurnedTextView.setVisibility(View.VISIBLE);
            bmiTextView.setVisibility(View.VISIBLE);
            initialSettings.setVisibility(View.GONE);
        }else{
            nameTextView.setVisibility(View.GONE);
            feetTextView.setVisibility(View.GONE);
            genderTextView.setVisibility(View.GONE);
            ageTextView.setVisibility(View.GONE);
            currWeightTextView.setVisibility(View.GONE);
            goalWeightTextView.setVisibility(View.GONE);
            goalCalorieTextView.setVisibility(View.GONE);
            goalCalorieBurnedTextView.setVisibility(View.GONE);
            bmiTextView.setVisibility(View.GONE);
            initialSettings.setVisibility(View.VISIBLE);
        }
    }

    public int getCalorieGoal(){
        return goalCalorie;
    }
    public String getName(){return name;}
    public int getCalorieBurnedGoal(){return goalCalorieBurned;}
    public double getCurrWeight(){return currWeight;}
    public double getGoalWeight(){return goalWeight;}
    public boolean getIsSetupDone(){return isSetup;}
}
