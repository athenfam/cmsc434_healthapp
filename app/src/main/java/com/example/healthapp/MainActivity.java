package com.example.healthapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    HomeFragment home;
    FitnessFragment exercise;
    NutritionFragment nutrition;
    SettingsFragment settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        home = new HomeFragment();
        exercise = new FitnessFragment();
        nutrition = new NutritionFragment();
        settings = new SettingsFragment();
        bottomNav.getMenu().getItem(3).setChecked(true);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, settings).commit();
    }

    private void homeSetup(){

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    Bundle bundle = new Bundle();
                    if(settings.getIsSetupDone()){
                        switch (item.getItemId()){
                            case R.id.nav_home:
                                bundle.putInt("calorieGoal", settings.getCalorieGoal());
                                bundle.putInt("calories", nutrition.getNutritionValue("calories"));
                                bundle.putString("name",settings.getName());
                                bundle.putInt("calorieBurned", exercise.getCaloriesBurned());
                                bundle.putInt("calorieBurnedGoal", settings.getCalorieBurnedGoal());
                                bundle.putDouble("currWeight", settings.getCurrWeight());
                                bundle.putDouble("goalWeight", settings.getGoalWeight());
                                bundle.putBoolean("isSetup", settings.getIsSetupDone());
                                selectedFragment = home;
                                selectedFragment.setArguments(bundle);
                                break;
                            case R.id.nav_fitness:
                                selectedFragment = exercise;
                                break;
                            case R.id.nav_nutrition:
                                selectedFragment = nutrition;
                                break;
                            case R.id.nav_settings:
                                selectedFragment = settings;
                                break;
                        }
                    }else{
                        selectedFragment = settings;
                        Toast.makeText(getApplicationContext(), "Please Provide Initial Setup", Toast.LENGTH_LONG).show();
                        return false;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                    return true;
                }
            };
}