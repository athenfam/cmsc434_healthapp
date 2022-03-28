package com.example.healthapp;

public class FoodEntry {
    public String foodName;
    public int calories, fat, cholesterol, sodium, carbs, protein;

    public FoodEntry(String name, int cal){
        this.foodName = name;
        this.calories = cal;
    }
}
