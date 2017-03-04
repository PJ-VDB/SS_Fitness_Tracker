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
import java.lang.Double;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface sets_logModel {
    String TABLE_NAME = "sets_log";

    String ID = "id";

    String DATE = "date";

    String REPS = "reps";

    String WEIGHT = "weight";

    String NOTE = "note";

    String EXERCISE_LOG_ID = "exercise_log_id";

    String EXERCISE_NAME = "exercise_name";

    String CREATE_TABLE = ""
            + "CREATE TABLE sets_log(\n"
            + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + "    date TEXT,\n"
            + "    reps INTEGER,\n"
            + "    weight REAL,\n"
            + "    note TEXT,\n"
            + "    exercise_log_id INTEGER,\n"
            + "    exercise_name INTEGER,\n"
            + "    FOREIGN KEY(exercise_log_id) REFERENCES exercise_log(id),\n"
            + "    FOREIGN KEY(exercise_name) REFERENCES exercises(name)\n"
            + "    )";

    String SELECTALL = ""
            + "SELECT * FROM sets_log";

    String SELECTSETSBYID = ""
            + "SELECT * FROM sets_log WHERE id = ?";

    String SELECTSETSBYDATE = ""
            + "SELECT * FROM sets_log WHERE date = ?";

    String SELECTSETSBYEXERCISEID = ""
            + "SELECT * FROM sets_log WHERE exercise_log_id = ?";

    String SELECTSETSBYEXERCISENAME = ""
            + "SELECT * FROM sets_log WHERE exercise_name = ?";

    @Nullable
    Long id();

    @Nullable
    String date();

    @Nullable
    Long reps();

    @Nullable
    Double weight();

    @Nullable
    String note();

    @Nullable
    Long exercise_log_id();

    @Nullable
    Long exercise_name();

    interface Creator<T extends sets_logModel> {
        T create(@Nullable Long id, @Nullable String date, @Nullable Long reps, @Nullable Double weight, @Nullable String note, @Nullable Long exercise_log_id, @Nullable Long exercise_name);
    }

    final class Mapper<T extends sets_logModel> implements RowMapper<T> {
        private final Factory<T> sets_logModelFactory;

        public Mapper(Factory<T> sets_logModelFactory) {
            this.sets_logModelFactory = sets_logModelFactory;
        }

        @Override
        public T map(@NonNull Cursor cursor) {
            return sets_logModelFactory.creator.create(
                    cursor.isNull(0) ? null : cursor.getLong(0),
                    cursor.isNull(1) ? null : cursor.getString(1),
                    cursor.isNull(2) ? null : cursor.getLong(2),
                    cursor.isNull(3) ? null : cursor.getDouble(3),
                    cursor.isNull(4) ? null : cursor.getString(4),
                    cursor.isNull(5) ? null : cursor.getLong(5),
                    cursor.isNull(6) ? null : cursor.getLong(6)
            );
        }
    }

    final class Marshal {
        protected final ContentValues contentValues = new ContentValues();

        Marshal(@Nullable sets_logModel copy) {
            if (copy != null) {
                this.id(copy.id());
                this.date(copy.date());
                this.reps(copy.reps());
                this.weight(copy.weight());
                this.note(copy.note());
                this.exercise_log_id(copy.exercise_log_id());
                this.exercise_name(copy.exercise_name());
            }
        }

        public ContentValues asContentValues() {
            return contentValues;
        }

        public Marshal id(Long id) {
            contentValues.put("id", id);
            return this;
        }

        public Marshal date(String date) {
            contentValues.put("date", date);
            return this;
        }

        public Marshal reps(Long reps) {
            contentValues.put("reps", reps);
            return this;
        }

        public Marshal weight(Double weight) {
            contentValues.put("weight", weight);
            return this;
        }

        public Marshal note(String note) {
            contentValues.put("note", note);
            return this;
        }

        public Marshal exercise_log_id(Long exercise_log_id) {
            contentValues.put("exercise_log_id", exercise_log_id);
            return this;
        }

        public Marshal exercise_name(Long exercise_name) {
            contentValues.put("exercise_name", exercise_name);
            return this;
        }
    }

    final class Factory<T extends sets_logModel> {
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
        public Marshal marshal(sets_logModel copy) {
            return new Marshal(copy);
        }

        /**
         * @deprecated Use {@link InsertSet}
         */
        @Deprecated
        public SqlDelightStatement InsertSet(@Nullable String date, @Nullable Long reps, @Nullable Double weight, @Nullable String note, @Nullable Long exercise_log_id, @Nullable Long exercise_name) {
            List<String> args = new ArrayList<String>();
            int currentIndex = 1;
            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO sets_log(date, reps, weight, note, exercise_log_id, exercise_name) VALUES (");
            if (date == null) {
                query.append("null");
            } else {
                query.append('?').append(currentIndex++);
                args.add(date);
            }
            query.append(", ");
            if (reps == null) {
                query.append("null");
            } else {
                query.append(reps);
            }
            query.append(", ");
            if (weight == null) {
                query.append("null");
            } else {
                query.append(weight);
            }
            query.append(", ");
            if (note == null) {
                query.append("null");
            } else {
                query.append('?').append(currentIndex++);
                args.add(note);
            }
            query.append(", ");
            if (exercise_log_id == null) {
                query.append("null");
            } else {
                query.append(exercise_log_id);
            }
            query.append(", ");
            if (exercise_name == null) {
                query.append("null");
            } else {
                query.append(exercise_name);
            }
            query.append(")");
            return new SqlDelightStatement(query.toString(), args.toArray(new String[args.size()]), Collections.<String>singleton("sets_log"));
        }

        public SqlDelightStatement SelectSetsById(@Nullable Long id) {
            List<String> args = new ArrayList<String>();
            int currentIndex = 1;
            StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM sets_log WHERE id = ");
            if (id == null) {
                query.append("null");
            } else {
                query.append(id);
            }
            return new SqlDelightStatement(query.toString(), args.toArray(new String[args.size()]), Collections.<String>singleton("sets_log"));
        }

        public SqlDelightStatement SelectSetsByDate(@Nullable String date) {
            List<String> args = new ArrayList<String>();
            int currentIndex = 1;
            StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM sets_log WHERE date = ");
            if (date == null) {
                query.append("null");
            } else {
                query.append('?').append(currentIndex++);
                args.add(date);
            }
            return new SqlDelightStatement(query.toString(), args.toArray(new String[args.size()]), Collections.<String>singleton("sets_log"));
        }

        public SqlDelightStatement SelectSetsByExerciseId(@Nullable Long exercise_log_id) {
            List<String> args = new ArrayList<String>();
            int currentIndex = 1;
            StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM sets_log WHERE exercise_log_id = ");
            if (exercise_log_id == null) {
                query.append("null");
            } else {
                query.append(exercise_log_id);
            }
            return new SqlDelightStatement(query.toString(), args.toArray(new String[args.size()]), Collections.<String>singleton("sets_log"));
        }

        public SqlDelightStatement SelectSetsByExerciseName(@Nullable Long exercise_name) {
            List<String> args = new ArrayList<String>();
            int currentIndex = 1;
            StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM sets_log WHERE exercise_name = ");
            if (exercise_name == null) {
                query.append("null");
            } else {
                query.append(exercise_name);
            }
            return new SqlDelightStatement(query.toString(), args.toArray(new String[args.size()]), Collections.<String>singleton("sets_log"));
        }

        public Mapper<T> selectAllMapper() {
            return new Mapper<T>(this);
        }

        public Mapper<T> selectSetsByIdMapper() {
            return new Mapper<T>(this);
        }

        public Mapper<T> selectSetsByDateMapper() {
            return new Mapper<T>(this);
        }

        public Mapper<T> selectSetsByExerciseIdMapper() {
            return new Mapper<T>(this);
        }

        public Mapper<T> selectSetsByExerciseNameMapper() {
            return new Mapper<T>(this);
        }
    }

    final class InsertSet extends SqlDelightCompiledStatement.Insert {
        public InsertSet(SQLiteDatabase database) {
            super("sets_log", database.compileStatement(""
                    + "INSERT INTO sets_log(date, reps, weight, note, exercise_log_id, exercise_name) VALUES (?, ?, ?, ?, ?, ?)"));
        }

        public void bind(@Nullable String date, @Nullable Long reps, @Nullable Double weight, @Nullable String note, @Nullable Long exercise_log_id, @Nullable Long exercise_name) {
            if (date == null) {
                program.bindNull(1);
            } else {
                program.bindString(1, date);
            }
            if (reps == null) {
                program.bindNull(2);
            } else {
                program.bindLong(2, reps);
            }
            if (weight == null) {
                program.bindNull(3);
            } else {
                program.bindDouble(3, weight);
            }
            if (note == null) {
                program.bindNull(4);
            } else {
                program.bindString(4, note);
            }
            if (exercise_log_id == null) {
                program.bindNull(5);
            } else {
                program.bindLong(5, exercise_log_id);
            }
            if (exercise_name == null) {
                program.bindNull(6);
            } else {
                program.bindLong(6, exercise_name);
            }
        }
    }
}
