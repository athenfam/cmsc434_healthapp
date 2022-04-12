package com.example.healthapp;

import static com.example.healthapp.R.*;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.RandomAccess;

public class HomeFragment extends Fragment {
    private int calories = 0;
    private int calorieGoal = 2000;
    private double weightGoal;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            this.calories = getArguments().getInt("calories");
            this.calorieGoal = getArguments().getInt("calorieGoal");
            this.weightGoal = getArguments().getDouble("weightGoal");
        }


        return inflater.inflate(layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set current date
        setDate(getView().findViewById(id.date));

        // Set updated calorie progression
        getCalorieGoal();

        //Need to figure out fragment to fragment data sharing. Right now all of the data is assigned to 4 objects based by tab.
        // Also consider how to retain saved data after an app session is finished
    }

    public void setDate (TextView view){
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMMM d yyyy");
        String date = formatter.format(today);
        view.setText(date);
    }

    public void getCalorieGoal(){
        ProgressBar circleCalorie = (ProgressBar) getView().findViewById(R.id.calorie_progress);

        double percent = calories * 100 / calorieGoal;
        circleCalorie.setProgress((int) percent);

        TextView view = getView().findViewById(id.calorieGoal);
        view.setText("You completed "+(int)percent+ "% of your daily calorie goal");

        TextView center = getView().findViewById(id.home_calorie);
        center.setText(calories+"/"+calorieGoal+"cal");

        if(percent>=100){//Highlight green if complete
            center.setTextColor(Color.GREEN);
        }

        ObjectAnimator animation = ObjectAnimator.ofInt(circleCalorie, "progress", 0, (int) percent); // see this max value coming back here, we animate towards that value
        animation.setDuration(1500); // in milliseconds
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();


        TextView quote = getView().findViewById(id.dailyQuote);
        String[] q = getResources().getStringArray(array.dailyQuotes);
        // Randomly picks a quote everytime the home page is selected
        quote.setText("Daily Quote: "+q[(int)(Math.random()*q.length)]);
    }
}
