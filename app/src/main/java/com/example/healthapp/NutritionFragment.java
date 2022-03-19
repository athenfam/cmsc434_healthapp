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

public class NutritionFragment extends Fragment {

    int targetCalories = 200;
    int consumedCalories = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nutrition, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView totalCaloriesConsumedTextView = (TextView) getView().findViewById(R.id.totalCaloriesConsumed);
        totalCaloriesConsumedTextView.setText("Total Calories Consumed: " + consumedCalories);

        Button button = (Button) getView().findViewById(R.id.addData);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(),AddNutritionPopUp.class),1);
                if(getArguments() != null) {
                    consumedCalories += Integer.valueOf(getArguments().getString("caloriesConsumed"));
                    totalCaloriesConsumedTextView.setText("Total Calories Consumed: " + consumedCalories);
                    System.out.println("recieved argument: " + consumedCalories);
                } else {
                    System.out.println("no arg");
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            consumedCalories += data.getIntExtra("caloriesAdded", 0);
            TextView totalCaloriesConsumedTextView = (TextView) getView().findViewById(R.id.totalCaloriesConsumed);
            totalCaloriesConsumedTextView.setText("Total Calories Consumed: " + consumedCalories);
        }
    }
}
