package com.example.pieter_jan.SS_fitness_tracker.data.model;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

/**
 * Created by pieter-jan on 3/2/2017.
 */

@AutoValue
public abstract class Workout implements WorkoutModel {


    public static final Creator<Workout> CREATOR = new Creator<Workout>() {
        @Override
        public Workout create(long id, @Nullable String date, @Nullable Long progr, @Nullable Long squat_weight, @Nullable Long squat_reps, @Nullable Long bench_weigth, @Nullable Long bench_reps, @Nullable Long press_weight, @Nullable Long press_reps, @Nullable Long deadlift_weight, @Nullable Long deadlift_reps) {
            return new AutoValue_Workout(id, date, progr, squat_weight, squat_reps, bench_weigth, bench_reps, press_weight, press_reps, deadlift_weight, deadlift_reps);
        }
    };

    public static final Factory<Workout> FACTORY = new Factory<>(CREATOR);
    public static final Mapper<Workout> MAPPER = new Mapper<>(FACTORY);


}
