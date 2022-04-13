package com.example.healthapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.os.Bundle;
import android.view.MenuItem;

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
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, home).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    Bundle bundle = new Bundle();
                    switch (item.getItemId()){
                        case R.id.nav_home:
                            bundle.putInt("calorieGoal", settings.getCalorieGoal());
                            bundle.putDouble("weightGoal", settings.getWeightGoal());
                            bundle.putInt("calories", nutrition.getNutritionValue("calories"));
                            bundle.putString("name",settings.getName());
                            bundle.putInt("calorieBurned", exercise.getCaloriesBurned());
                            bundle.putInt("calorieBurnedGoal", settings.getCalorieBurnedGoal());
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
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                    return true;
                }
            };
}