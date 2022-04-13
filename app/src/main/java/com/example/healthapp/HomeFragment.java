package com.example.healthapp;

import static com.example.healthapp.R.*;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.RandomAccess;

public class HomeFragment extends Fragment {
    private int calories = 0;
    private int calorieGoal = 2500;
    private double weightGoal = 145;
    private String name = "John Doe";
    private int burnedCalories = 0;
    private int calorieBurnedGoal = 800;
    private double currWeight=160;
    private boolean today = true;
    private boolean metCalorieGoal = false;
    private boolean metCalorieBurnedGoal = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            this.calories = getArguments().getInt("calories");
            this.calorieGoal = getArguments().getInt("calorieGoal");
            this.weightGoal = getArguments().getDouble("goalWeight");
            this.name = getArguments().getString("name");
            this.burnedCalories = getArguments().getInt("calorieBurned");
            this.calorieBurnedGoal = getArguments().getInt("calorieBurnedGoal");
            this.currWeight=getArguments().getDouble("currWeight");
        }


        return inflater.inflate(layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set current date
        setDate(getView().findViewById(id.date));

        TextView greeting = (TextView) getView().findViewById(id.greeting);
        greeting.setText("Hello "+name+"!");

        // Set updated calorie progression
        getCalorieGoal();

        //Update Weight Goal progression
        getWeightGoal();

        //Need to figure out fragment to fragment data sharing. Right now all of the data is assigned to 4 objects based by tab.
        // Also consider how to retain saved data after an app session is finished


        //Click left
        ImageView left = (ImageView) getView().findViewById(R.id.left);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), // <- Line changed
                        "Previous day's data appears",
                        Toast.LENGTH_SHORT).show();
            }
        });

        //Click right
        ImageView right = (ImageView) getView().findViewById(R.id.right);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(today) {
                    Toast.makeText(v.getContext(),
                            "No data for the next day",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setDate (TextView view){
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMMM d yyyy");
        String date = formatter.format(today);
        view.setText(date);
    }

    public void getCalorieGoal(){
        ProgressBar circleCalorie = (ProgressBar) getView().findViewById(R.id.calorie_progress);
        ProgressBar circleCalorieBurned = (ProgressBar) getView().findViewById(id.calorie_burned_progress);


        double percent = calories * 100 / calorieGoal;
        circleCalorie.setProgress((int) percent);

        // Calorie consumed progress
        TextView center = getView().findViewById(id.home_calorie);
        center.setText(calories+"/"+calorieGoal+"cal consumed");
        if(percent>=100){//Highlight green if complete
            center.setTextColor(Color.GREEN);
            metCalorieGoal = true;
        }
        ObjectAnimator animation = ObjectAnimator.ofInt(circleCalorie, "progress", 0, (int) percent); // see this max value coming back here, we animate towards that value
        animation.setDuration(1500); // in milliseconds
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();

        // Calorie burned progress
        percent = burnedCalories*100/calorieBurnedGoal;
        TextView center2 = getView().findViewById(id.home_calorie_burned);
        center2.setText(burnedCalories+"/"+calorieBurnedGoal+"cal burned");
        if(percent>=100){//Highlight green if complete
            center2.setTextColor(Color.GREEN);
            metCalorieBurnedGoal = true;
        }
        ObjectAnimator animation2 = ObjectAnimator.ofInt(circleCalorieBurned, "progress", 0, (int) percent); // see this max value coming back here, we animate towards that value
        animation2.setDuration(1500); // in milliseconds
        animation2.setInterpolator(new DecelerateInterpolator());
        animation2.start();


        if(metCalorieGoal && metCalorieBurnedGoal){
            ImageView checkmark = (ImageView) getView().findViewById(id.checkmark);
            checkmark.setVisibility(View.VISIBLE);
        }


        TextView quote = getView().findViewById(id.dailyQuote);
        String[] q = getResources().getStringArray(array.dailyQuotes);
        // Randomly picks a quote everytime the home page is selected
        quote.setText("Daily Quote: "+q[(int)(Math.random()*q.length)]);
    }

    public void getWeightGoal(){
        TextView view = getView().findViewById(id.weightGoal);
        view.setText("You are "+Math.abs(currWeight-weightGoal)+"lbs from your weight goal of "+weightGoal+"lbs!");
    }
}
