package com.example.healthapp;

import static com.example.healthapp.R.*;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.RandomAccess;

public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
        TextView view = getView().findViewById(id.calorieGoal);
        view.setText("You have eaten x/2000 calories today");


        TextView quote = getView().findViewById(id.dailyQuote);
        String[] q = getResources().getStringArray(array.dailyQuotes);
        // Randomly picks a quote everytime the home page is selected
        quote.setText("Daily Quote: "+q[(int)(Math.random()*q.length)]);
    }
}
