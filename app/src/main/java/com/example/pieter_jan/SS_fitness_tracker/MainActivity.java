package com.example.pieter_jan.SS_fitness_tracker;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.example.pieter_jan.SS_fitness_tracker.data.model.ExerciseLog;
import com.example.pieter_jan.SS_fitness_tracker.data.model.OpenHelper;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
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
        setupUI(findViewById(R.id.activity_main)); // Set up the UI so that keyboard is hidden if something is touched

        mContext = getApplicationContext();

        // Set up database: Should always be in the beginning!
        mDatabase = mOpenHelper.getInstance(mContext).getWritableDatabase();
        currentDate = Utilities.getTodayDate(); // the date used to fetch the data and update the UI


        //---

        mExerciseRecyclerView = (RecyclerView) findViewById(R.id.exercises_recycler_view);
        setupRecyclerView();



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
//                Toast.makeText(MainActivity.this, "Got data for the date : " + desiredDate, Toast.LENGTH_SHORT).show();

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

    private void setupRecyclerView() {
        mExerciseRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        updateUI();
        setupItemTouchHelper();
        setupAnimationDecoratorHelper();
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    /**
     * From: https://github.com/nemanja-kovacevic/recycler-view-swipe-to-delete/blob/master/app/src/main/java/net/nemanjakovacevic/recyclerviewswipetodelete/MainActivity.java
     * This is the standard support library way of implementing "swipe to delete" feature. You can do custom drawing in onChildDraw method
     * but whatever you draw will disappear once the swipe is over, and while the items are animating to their new position the recycler view
     * background will be visible. That is rarely an desired effect.
     */
    private void setupItemTouchHelper() {

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // we want to cache these and not allocate anything repeatedly in the onChildDraw method
            Drawable background;
            Drawable xMark;
            int xMarkMargin;
            boolean initiated;

            private void init() {
                background = new ColorDrawable(Color.RED);
                xMark = ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_clear_24dp);
                xMark.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                xMarkMargin = (int) MainActivity.this.getResources().getDimension(R.dimen.ic_clear_margin);
                initiated = true;
            }

            // not important, we don't want drag & drop
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int position = viewHolder.getAdapterPosition();
                ExerciseLogAdapter testAdapter = (ExerciseLogAdapter) recyclerView.getAdapter();
                if (testAdapter.isUndoOn() && testAdapter.isPendingRemoval(position)) {
                    return 0;
                }
                return super.getSwipeDirs(recyclerView, viewHolder);
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int swipedPosition = viewHolder.getAdapterPosition();
                ExerciseLogAdapter adapter = (ExerciseLogAdapter) mExerciseRecyclerView.getAdapter();
                boolean undoOn = adapter.isUndoOn();
                if (undoOn) {
                    adapter.pendingRemoval(swipedPosition);
                } else {
                    adapter.remove(swipedPosition);
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                View itemView = viewHolder.itemView;

                // not sure why, but this method get's called for viewholder that are already swiped away
                if (viewHolder.getAdapterPosition() == -1) {
                    // not interested in those
                    return;
                }

                if (!initiated) {
                    init();
                }

                // draw red background
                background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                background.draw(c);

                // draw x mark
                int itemHeight = itemView.getBottom() - itemView.getTop();
                int intrinsicWidth = xMark.getIntrinsicWidth();
                int intrinsicHeight = xMark.getIntrinsicWidth();

                int xMarkLeft = itemView.getRight() - xMarkMargin - intrinsicWidth;
                int xMarkRight = itemView.getRight() - xMarkMargin;
                int xMarkTop = itemView.getTop() + (itemHeight - intrinsicHeight)/2;
                int xMarkBottom = xMarkTop + intrinsicHeight;
                xMark.setBounds(xMarkLeft, xMarkTop, xMarkRight, xMarkBottom);

                xMark.draw(c);

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        };
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        mItemTouchHelper.attachToRecyclerView(mExerciseRecyclerView);
    }

    /**
     * We're gonna setup another ItemDecorator that will draw the red background in the empty space while the items are animating to their new positions
     * after an item is removed.
     */
    private void setupAnimationDecoratorHelper() {
        mExerciseRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {

            // we want to cache this and not allocate anything repeatedly in the onDraw method
            Drawable background;
            boolean initiated;

            private void init() {
                background = new ColorDrawable(Color.RED);
                initiated = true;
            }

            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

                if (!initiated) {
                    init();
                }

                // only if animation is in progress
                if (parent.getItemAnimator().isRunning()) {

                    // some items might be animating down and some items might be animating up to close the gap left by the removed item
                    // this is not exclusive, both movement can be happening at the same time
                    // to reproduce this leave just enough items so the first one and the last one would be just a little off screen
                    // then remove one from the middle

                    // find first child with translationY > 0
                    // and last one with translationY < 0
                    // we're after a rect that is not covered in recycler-view views at this point in time
                    View lastViewComingDown = null;
                    View firstViewComingUp = null;

                    // this is fixed
                    int left = 0;
                    int right = parent.getWidth();

                    // this we need to find out
                    int top = 0;
                    int bottom = 0;

                    // find relevant translating views
                    int childCount = parent.getLayoutManager().getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View child = parent.getLayoutManager().getChildAt(i);
                        if (child.getTranslationY() < 0) {
                            // view is coming down
                            lastViewComingDown = child;
                        } else if (child.getTranslationY() > 0) {
                            // view is coming up
                            if (firstViewComingUp == null) {
                                firstViewComingUp = child;
                            }
                        }
                    }

                    if (lastViewComingDown != null && firstViewComingUp != null) {
                        // views are coming down AND going up to fill the void
                        top = lastViewComingDown.getBottom() + (int) lastViewComingDown.getTranslationY();
                        bottom = firstViewComingUp.getTop() + (int) firstViewComingUp.getTranslationY();
                    } else if (lastViewComingDown != null) {
                        // views are going down to fill the void
                        top = lastViewComingDown.getBottom() + (int) lastViewComingDown.getTranslationY();
                        bottom = lastViewComingDown.getBottom();
                    } else if (firstViewComingUp != null) {
                        // views are coming up to fill the void
                        top = firstViewComingUp.getTop();
                        bottom = firstViewComingUp.getTop() + (int) firstViewComingUp.getTranslationY();
                    }

                    background.setBounds(left, top, right, bottom);
                    background.draw(c);

                }
                super.onDraw(c, parent, state);
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
                actionC.setTitle("Squat added");
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
    private void updateExerciseWeight(double weight, long id){
        ExerciseLog.UpdateWorkoutWeightById updateExercise = new ExerciseLog.UpdateWorkoutWeightById(mDatabase);
        updateExercise.bind(weight, id);
        updateExercise.program.execute();
        Log.i(TAG, "the id of the date whose weight was changed is: " + id);


    }

    private void addSquat() {
        ExerciseLog.InsertExerciseInLog insertExercise = new ExerciseLog.InsertExerciseInLog(mDatabase);
        insertExercise.bind("Squat", currentDate, null, null, null);
        insertExercise.program.execute();
        updateUI();
    }

    // Recursive way to set up the UI so that the keyboard is also hid when anything other than an edittext is clicked
    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(MainActivity.this);
                    v.requestFocus(); //added, this makes it so the clicked view is also actually focused
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
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

//            Log.i(TAG, "the workout today was found");
            return exerciseList;
        }
        else {
//            Log.i(TAG, "No workout found...");
            return null; // there is no workout yet on this date
        }
    }

    private class ExerciseLogHolder extends RecyclerView.ViewHolder{
        private TextView mExerciseNameTV;
        private TextView mPreviousExerciseWeightTV;
        private EditText mCurrentExerciseWeightTV;
        private TextView mExerciseInfoTV;
        private Button mUndoButton;

        private String inputWeight;

        private ExerciseLog mExerciseLog;
        private TextWatcher mTextWatcher;

        public ExerciseLogHolder(View itemView){
            super(itemView);

            mExerciseNameTV = (TextView) itemView.findViewById(R.id.exercise_name_tv);
            mPreviousExerciseWeightTV = (TextView) itemView.findViewById(R.id.previous_exercise_weight_tv);
            mCurrentExerciseWeightTV = (EditText) itemView.findViewById(R.id.current_exercise_weight_tv);
            mExerciseInfoTV = (TextView) itemView.findViewById(R.id.exercise_info_tv);
            mUndoButton = (Button) itemView.findViewById(R.id.undo_button);

//            mCurrentExerciseWeightTV.setOnFocusChangeListener(mOnFocusChangeListener);

        }

        public void bindExercise(com.example.pieter_jan.SS_fitness_tracker.data.model.ExerciseLog exerciseLog){

            mExerciseLog = exerciseLog;

            if(mExerciseLog.weight() != null) {
                inputWeight = mExerciseLog.weight().toString();
            } else{
                inputWeight = null;
            }

            mExerciseNameTV.setText(mExerciseLog.exercise_name());

            if(inputWeight != null){
                mPreviousExerciseWeightTV.setText(inputWeight);
                mCurrentExerciseWeightTV.setText(inputWeight);
            } else{
                mCurrentExerciseWeightTV.setText("");
            }

//            if(exerciseLog.weight() != null ){ //check if there already is a weight in the table
//                mPreviousExerciseWeightTV.setText(mExerciseLog.weight().toString());
//                mCurrentExerciseWeightTV.setText(mExerciseLog.weight().toString());
//            } else {
//                // keep null in the field
//            }

        }


    }

    private class ExerciseLogAdapter extends RecyclerView.Adapter<ExerciseLogHolder>{
        private List<ExerciseLog> mExercises;
        private List<ExerciseLog> mExercisesPendingRemoval;
        boolean undoOn = false; // Works partially, some bug with the red background

        private static final int PENDING_REMOVAL_TIMEOUT = 3000; // 3sec


        private Handler handler = new Handler(); // handler for running delayed runnables
        HashMap<ExerciseLog, Runnable> pendingRunnables = new HashMap<>(); // map of items to pending runnables, so we can cancel a removal if need be


        public ExerciseLogAdapter(List<ExerciseLog> exercises){
            mExercises = exercises;
            mExercisesPendingRemoval = new ArrayList<>();
        }

        @Override
        public ExerciseLogHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.exercise_banner, parent, false);
            return new ExerciseLogHolder(view);
        }

        @Override
        public void onBindViewHolder(final ExerciseLogHolder holder, int position) {
            final ExerciseLog exerciseLog = mExercises.get(position);
            final String setWeigth = "";

            if(mExercisesPendingRemoval.contains(exerciseLog)){
                // we need to show the "undo" state of the row
                holder.itemView.setBackgroundColor(Color.RED);
                holder.mExerciseNameTV.setVisibility(View.GONE);
                holder.mPreviousExerciseWeightTV.setVisibility(View.GONE);
                holder.mCurrentExerciseWeightTV.setVisibility(View.GONE);
                holder.mExerciseInfoTV.setVisibility(View.GONE);

                holder.mUndoButton.setVisibility(View.VISIBLE);
                holder.mUndoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // user wants to undo the removal, let's cancel the pending task
                        Runnable pendingRemovalRunnable = pendingRunnables.get(exerciseLog);
                        pendingRunnables.remove(exerciseLog);
                        if (pendingRemovalRunnable != null) handler.removeCallbacks(pendingRemovalRunnable);
                        mExercisesPendingRemoval.remove(exerciseLog);
                        // this will rebind the row in "normal" state
                        notifyItemChanged(mExercises.indexOf(exerciseLog));
                    }
                });
            } else {
                // we need to show the "normal" state

                //////////////// FOCUSCHANGELISTENER APPROACH CURRENTLY NOT WORKING
//                // Remove any existing FocusChangeListener that will be keyed to the wrong ListItem
//                if(holder.mOnFocusChangeListener != null){
//                    holder.mOnFocusChangeListener = null;
//                    holder.mCurrentExerciseWeightTV.setOnFocusChangeListener(holder.mOnFocusChangeListener); // remove it essentially
//                }
//
//                View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
//                    @Override
//                    public void onFocusChange(View v, boolean hasFocus) {
//                        if(!hasFocus){
//                            // The user is most likely done typing
//                            // So time to update the table with the weight
//
//                            holder.inputWeight = holder.mCurrentExerciseWeightTV.getText().toString();
//                            holder.mPreviousExerciseWeightTV.setText(holder.inputWeight);
//
//                            try {
//                                updateExerciseWeight(Double.parseDouble(holder.inputWeight), exerciseLog.id());
//                                Log.i(TAG, "the weight changed to " + holder.inputWeight);
//                            } catch(NumberFormatException ex){
//                                Log.e(TAG, "The input for the current weight was in the wrong format.");
//                            }
//
//
//                        }
//                    }
//                };
//
//                holder.mCurrentExerciseWeightTV.setOnFocusChangeListener(onFocusChangeListener);
//
//                holder.bindExercise(exerciseLog, onFocusChangeListener);

                if (holder.mTextWatcher != null) {
                    holder.mCurrentExerciseWeightTV.removeTextChangedListener(holder.mTextWatcher);
                }

                holder.mTextWatcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        holder.inputWeight = s.toString();
                        Log.e(TAG, "The weight is set at" + holder.inputWeight);
                    }
                };

                holder.mCurrentExerciseWeightTV.addTextChangedListener(holder.mTextWatcher);

                holder.bindExercise(exerciseLog);
                holder.mUndoButton.setVisibility(View.GONE);
                holder.mUndoButton.setOnClickListener(null);
            }

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

        public void setUndoOn(boolean undoOn){
            this.undoOn = undoOn;
        }

        public boolean isUndoOn() {
            return undoOn;
        }

        public void pendingRemoval(int position) {
            final ExerciseLog item = mExercises.get(position);
            if (!mExercisesPendingRemoval.contains(item)) {
                mExercisesPendingRemoval.add(item);
                // this will redraw row in "undo" state
                notifyItemChanged(position);
                // let's create, store and post a runnable to remove the item
                Runnable pendingRemovalRunnable = new Runnable() {
                    @Override
                    public void run() {
//                        remove(mExercises.indexOf(item));
//                        deleteExercise(item.id());
                        remove(mExercises.indexOf(item));
                        //TODO: updateUI() ??
                    }
                };
                handler.postDelayed(pendingRemovalRunnable, PENDING_REMOVAL_TIMEOUT);
                pendingRunnables.put(item, pendingRemovalRunnable);
            }
        }

        public void remove(int position) {
            ExerciseLog item = mExercises.get(position);
            if (mExercisesPendingRemoval.contains(item)) {
                mExercisesPendingRemoval.remove(item);
            }
            if (mExercises.contains(item)) {
//                mExercisesPendingRemoval.remove(position);
                deleteExercise(item.id());
                notifyItemRemoved(position);
                updateUI();
                //TODO: updateUI() ??
            }
        }

        public boolean isPendingRemoval(int position) {
            ExerciseLog item = mExercises.get(position);
            return mExercisesPendingRemoval.contains(item);
        }

    }


}
