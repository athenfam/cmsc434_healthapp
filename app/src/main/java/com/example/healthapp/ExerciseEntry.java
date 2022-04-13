package com.example.healthapp;

public class ExerciseEntry {
    public String exerciseName;
    public int calories;
    public String comments;

    public ExerciseEntry(String name, int cal, String comment){
        this.exerciseName = name;
        this.calories = cal;
        this.comments = comment;
    }
}
