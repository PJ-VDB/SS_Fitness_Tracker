//package com.example.pieter_jan.SS_fitness_tracker.data.model;
//
//import android.database.Cursor;
//import android.util.Log;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by pieter-jan on 3/4/2017.
// */
//
//public class junk {
//
//        /*
//    Checks if there is a workout on the date when the app starts up
//     */
////    private boolean isWorkoutAvailable(String date) {
////        boolean isAvailable;
////
////        if(fetchDayData(date) == null){
////            isAvailable = false;
////        } else {
////            isAvailable = true;
////        }
////
////        Log.i(TAG, "Day has a workout? " + isAvailable);
////        return isAvailable;
////    }
////
////    private void addWorkout() {
////    }
//
////    private void updateWorkoutUI(Workout workoutOnDay) {
////
////        long workoutType = workoutOnDay.progr(); // 1 : workout A; 2 : workout B
////
////        String previousWeight = ""; //TODO: find the previous workout and the used weight
////
////        if(workoutType == 1) {
////            // Load xml for workout A
////            for (int i = 3; i >= 0; i--) {
////                View exerciseBannerLayout = getLayoutInflater().inflate(R.layout.exercise_banner, null);
////
////                final TextView exercise_name_tv = (TextView) exerciseBannerLayout.findViewById(R.id.exercise_name_tv);
////                final TextView previous_exercise_weight_tv = (TextView) exerciseBannerLayout.findViewById(R.id.previous_exercise_weight_tv);
////                final TextView current_exercise_weight_tv = (TextView) exerciseBannerLayout.findViewById(R.id.current_exercise_weight_tv);
////                final TextView exercise_info_tv = (TextView) exerciseBannerLayout.findViewById(R.id.exercise_info_tv);
////
////                exercise_name_tv.setText("SQUAT");
////                previous_exercise_weight_tv.setText(previousWeight);
////                current_exercise_weight_tv.setText("");
////                exercise_info_tv.setText("5 x 3");
////
//////                exerciseCarouselContainer.addView(itemDateCarouselLayout);
////
////            }
////        }
////        if(workoutType == 2){
////            // Load xml for workout B
////
////
////        }
////
////    }
//
////    private Workout fetchDayData(String date) {
////        String[] selectionArgs = new String[]{ date };
////        Cursor cursor = mDatabase.rawQuery(Workout.SELECTWORKOUTBYDATE, selectionArgs);
////
////        if((cursor != null) && (cursor.getCount() > 0)){
////            cursor.moveToFirst();
////            Workout workoutData = Workout.MAPPER.map(cursor);
////            Log.e(TAG, "Workout found...");
////            return workoutData;
////        }
////        else {
////            Log.e(TAG, "No workout found...");
////            return null; // there is no workout yet on this date
////        }
////    }
//
//    private void insertWorkoutTest() {
//
//        Workout.InsertWorkout insertWorkout = new WorkoutModel.InsertWorkout(mDatabase);
//        insertWorkout.bind("workout A", (long) 5,(long) 5, (long) 5, (long) 5, (long) 5, (long) 5, (long) 5, (long) 5, (long) 5);
//        insertWorkout.program.execute();
//        insertWorkout.bind("workout A", (long) 5,(long) 5, (long) 5, (long) 5, (long) 5, (long) 5, (long) 5, (long) 5, (long) 5);
//        insertWorkout.program.execute();
//        insertWorkout.bind("workout A", (long) 5,(long) 5, (long) 5, (long) 5, (long) 5, (long) 5, (long) 5, (long) 5, (long) 5);
//        insertWorkout.program.execute();
//        insertWorkout.bind("workout A", (long) 5,(long) 5, (long) 5, (long) 5, (long) 5, (long) 5, (long) 5, (long) 5, (long) 5);
//        insertWorkout.program.execute();
//        insertWorkout.bind("workout B", (long) 5,(long) 5, (long) 5, (long) 5, (long) 5, (long) 5, (long) 5, (long) 5, (long) 5);
//        insertWorkout.program.execute();
//        insertWorkout.bind("workout C", (long) 5,(long) 5, (long) 5, (long) 5, (long) 5, (long) 5, (long) 5, (long) 5, (long) 5);
//        insertWorkout.program.execute();
//
//    }
//
//    private void insertExerciseTest(){
//        Exercise.InsertExercise insertExercise = new exercisesModel.InsertExercise(mDatabase);
//        insertExercise.bind("Deadlift", null);
//        insertExercise.program.execute();
//        insertExercise.bind("Squat", null);
//        insertExercise.program.execute();
//        insertExercise.bind("Bench Press", null);
//        insertExercise.program.execute();
//        insertExercise.bind("Shoulder Press", null);
//        insertExercise.program.execute();
//    }
//
//    private void insertExerciseLogTest(){
//        ExerciseLog.InsertExerciseInLog insertExerciseInLog = new exercise_logModel.InsertExerciseInLog(mDatabase);
//        insertExerciseInLog.bind("Squat", "today", null, (double) 155, (long) 1);
//        insertExerciseInLog.program.execute();
//    }
//
//    private void fetchExerciseTest(){
//        List<Exercise> exerciseList = new ArrayList<>();
//
//        Cursor cursor = mDatabase.rawQuery(Exercise.SELECTALL, new String[0]);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()){
//            exerciseList.add(Exercise.FACTORY.selectAllMapper().map(cursor));
//            cursor.moveToNext();
//        }
//        cursor.close();
//
//        Log.e(TAG, "The workout list size is: " + exerciseList.size());
//    }
//
//    private void fetchExerciseLogTest(){
//        List<ExerciseLog> exerciseLogList = new ArrayList<>();
//
//        Cursor cursor = mDatabase.rawQuery(ExerciseLog.SELECTALL, new String[0]);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()){
//            exerciseLogList.add(ExerciseLog.FACTORY.selectAllMapper().map(cursor));
//            cursor.moveToNext();
//        }
//        cursor.close();
//
//        Log.e(TAG, "The workout log list size is: " + exerciseLogList.size());
////        return exerciseLogList;
//
//    }
//
//    private void fetchWorkoutTest(){
//        List<Workout> workoutList = new ArrayList<>();
//        Cursor cursor = mDatabase.rawQuery(Workout.SELECTALL, new String[0]);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()){
//            workoutList.add(Workout.FACTORY.selectAllMapper().map(cursor));
//            cursor.moveToNext();
//        }
//        cursor.close();
//
//        Log.e(TAG, "The workout list size is: " + workoutList.size());
//    }
//
//}
