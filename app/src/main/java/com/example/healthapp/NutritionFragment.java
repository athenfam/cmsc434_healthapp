package com.example.healthapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class NutritionFragment extends Fragment {

    HashMap<String, Integer> totalValues;
    List<FoodEntry> entries = new ArrayList<>();
    private long mLastClickTime = 0;
    // Assign initial total values to 0 on creation
    public NutritionFragment(){
        totalValues = new HashMap<>();
        totalValues.put("calories",0);
        totalValues.put("fat",0);
        totalValues.put("cholesterol",0);
        totalValues.put("sodium",0);
        totalValues.put("carbs",0);
        totalValues.put("protein",0);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nutrition, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setDate(getView().findViewById(R.id.date2));

        // Display total nutrition values
        displayValues();

        // Display scrollable meal entries
        // See if there is a way to make scrollable text changes permanent between screen transitions,
        // more efficient than repopulating each time
        // maybe something to do with savedinstancestate in MainActivity line 21?
        int counter = 1;
        for(FoodEntry e:entries){
            createScrollEntry(e, counter++);
        }

        // Button Listener for "ADD NUTRITIONAL DATA"
        Button button = (Button) getView().findViewById(R.id.addFoodData);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // mis-clicking prevention, using threshold of 1000 ms
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                startActivityForResult(new Intent(getActivity(),AddNutritionPopUp.class),1);
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Return entry data after hitting submit
        if(requestCode == 1){
            // If someone clicks off the popup window, exits here
            if(data == null){
                return;
            }

            // Create new FoodEntry
            FoodEntry entry = new FoodEntry(data.getStringExtra("foodName"), data.getIntExtra("caloriesAdded",0));
            entry.fat = data.getIntExtra("fatAdded", 0);
            entry.cholesterol = data.getIntExtra("cholesterolAdded", 0);
            entry.sodium = data.getIntExtra("sodiumAdded", 0);
            entry.carbs = data.getIntExtra("carbsAdded", 0);
            entry.protein = data.getIntExtra("proteinAdded", 0);
            entries.add(entry);


            // Add entry to scrollable element
            createScrollEntry(entry, entries.size());

            // Update total values
            totalValues.put("calories", totalValues.get("calories")+entry.calories);
            totalValues.put("fat", totalValues.get("fat")+entry.fat);
            totalValues.put("cholesterol", totalValues.get("cholesterol")+ entry.cholesterol);
            totalValues.put("sodium", totalValues.get("sodium")+entry.sodium);
            totalValues.put("carbs", totalValues.get("carbs")+entry.carbs);
            totalValues.put("protein", totalValues.get("protein")+entry.protein);
            displayValues();
        }
    }

    private void createScrollEntry(FoodEntry entry, int entryNum){
        LinearLayout scrollable = getView().findViewById(R.id.nutritionScrollable);
        TextView entryScrollable = new TextView(getContext());
        StringBuilder text = new StringBuilder("Meal #"+entryNum+": "+entry.foodName+"\n\t"+"Calories: "+entry.calories);

        if(entry.fat != 0)
            text.append("\n\tFat: " + entry.fat + " grams");
        if(entry.cholesterol != 0)
            text.append("\n\tCholesterol: "+entry.cholesterol+" grams");
        if(entry.sodium!=0)
            text.append("\n\tSodium: "+entry.sodium+"mg");
        if(entry.carbs!=0)
            text.append("\n\tCarbohydrates: "+entry.carbs+" grams");
        if(entry.protein!=0)
            text.append("\n\tProtein: "+entry.protein+" grams");

        entryScrollable.setText(text);
        entryScrollable.setTextColor(Color.BLACK);
        entryScrollable.setPadding(10,10,10,10);
        //scrollable.addView(entryScrollable);

        CardView cardScrollable = new CardView(getContext());
        cardScrollable.setCardBackgroundColor(Color.parseColor("#d2042d"));
        cardScrollable.setRadius(30);
        cardScrollable.addView(entryScrollable);
        scrollable.addView(cardScrollable);
    }

    // Display current nutrition values
    public void displayValues(){
        TextView totalCaloriesConsumedTextView = (TextView) getView().findViewById(R.id.totalCaloriesConsumed);
        totalCaloriesConsumedTextView.setText("Total Calories Consumed: " + totalValues.get("calories"));

        TextView totalSodiumConsumedTextView = (TextView) getView().findViewById(R.id.totalSodiumConsumed);
        totalSodiumConsumedTextView.setText("Total Sodium Consumed: " + totalValues.get("sodium") +" mg");

        TextView totalFatConsumedTextView = (TextView) getView().findViewById(R.id.totalFatConsumed);
        totalFatConsumedTextView.setText("Total Fat Consumed: " + totalValues.get("fat")+" grams");

        TextView totalProteinConsumedTextView = (TextView) getView().findViewById(R.id.totalProteinConsumed);
        totalProteinConsumedTextView.setText("Total Protein Consumed: " + totalValues.get("protein")+" grams");

        TextView totalCarbsConsumedTextView = (TextView) getView().findViewById(R.id.totalCarbohydratesConsumed);
        totalCarbsConsumedTextView.setText("Total Carbohydrates Consumed: " + totalValues.get("carbs")+" grams");

        TextView totalCholesterolConsumedTextView = (TextView) getView().findViewById(R.id.totalCholesterolConsumed);
        totalCholesterolConsumedTextView.setText("Total Cholesterol Consumed: " + totalValues.get("cholesterol")+" mg");
    }

    public void setDate (TextView view){
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMMM d yyyy");
        String date = formatter.format(today);
        view.setText(date);

    }
    public int getNutritionValue(String value){
        return totalValues.get(value);
    }
}
