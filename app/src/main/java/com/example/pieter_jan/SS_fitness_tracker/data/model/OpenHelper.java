package com.example.pieter_jan.SS_fitness_tracker.data.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by pieter-jan on 3/2/2017.
 */

public class OpenHelper extends SQLiteOpenHelper {

    public static final String TAG = "OpenHelper";
    public static final String DB_NAME = "workout.db";
    public static final int DB_VERSION = 1;

    private static OpenHelper instance;

    public static OpenHelper getInstance(Context context){
        if(null == instance){
            instance = new OpenHelper(context);
        }
        return instance;
    }

    private OpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(Workout.CREATE_TABLE);
        db.execSQL(exercisesModel.CREATE_TABLE);
        db.execSQL(exercise_logModel.CREATE_TABLE);
        db.execSQL(sets_logModel.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO: upgrade update logic
    }
}
