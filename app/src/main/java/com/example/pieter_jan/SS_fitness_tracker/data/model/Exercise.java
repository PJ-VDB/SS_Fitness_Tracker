package com.example.pieter_jan.SS_fitness_tracker.data.model;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

/**
 * Created by pieter-jan on 3/3/2017.
 */

@AutoValue
public abstract class Exercise implements exercisesModel {

    public static final exercisesModel.Creator<Exercise> CREATOR = new Creator<Exercise>() {
        @Override
        public Exercise create(@Nullable String name, @Nullable String note) {
            return new AutoValue_Exercise(name, note);
        }
    };

    public static final exercisesModel.Factory<Exercise> FACTORY = new exercisesModel.Factory<>(CREATOR);
    public static final exercisesModel.Mapper<Exercise> MAPPER = new exercisesModel.Mapper<>(FACTORY);

}
