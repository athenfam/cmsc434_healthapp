package com.example.healthapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;


public class AddNutritionPopUp extends Activity {
    int caloriesConsumed, fatConsumed,cholesterolConsumed,sodiumConsumed,carbsConsumed, proteinConsumed;
    EditText caloriesConsumedInput, fatConsumedInput, cholesterolConsumedInput, sodiumConsumedInput, carbsConsumedInput, proteinConsumedInput;
    Button submitButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_nutrition);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*0.8), (int)(height*0.8));

        caloriesConsumedInput = (EditText) findViewById(R.id.caloriesConsumed);
        fatConsumedInput = (EditText) findViewById(R.id.fatConsumed);
        cholesterolConsumedInput = (EditText) findViewById(R.id.cholesterolConsumed);
        sodiumConsumedInput = (EditText) findViewById(R.id.sodiumConsumed);
        carbsConsumedInput = (EditText) findViewById(R.id.carbsConsumed);
        proteinConsumedInput = (EditText) findViewById(R.id.proteinConsumed);

        submitButton = (Button) findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(caloriesConsumedInput.getText().toString().equals("")){
                    caloriesConsumedInput.setError("Please Enter Valid Calories!");
                    return;
                }
                caloriesConsumed = Integer.valueOf(caloriesConsumedInput.getText().toString());
                fatConsumed = fatConsumedInput==null || fatConsumedInput.getText().toString().equals("")? 0:Integer.valueOf(fatConsumedInput.getText().toString());
                cholesterolConsumed = cholesterolConsumedInput==null || cholesterolConsumedInput.getText().toString().equals("")? 0:Integer.valueOf(cholesterolConsumedInput.getText().toString());
                sodiumConsumed = sodiumConsumedInput==null || sodiumConsumedInput.getText().toString().equals("")? 0:Integer.valueOf(sodiumConsumedInput.getText().toString());
                carbsConsumed = carbsConsumedInput==null || carbsConsumedInput.getText().toString().equals("")? 0:Integer.valueOf(carbsConsumedInput.getText().toString());
                proteinConsumed = proteinConsumedInput==null || proteinConsumedInput.getText().toString().equals("")? 0:Integer.valueOf(proteinConsumedInput.getText().toString());



                Toast.makeText(getApplicationContext(), caloriesConsumed + " Calories Added", Toast.LENGTH_LONG).show();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("caloriesAdded", caloriesConsumed);
                resultIntent.putExtra("fatAdded", fatConsumed);
                resultIntent.putExtra("cholesterolAdded", cholesterolConsumed);
                resultIntent.putExtra("sodiumAdded", sodiumConsumed);
                resultIntent.putExtra("carbsAdded", carbsConsumed);
                resultIntent.putExtra("proteinAdded", proteinConsumed);
                setResult(RESULT_OK,resultIntent);


                finish();
            }
        });
    }
}
