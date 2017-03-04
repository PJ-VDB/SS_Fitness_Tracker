package com.example.pieter_jan.SS_fitness_tracker.data.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.squareup.sqldelight.RowMapper;
import com.squareup.sqldelight.SqlDelightCompiledStatement;
import com.squareup.sqldelight.SqlDelightStatement;
import java.lang.Deprecated;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface WorkoutModel {
    String TABLE_NAME = "workout_db";

    String ID = "id";

    String DATE = "date";

    String PROGR = "progr";

    String SQUAT_WEIGHT = "squat_weight";

    String SQUAT_REPS = "squat_reps";

    String BENCH_WEIGTH = "bench_weigth";

    String BENCH_REPS = "bench_reps";

    String PRESS_WEIGHT = "press_weight";

    String PRESS_REPS = "press_reps";

    String DEADLIFT_WEIGHT = "deadlift_weight";

    String DEADLIFT_REPS = "deadlift_reps";

    String CREATE_TABLE = ""
            + "CREATE TABLE workout_db (\n"
            + "    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n"
            + "    date\tTEXT,\n"
            + "    progr\tINTEGER,\n"
            + "    squat_weight\tINTEGER,\n"
            + "    squat_reps\tINTEGER,\n"
            + "    bench_weigth\tINTEGER,\n"
            + "    bench_reps\tINTEGER,\n"
            + "    press_weight\tINTEGER,\n"
            + "    press_reps\tINTEGER,\n"
            + "    deadlift_weight\tINTEGER,\n"
            + "    deadlift_reps\tINTEGER\n"
            + ")";

    String SELECTALL = ""
            + "SELECT * FROM workout_db";

    String SELECTWORKOUTBYDATE = ""
            + "SELECT * FROM workout_db WHERE date = ?";

    String SELECTWORKOUTBYID = ""
            + "SELECT * FROM workout_db WHERE id = ?";

    String SELECTWORKOUTBYPROGRAM = ""
            + "SELECT * FROM workout_db WHERE progr = ?";

    String FINDIDBYDATE = ""
            + "SELECT * FROM workout_db WHERE date = ?";

    String FINDDATEBYID = ""
            + "SELECT * FROM workout_db WHERE id = ?";

    String FINDDATEBYPROGRAM = ""
            + "SELECT * FROM workout_db WHERE progr = ?";

    long id();

    @Nullable
    String date();

    @Nullable
    Long progr();

    @Nullable
    Long squat_weight();

    @Nullable
    Long squat_reps();

    @Nullable
    Long bench_weigth();

    @Nullable
    Long bench_reps();

    @Nullable
    Long press_weight();

    @Nullable
    Long press_reps();

    @Nullable
    Long deadlift_weight();

    @Nullable
    Long deadlift_reps();

    interface Creator<T extends WorkoutModel> {
        T create(long id, @Nullable String date, @Nullable Long progr, @Nullable Long squat_weight, @Nullable Long squat_reps, @Nullable Long bench_weigth, @Nullable Long bench_reps, @Nullable Long press_weight, @Nullable Long press_reps, @Nullable Long deadlift_weight, @Nullable Long deadlift_reps);
    }

    final class Mapper<T extends WorkoutModel> implements RowMapper<T> {
        private final Factory<T> workoutModelFactory;

        public Mapper(Factory<T> workoutModelFactory) {
            this.workoutModelFactory = workoutModelFactory;
        }

        @Override
        public T map(@NonNull Cursor cursor) {
            return workoutModelFactory.creator.create(
                    cursor.getLong(0),
                    cursor.isNull(1) ? null : cursor.getString(1),
                    cursor.isNull(2) ? null : cursor.getLong(2),
                    cursor.isNull(3) ? null : cursor.getLong(3),
                    cursor.isNull(4) ? null : cursor.getLong(4),
                    cursor.isNull(5) ? null : cursor.getLong(5),
                    cursor.isNull(6) ? null : cursor.getLong(6),
                    cursor.isNull(7) ? null : cursor.getLong(7),
                    cursor.isNull(8) ? null : cursor.getLong(8),
                    cursor.isNull(9) ? null : cursor.getLong(9),
                    cursor.isNull(10) ? null : cursor.getLong(10)
            );
        }
    }

    final class Marshal {
        protected final ContentValues contentValues = new ContentValues();

        Marshal(@Nullable WorkoutModel copy) {
            if (copy != null) {
                this.id(copy.id());
                this.date(copy.date());
                this.progr(copy.progr());
                this.squat_weight(copy.squat_weight());
                this.squat_reps(copy.squat_reps());
                this.bench_weigth(copy.bench_weigth());
                this.bench_reps(copy.bench_reps());
                this.press_weight(copy.press_weight());
                this.press_reps(copy.press_reps());
                this.deadlift_weight(copy.deadlift_weight());
                this.deadlift_reps(copy.deadlift_reps());
            }
        }

        public ContentValues asContentValues() {
            return contentValues;
        }

        public Marshal id(long id) {
            contentValues.put("id", id);
            return this;
        }

        public Marshal date(String date) {
            contentValues.put("date", date);
            return this;
        }

        public Marshal progr(Long progr) {
            contentValues.put("progr", progr);
            return this;
        }

        public Marshal squat_weight(Long squat_weight) {
            contentValues.put("squat_weight", squat_weight);
            return this;
        }

        public Marshal squat_reps(Long squat_reps) {
            contentValues.put("squat_reps", squat_reps);
            return this;
        }

        public Marshal bench_weigth(Long bench_weigth) {
            contentValues.put("bench_weigth", bench_weigth);
            return this;
        }

        public Marshal bench_reps(Long bench_reps) {
            contentValues.put("bench_reps", bench_reps);
            return this;
        }

        public Marshal press_weight(Long press_weight) {
            contentValues.put("press_weight", press_weight);
            return this;
        }

        public Marshal press_reps(Long press_reps) {
            contentValues.put("press_reps", press_reps);
            return this;
        }

        public Marshal deadlift_weight(Long deadlift_weight) {
            contentValues.put("deadlift_weight", deadlift_weight);
            return this;
        }

        public Marshal deadlift_reps(Long deadlift_reps) {
            contentValues.put("deadlift_reps", deadlift_reps);
            return this;
        }
    }

    final class Factory<T extends WorkoutModel> {
        public final Creator<T> creator;

        public Factory(Creator<T> creator) {
            this.creator = creator;
        }

        /**
         * @deprecated Use compiled statements (https://github.com/square/sqldelight#compiled-statements)
         */
        @Deprecated
        public Marshal marshal() {
            return new Marshal(null);
        }

        /**
         * @deprecated Use compiled statements (https://github.com/square/sqldelight#compiled-statements)
         */
        @Deprecated
        public Marshal marshal(WorkoutModel copy) {
            return new Marshal(copy);
        }

        public SqlDelightStatement SelectWorkoutByDate(@Nullable String date) {
            List<String> args = new ArrayList<String>();
            int currentIndex = 1;
            StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM workout_db WHERE date = ");
            if (date == null) {
                query.append("null");
            } else {
                query.append('?').append(currentIndex++);
                args.add(date);
            }
            return new SqlDelightStatement(query.toString(), args.toArray(new String[args.size()]), Collections.<String>singleton("workout_db"));
        }

        public SqlDelightStatement SelectWorkoutById(long id) {
            List<String> args = new ArrayList<String>();
            int currentIndex = 1;
            StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM workout_db WHERE id = ");
            query.append(id);
            return new SqlDelightStatement(query.toString(), args.toArray(new String[args.size()]), Collections.<String>singleton("workout_db"));
        }

        public SqlDelightStatement SelectWorkoutByProgram(@Nullable Long progr) {
            List<String> args = new ArrayList<String>();
            int currentIndex = 1;
            StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM workout_db WHERE progr = ");
            if (progr == null) {
                query.append("null");
            } else {
                query.append(progr);
            }
            return new SqlDelightStatement(query.toString(), args.toArray(new String[args.size()]), Collections.<String>singleton("workout_db"));
        }

        /**
         * @deprecated Use {@link InsertWorkout}
         */
        @Deprecated
        public SqlDelightStatement InsertWorkout(@Nullable String date, @Nullable Long progr, @Nullable Long squat_weight, @Nullable Long squat_reps, @Nullable Long bench_weigth, @Nullable Long bench_reps, @Nullable Long press_weight, @Nullable Long press_reps, @Nullable Long deadlift_weight, @Nullable Long deadlift_reps) {
            List<String> args = new ArrayList<String>();
            int currentIndex = 1;
            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO workout_db(date, progr, squat_weight, squat_reps, bench_weigth, bench_reps,\n"
                    + "press_weight, press_reps, deadlift_weight, deadlift_reps) VALUES (");
            if (date == null) {
                query.append("null");
            } else {
                query.append('?').append(currentIndex++);
                args.add(date);
            }
            query.append(", ");
            if (progr == null) {
                query.append("null");
            } else {
                query.append(progr);
            }
            query.append(", ");
            if (squat_weight == null) {
                query.append("null");
            } else {
                query.append(squat_weight);
            }
            query.append(", ");
            if (squat_reps == null) {
                query.append("null");
            } else {
                query.append(squat_reps);
            }
            query.append(", ");
            if (bench_weigth == null) {
                query.append("null");
            } else {
                query.append(bench_weigth);
            }
            query.append(", ");
            if (bench_reps == null) {
                query.append("null");
            } else {
                query.append(bench_reps);
            }
            query.append(", ");
            if (press_weight == null) {
                query.append("null");
            } else {
                query.append(press_weight);
            }
            query.append(", ");
            if (press_reps == null) {
                query.append("null");
            } else {
                query.append(press_reps);
            }
            query.append(", ");
            if (deadlift_weight == null) {
                query.append("null");
            } else {
                query.append(deadlift_weight);
            }
            query.append(", ");
            if (deadlift_reps == null) {
                query.append("null");
            } else {
                query.append(deadlift_reps);
            }
            query.append(")");
            return new SqlDelightStatement(query.toString(), args.toArray(new String[args.size()]), Collections.<String>singleton("workout_db"));
        }

        /**
         * @deprecated Use {@link DeleteWorkoutByDate}
         */
        @Deprecated
        public SqlDelightStatement DeleteWorkoutByDate(@Nullable String date) {
            List<String> args = new ArrayList<String>();
            int currentIndex = 1;
            StringBuilder query = new StringBuilder();
            query.append("DELETE FROM workout_db WHERE date = ");
            if (date == null) {
                query.append("null");
            } else {
                query.append('?').append(currentIndex++);
                args.add(date);
            }
            return new SqlDelightStatement(query.toString(), args.toArray(new String[args.size()]), Collections.<String>singleton("workout_db"));
        }

        /**
         * @deprecated Use {@link DeleteWorkoutById}
         */
        @Deprecated
        public SqlDelightStatement DeleteWorkoutById(long id) {
            List<String> args = new ArrayList<String>();
            int currentIndex = 1;
            StringBuilder query = new StringBuilder();
            query.append("DELETE FROM workout_db WHERE id = ");
            query.append(id);
            return new SqlDelightStatement(query.toString(), args.toArray(new String[args.size()]), Collections.<String>singleton("workout_db"));
        }

        public SqlDelightStatement FindIdByDate(@Nullable String date) {
            List<String> args = new ArrayList<String>();
            int currentIndex = 1;
            StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM workout_db WHERE date = ");
            if (date == null) {
                query.append("null");
            } else {
                query.append('?').append(currentIndex++);
                args.add(date);
            }
            return new SqlDelightStatement(query.toString(), args.toArray(new String[args.size()]), Collections.<String>singleton("workout_db"));
        }

        public SqlDelightStatement FindDateById(long id) {
            List<String> args = new ArrayList<String>();
            int currentIndex = 1;
            StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM workout_db WHERE id = ");
            query.append(id);
            return new SqlDelightStatement(query.toString(), args.toArray(new String[args.size()]), Collections.<String>singleton("workout_db"));
        }

        public SqlDelightStatement FindDateByProgram(@Nullable Long progr) {
            List<String> args = new ArrayList<String>();
            int currentIndex = 1;
            StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM workout_db WHERE progr = ");
            if (progr == null) {
                query.append("null");
            } else {
                query.append(progr);
            }
            return new SqlDelightStatement(query.toString(), args.toArray(new String[args.size()]), Collections.<String>singleton("workout_db"));
        }

        public Mapper<T> selectAllMapper() {
            return new Mapper<T>(this);
        }

        public Mapper<T> selectWorkoutByDateMapper() {
            return new Mapper<T>(this);
        }

        public Mapper<T> selectWorkoutByIdMapper() {
            return new Mapper<T>(this);
        }

        public Mapper<T> selectWorkoutByProgramMapper() {
            return new Mapper<T>(this);
        }

        public Mapper<T> findIdByDateMapper() {
            return new Mapper<T>(this);
        }

        public Mapper<T> findDateByIdMapper() {
            return new Mapper<T>(this);
        }

        public Mapper<T> findDateByProgramMapper() {
            return new Mapper<T>(this);
        }
    }

    final class InsertWorkout extends SqlDelightCompiledStatement.Insert {
        public InsertWorkout(SQLiteDatabase database) {
            super("workout_db", database.compileStatement(""
                    + "INSERT INTO workout_db(date, progr, squat_weight, squat_reps, bench_weigth, bench_reps,\n"
                    + "press_weight, press_reps, deadlift_weight, deadlift_reps) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"));
        }

        public void bind(@Nullable String date, @Nullable Long progr, @Nullable Long squat_weight, @Nullable Long squat_reps, @Nullable Long bench_weigth, @Nullable Long bench_reps, @Nullable Long press_weight, @Nullable Long press_reps, @Nullable Long deadlift_weight, @Nullable Long deadlift_reps) {
            if (date == null) {
                program.bindNull(1);
            } else {
                program.bindString(1, date);
            }
            if (progr == null) {
                program.bindNull(2);
            } else {
                program.bindLong(2, progr);
            }
            if (squat_weight == null) {
                program.bindNull(3);
            } else {
                program.bindLong(3, squat_weight);
            }
            if (squat_reps == null) {
                program.bindNull(4);
            } else {
                program.bindLong(4, squat_reps);
            }
            if (bench_weigth == null) {
                program.bindNull(5);
            } else {
                program.bindLong(5, bench_weigth);
            }
            if (bench_reps == null) {
                program.bindNull(6);
            } else {
                program.bindLong(6, bench_reps);
            }
            if (press_weight == null) {
                program.bindNull(7);
            } else {
                program.bindLong(7, press_weight);
            }
            if (press_reps == null) {
                program.bindNull(8);
            } else {
                program.bindLong(8, press_reps);
            }
            if (deadlift_weight == null) {
                program.bindNull(9);
            } else {
                program.bindLong(9, deadlift_weight);
            }
            if (deadlift_reps == null) {
                program.bindNull(10);
            } else {
                program.bindLong(10, deadlift_reps);
            }
        }
    }

    final class DeleteWorkoutByDate extends SqlDelightCompiledStatement.Delete {
        public DeleteWorkoutByDate(SQLiteDatabase database) {
            super("workout_db", database.compileStatement(""
                    + "DELETE FROM workout_db WHERE date = ?"));
        }

        public void bind(@Nullable String date) {
            if (date == null) {
                program.bindNull(1);
            } else {
                program.bindString(1, date);
            }
        }
    }

    final class DeleteWorkoutById extends SqlDelightCompiledStatement.Delete {
        public DeleteWorkoutById(SQLiteDatabase database) {
            super("workout_db", database.compileStatement(""
                    + "DELETE FROM workout_db WHERE id = ?"));
        }

        public void bind(long id) {
            program.bindLong(1, id);
        }
    }
}
