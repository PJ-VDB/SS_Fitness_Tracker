package com.example.pieter_jan.SS_fitness_tracker.data.model;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

/**
 * Created by pieter-jan on 3/3/2017.
 */

@AutoValue
public abstract class ExerciseLog implements exercise_logModel {

    public static final exercise_logModel.Creator<ExerciseLog> CREATOR = new Creator<ExerciseLog>() {
        @Override
        public ExerciseLog create(@Nullable Long id, @Nullable String exercise_name, @Nullable String date, @Nullable String note, @Nullable Double weight, @Nullable Long succeeded) {
            return new AutoValue_ExerciseLog(id, exercise_name, date, note, weight, succeeded);
        }
    };

//    static Builder builder(){
//        return new AutoValue_ExerciseLog.Builder();
//    }
//
//    @AutoValue.Builder
//    abstract static class Builder {
//        abstract Builder setExercise_ame(String value);
//        abstract Builder setDate(String value);
//        abstract Builder setWeight(Double value);
//        abstract Builder setNote(String value);
//        abstract Builder setSucceeded(Long value);
//        abstract ExerciseLog build();
//    }


    public static final exercise_logModel.Factory<ExerciseLog> FACTORY = new exercise_logModel.Factory<>(CREATOR);
    public static final exercise_logModel.Mapper<ExerciseLog> MAPPER = new exercise_logModel.Mapper<>(FACTORY);

}
