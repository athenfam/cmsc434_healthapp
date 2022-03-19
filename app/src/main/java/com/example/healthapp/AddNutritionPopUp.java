package com.example.healthapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;


public class AddNutritionPopUp extends Activity {
    int caloriesConsumed;
    EditText caloriesConsumedInput;
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
        submitButton = (Button) findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(caloriesConsumedInput.getText().toString().equals("")){
                    caloriesConsumedInput.setError("Please Enter Valid Calories!");
                    return;
                }
                caloriesConsumed = Integer.valueOf(caloriesConsumedInput.getText().toString());

                Toast.makeText(getApplicationContext(), caloriesConsumed + " Calories Added", Toast.LENGTH_SHORT).show();

                Intent intent = getIntent();
                Bundle bundle = new Bundle();
                bundle.putString("caloriesConsumed", caloriesConsumedInput.getText().toString());

                //NutritionFragment fragment = (NutritionFragment) getFragmentManager().findFragmentById(R.id.nutritionid);
                //fragment.updateTotalCaloriesConsumed(bundle);

                finish();
            }
        });
    }
}
