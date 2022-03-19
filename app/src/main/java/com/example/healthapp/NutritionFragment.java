package com.example.healthapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.HashMap;

public class NutritionFragment extends Fragment {

    int targetCalories = 200;
    int consumedCalories = 0, consumedFat = 0, consumedCholesterol = 0, consumedSodium = 0, consumedCarbohydrates=0, consumedProtein=0;
    HashMap<String, Integer> nutritionValues;

    public NutritionFragment(){
        nutritionValues = new HashMap<>();
        nutritionValues.put("calories",0);
        nutritionValues.put("fat",0);
        nutritionValues.put("cholesterol",0);
        nutritionValues.put("sodium",0);
        nutritionValues.put("carbs",0);
        nutritionValues.put("protein",0);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nutrition, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set nutrient values
        displayValues();

        Button button = (Button) getView().findViewById(R.id.addData);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(),AddNutritionPopUp.class),1);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            nutritionValues.put("calories", nutritionValues.get("calories")+data.getIntExtra("caloriesAdded", 0));
            nutritionValues.put("fat", nutritionValues.get("fat")+data.getIntExtra("fatAdded", 0));
            nutritionValues.put("cholesterol", nutritionValues.get("cholesterol")+data.getIntExtra("cholesterolAdded", 0));
            nutritionValues.put("sodium", nutritionValues.get("sodium")+data.getIntExtra("sodiumAdded", 0));
            nutritionValues.put("carbs", nutritionValues.get("carbs")+data.getIntExtra("carbsAdded", 0));
            nutritionValues.put("protein", nutritionValues.get("protein")+data.getIntExtra("proteinAdded", 0));

            displayValues();
        }
    }

    // Display current nutrition values
    public void displayValues(){
        TextView totalCaloriesConsumedTextView = (TextView) getView().findViewById(R.id.totalCaloriesConsumed);
        totalCaloriesConsumedTextView.setText("Total Calories Consumed: " + nutritionValues.get("calories"));

        TextView totalSodiumConsumedTextView = (TextView) getView().findViewById(R.id.totalSodiumConsumed);
        totalSodiumConsumedTextView.setText("Total Sodium Consumed: " + nutritionValues.get("sodium"));

        TextView totalFatConsumedTextView = (TextView) getView().findViewById(R.id.totalFatConsumed);
        totalFatConsumedTextView.setText("Total Fat Consumed: " + nutritionValues.get("fat"));

        TextView totalProteinConsumedTextView = (TextView) getView().findViewById(R.id.totalProteinConsumed);
        totalProteinConsumedTextView.setText("Total Protein Consumed: " + nutritionValues.get("protein"));

        TextView totalCarbsConsumedTextView = (TextView) getView().findViewById(R.id.totalCarbohydratesConsumed);
        totalCarbsConsumedTextView.setText("Total Carbohydrates Consumed: " + nutritionValues.get("carbs"));

        TextView totalCholesterolConsumedTextView = (TextView) getView().findViewById(R.id.totalCholesterolConsumed);
        totalCholesterolConsumedTextView.setText("Total Cholesterol Consumed: " + nutritionValues.get("cholesterol"));
    }
}
