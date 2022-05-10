package com.example.healthapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class FitnessFragment extends Fragment {
    HashMap<String, Integer> totalValues;
    List<ExerciseEntry> entries = new ArrayList<>();
    private long mLastClickTime = 0;
    // Assign initial total values to 0 on creation
    public FitnessFragment(){
        totalValues = new HashMap<>();
        totalValues.put("calories",0);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fitness, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Display total nutrition values
        displayValues();

        setDate(getView().findViewById(R.id.date1));

        // Display scrollable meal entries
        // See if there is a way to make scrollable text changes permanent between screen transitions,
        // more efficient than repopulating each time
        // maybe something to do with savedinstancestate in MainActivity line 21?

        int counter = 1;
        for(ExerciseEntry e:entries){
            createScrollEntry(e, counter++);
        }



        // Button Listener for "ADD EXERCISE DATA"
        Button button = (Button) getView().findViewById(R.id.addExerciseData);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // mis-clicking prevention, using threshold of 1000 ms
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                startActivityForResult(new Intent(getActivity(), AddFitnessPopUp.class), 1);
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
            // Create new ExerciseEntry
            ExerciseEntry entry = new ExerciseEntry(data.getStringExtra("exerciseName"), data.getIntExtra("caloriesBurned",0), data.getStringExtra("comment"));
            entries.add(entry);

            // Add entry to scrollable element
            createScrollEntry(entry, entries.size());

            // Update total values
            totalValues.put("calories", totalValues.get("calories")+entry.calories);
            displayValues();
        }
    }

    private void createScrollEntry(ExerciseEntry entry, int entryNum){
        LinearLayout scrollable = getView().findViewById(R.id.fitnessScrollable);
        TextView entryScrollable = new TextView(getContext());
        StringBuilder text = new StringBuilder("Exercise #"+entryNum+": "+entry.exerciseName+"\n\t"+"Calories: "+entry.calories);
        if(!entry.comments.equals("")){
            text.append("\n\tExercise Details:" + entry.comments);
        }

        entryScrollable.setText(text);
        entryScrollable.setPadding(10,10,10,10);
        entryScrollable.setTextColor(Color.BLACK);

        //scrollable.addView(entryScrollable);

        CardView cardScrollable = new CardView(getContext());
        cardScrollable.setCardBackgroundColor(Color.parseColor("#4169e1"));
        cardScrollable.setRadius(30);
        cardScrollable.addView(entryScrollable);
        scrollable.addView(cardScrollable);
    }

    public void setDate (TextView view){
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMMM d yyyy");
        String date = formatter.format(today);
        view.setText(date);

    }

    // Display current nutrition values
    public void displayValues() {
        TextView totalCaloriesBurnedTextView = (TextView) getView().findViewById(R.id.totalCaloriesBurned);
        totalCaloriesBurnedTextView.setText("Total Calories Burned: " + totalValues.get("calories"));
    }

    public int getCaloriesBurned(){
        return totalValues.get("calories");
    }
}
