package com.example.pieter_jan.SS_fitness_tracker.data.model;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

/**
 * Created by pieter-jan on 3/3/2017.
 */

@AutoValue
public abstract class SetsLog implements sets_logModel {

    public static final sets_logModel.Creator<SetsLog> CREATOR = new Creator<SetsLog>() {
        @Override
        public SetsLog create(@Nullable Long id, @Nullable String date, @Nullable Long reps, @Nullable Double weight, @Nullable String note, @Nullable Long exercise_log_id, @Nullable Long exercise_name) {
            return new AutoValue_SetsLog(id, date, reps, weight, note, exercise_log_id, exercise_name);
        }
    };

    public static final sets_logModel.Factory<SetsLog> FACTORY = new sets_logModel.Factory<>(CREATOR);
    public static final sets_logModel.Mapper<SetsLog> MAPPER = new sets_logModel.Mapper<>(FACTORY);

}
