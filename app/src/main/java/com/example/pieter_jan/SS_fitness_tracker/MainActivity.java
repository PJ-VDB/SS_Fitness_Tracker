package com.example.pieter_jan.SS_fitness_tracker;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pieter_jan.SS_fitness_tracker.data.model.ExerciseLog;
import com.example.pieter_jan.SS_fitness_tracker.data.model.OpenHelper;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private DateCarousel dateCarousel;
    private SQLiteDatabase mDatabase;
    private OpenHelper mOpenHelper;
    private Context mContext;
    private boolean hasWorkOut;

    private FloatingActionButton actionA;
    private FloatingActionButton actionB;
    private FloatingActionButton actionC;

    private RecyclerView mExerciseRecyclerView;
    private ExerciseLogAdapter mAdapter;

    private List<ExerciseLog> mExerciseLogs;
    private String currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();

        mExerciseRecyclerView = (RecyclerView) findViewById(R.id.exercises_recycler_view);
        mExerciseRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        // Set up database
        mDatabase = mOpenHelper.getInstance(mContext).getWritableDatabase();

        //-------------------- Some tests
//        insertExerciseTest();
//        fetchExerciseTest();

//        insertExerciseLogTest();
//        fetchExerciseLogTest();

        // Insert some random workouts to test
//        insertWorkoutTest();
//        fetchWorkoutTest(); // prints out the number of items in the database

        // Set up some constants
//        hasWorkOut = isWorkoutAvailable(Utilities.getTodayDate());
        //-------------------------------------

        currentDate = Utilities.getTodayDate(); // the date used to fetch the data and update the UI
        updateUI();

        // Set up FAB
        setupFAB();

        // Set up datecarousel
        dateCarousel = (DateCarousel) findViewById(R.id.dateCarouselLayout);
        dateCarousel.addClickListener(new DateCarousel.CarouselClickListener() {
            @Override
            public void handleClick(String desiredDate) {
                //TODO: Update the views when the date is clicked
                currentDate = desiredDate;

                if(fetchExercisesOfDay(currentDate) != null){ // check if there is a workout yet on this day
                    updateUI();
                } else{
                    //TODO: take a look at this
                    updateUI();
                }
//                Toast.makeText(MainActivity.this, "Got data for the date : " + desiredDate + ". The workout day is " + workoutOnDay.date() , Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, "Got data for the date : " + desiredDate, Toast.LENGTH_SHORT).show();

            }
        });

        dateCarousel.post(new Runnable() {
            @Override
            public void run() {
                dateCarousel.setSmoothScrollingEnabled(true);
                dateCarousel.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        });
    }



    /**
     * Setup of the floating action button with the menu to add workouts
     */
    private void setupFAB() {
        actionA = (FloatingActionButton) findViewById(R.id.action_a);
        actionB = (FloatingActionButton) findViewById(R.id.action_b);
        actionC = (FloatingActionButton) findViewById(R.id.action_c);

        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionA.setTitle("Workout A added");
                addWorkoutA();
                updateUI();

                // You can only add 1 type of program per day (you can still add extra exercises)
//                actionA.setClickable(false);
//                actionB.setClickable(false);
                //TODO: close fab
            }
        });

        actionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionB.setTitle("Workout B added");
                addWorkoutB();
                updateUI();

                // TODO: you can only add 1 program, but you can still add other exercises
                //TODO: close fab & make other buttons unclickable
            }
        });

        actionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionB.setTitle("Squat added");
                //TODO: close fab & make other buttons unclickable
                addSquat();
            }
        });
    }

    private void addWorkoutA() {
        addExercise("Squat", currentDate);
        addExercise("Bench Press", currentDate);
        addExercise("Deadlift", currentDate);
    }

    private void addWorkoutB() {
        addExercise("Squat", currentDate);
        addExercise("Shoulder Press", currentDate);
        addExercise("Deadlift", currentDate);
    }

    private void addExercise(String name, String date){
        ExerciseLog.InsertExerciseInLog insertExercise = new ExerciseLog.InsertExerciseInLog(mDatabase);
        insertExercise.bind(name, date, null, null, null);
        insertExercise.program.execute();
    }

    private void deleteExercise(long exerciseId){
        ExerciseLog.DeleteWorkoutById deleteExercise = new ExerciseLog.DeleteWorkoutById(mDatabase);
        deleteExercise.bind(exerciseId);
        deleteExercise.program.execute();
    }

    // TODO: update the exercise when clicked with the weight and if succeeded
    private void updateExercise(){};

    private void addSquat() {
        ExerciseLog.InsertExerciseInLog insertExercise = new ExerciseLog.InsertExerciseInLog(mDatabase);
        insertExercise.bind("Squat", currentDate, null, null, null);
        insertExercise.program.execute();
        updateUI();
    }

    public void updateUI(){
        mExerciseLogs = fetchExercisesOfDay(currentDate);

        if (mAdapter == null){
            mAdapter = new ExerciseLogAdapter(mExerciseLogs);
            mExerciseRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setExercises(mExerciseLogs);
            mAdapter.notifyDataSetChanged();
        }
    }

    //TODO: give day as input
    private List<ExerciseLog> fetchExercisesOfDay(String date) {

        List<ExerciseLog> exerciseList = new ArrayList<>();

        String[] selectionArgs = new String[]{ date };
        Cursor cursor = mDatabase.rawQuery(ExerciseLog.SELECTEXERCISEBYDATEFROMLOG, selectionArgs);

        if((cursor != null) && (cursor.getCount() > 0)){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                exerciseList.add(ExerciseLog.FACTORY.selectExerciseByDateFromLogMapper().map(cursor));
                cursor.moveToNext();
            }
            cursor.close();

            Log.e(TAG, "the workout today was found");
            return exerciseList;
        }
        else {
            Log.e(TAG, "No workout found...");
            return null; // there is no workout yet on this date
        }
    }

    private class ExerciseLogHolder extends RecyclerView.ViewHolder{
        private TextView mExerciseNameTV;
        private TextView mPreviousExerciseWeightTV;
        private TextView mCurrentExerciseWeightTV;
        private TextView mExerciseInfoTV;

        private ExerciseLog mExerciseLog;

        public ExerciseLogHolder(View itemView){
            super(itemView);

            mExerciseNameTV = (TextView) itemView.findViewById(R.id.exercise_name_tv);
            mPreviousExerciseWeightTV = (TextView) itemView.findViewById(R.id.previous_exercise_weight_tv);
            mCurrentExerciseWeightTV = (TextView) itemView.findViewById(R.id.current_exercise_weight_tv);
            mExerciseInfoTV = (TextView) itemView.findViewById(R.id.exercise_info_tv);
        }

        public void bindExercise(com.example.pieter_jan.SS_fitness_tracker.data.model.ExerciseLog exerciseLog){
            mExerciseLog = exerciseLog;

            mExerciseNameTV.setText(mExerciseLog.exercise_name());
            mPreviousExerciseWeightTV.setText(mExerciseLog.date());
        }


    }

    private class ExerciseLogAdapter extends RecyclerView.Adapter<ExerciseLogHolder>{
        private List<ExerciseLog> mExercises;

        public ExerciseLogAdapter(List<ExerciseLog> exercises){
            mExercises = exercises;
        }

        @Override
        public ExerciseLogHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.exercise_banner, parent, false);
            return new ExerciseLogHolder(view);
        }

        @Override
        public void onBindViewHolder(ExerciseLogHolder holder, int position) {
            ExerciseLog exerciseLog = mExercises.get(position);
            holder.bindExercise(exerciseLog);
        }

        @Override
        public int getItemCount() {
            // This makes sure the app doesn't crash if there is no data found for the day
            if(mExercises != null) {
                return mExercises.size();
            }
            else {
                return 0;
            }
        }

        public void setExercises(List<ExerciseLog> exercises){
            mExercises = exercises;
        }
    }



}
