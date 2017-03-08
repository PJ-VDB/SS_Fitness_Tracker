package com.example.pieter_jan.SS_fitness_tracker.data.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.squareup.sqldelight.RowMapper;
import com.squareup.sqldelight.SqlDelightCompiledStatement;
import com.squareup.sqldelight.SqlDelightStatement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface exercise_logModel {
    String TABLE_NAME = "exercise_log";

    String ID = "id";

    String EXERCISE_NAME = "exercise_name";

    String DATE = "date";

    String NOTE = "note";

    String WEIGHT = "weight";

    String SUCCEEDED = "succeeded";

    String CREATE_TABLE = ""
            + "CREATE TABLE exercise_log (\n"
            + "    id  INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + "                exercise_name TEXT,\n"
            + "                date   TEXT,\n"
            + "                note TEXT,\n"
            + "                weight REAL,\n"
            + "                succeeded INTEGER,\n"
            + "    FOREIGN KEY(exercise_name) REFERENCES exercises(name)\n"
            + ")";

    String SELECTALL = ""
            + "SELECT * FROM exercise_log";

    String SELECTEXERCISEBYDATEFROMLOG = ""
            + "SELECT * FROM exercise_log WHERE date = ?";

    String SELECTEXERCISEBYIDFROMLOG = ""
            + "SELECT * FROM exercise_log WHERE id = ?";

    String SELECTEXERCISEBYNAMEFROMLOG = ""
            + "SELECT * FROM exercise_log WHERE exercise_name = ?";

    String SELECTMOSTRECENTOFEACHEXERCISE = ""
    + "SELECT id,\n"
    + "exercise_name,\n"
    + "max(date) as date,\n"
    + "note, \n"
    + "weight,\n"
    + "succeeded FROM exercise_log WHERE date < ? GROUP BY exercise_name";


//    String SELECTMOSTRECENTOFEACHEXERCISE = ""
//            + "Select id,\n"
//            + "u.[exercise_name],\n"
//            + "q.[date],\n"
//            + "note,\n"
//            + "weight,\n"
//            + "succeeded,\n"
//            + "From [exercise_log] As u\n"
//            + "Inner Join (\n"
//            + "Select [exercise_name],\n"
//            + "max(date) as [date]\n"
//            + "From [exercise_log]\n"
//            + "Group By [exercise_name]) As [q]\n"
//            + "On u.exercise_name = q.exercise_name\n"
//            + "And u.date = q.date";

    String SELECTEXERCISEBYNAMEFROMLOGANDSORT = ""
            + "SELECT * FROM exercise_log WHERE exercise_name = ? AND date < ? ORDER BY date";

    @Nullable
    Long id();

    @Nullable
    String exercise_name();

    @Nullable
    String date();

    @Nullable
    String note();

    @Nullable
    Double weight();

    @Nullable
    Long succeeded();

    interface Creator<T extends exercise_logModel> {
        T create(@Nullable Long id, @Nullable String exercise_name, @Nullable String date, @Nullable String note, @Nullable Double weight, @Nullable Long succeeded);
    }

    final class Mapper<T extends exercise_logModel> implements RowMapper<T> {
        private final Factory<T> exercise_logModelFactory;

        public Mapper(Factory<T> exercise_logModelFactory) {
            this.exercise_logModelFactory = exercise_logModelFactory;
        }

        @Override
        public T map(@NonNull Cursor cursor) {
            return exercise_logModelFactory.creator.create(
                    cursor.isNull(0) ? null : cursor.getLong(0),
                    cursor.isNull(1) ? null : cursor.getString(1),
                    cursor.isNull(2) ? null : cursor.getString(2),
                    cursor.isNull(3) ? null : cursor.getString(3),
                    cursor.isNull(4) ? null : cursor.getDouble(4),
                    cursor.isNull(5) ? null : cursor.getLong(5)
            );
        }
    }

    final class Marshal {
        protected final ContentValues contentValues = new ContentValues();

        Marshal(@Nullable exercise_logModel copy) {
            if (copy != null) {
                this.id(copy.id());
                this.exercise_name(copy.exercise_name());
                this.date(copy.date());
                this.note(copy.note());
                this.weight(copy.weight());
                this.succeeded(copy.succeeded());
            }
        }

        public ContentValues asContentValues() {
            return contentValues;
        }

        public Marshal id(Long id) {
            contentValues.put("id", id);
            return this;
        }

        public Marshal exercise_name(String exercise_name) {
            contentValues.put("exercise_name", exercise_name);
            return this;
        }

        public Marshal date(String date) {
            contentValues.put("date", date);
            return this;
        }

        public Marshal note(String note) {
            contentValues.put("note", note);
            return this;
        }

        public Marshal weight(Double weight) {
            contentValues.put("weight", weight);
            return this;
        }

        public Marshal succeeded(Long succeeded) {
            contentValues.put("succeeded", succeeded);
            return this;
        }
    }

    final class Factory<T extends exercise_logModel> {
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
        public Marshal marshal(exercise_logModel copy) {
            return new Marshal(copy);
        }

        /**
         * @deprecated Use {@link InsertExerciseInLog}
         */
        @Deprecated
        public SqlDelightStatement InsertExerciseInLog(@Nullable String exercise_name, @Nullable String date, @Nullable String note, @Nullable Double weight, @Nullable Long succeeded) {
            List<String> args = new ArrayList<String>();
            int currentIndex = 1;
            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO exercise_log(exercise_name, date, note, weight, succeeded) VALUES (");
            if (exercise_name == null) {
                query.append("null");
            } else {
                query.append('?').append(currentIndex++);
                args.add(exercise_name);
            }
            query.append(", ");
            if (date == null) {
                query.append("null");
            } else {
                query.append('?').append(currentIndex++);
                args.add(date);
            }
            query.append(", ");
            if (note == null) {
                query.append("null");
            } else {
                query.append('?').append(currentIndex++);
                args.add(note);
            }
            query.append(", ");
            if (weight == null) {
                query.append("null");
            } else {
                query.append(weight);
            }
            query.append(", ");
            if (succeeded == null) {
                query.append("null");
            } else {
                query.append(succeeded);
            }
            query.append(")");
            return new SqlDelightStatement(query.toString(), args.toArray(new String[args.size()]), Collections.<String>singleton("exercise_log"));
        }

        public SqlDelightStatement SelectExerciseByDateFromLog(@Nullable String date) {
            List<String> args = new ArrayList<String>();
            int currentIndex = 1;
            StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM exercise_log WHERE date = ");
            if (date == null) {
                query.append("null");
            } else {
                query.append('?').append(currentIndex++);
                args.add(date);
            }
            return new SqlDelightStatement(query.toString(), args.toArray(new String[args.size()]), Collections.<String>singleton("exercise_log"));
        }

        public SqlDelightStatement SelectExerciseByIdFromLog(@Nullable Long id) {
            List<String> args = new ArrayList<String>();
            int currentIndex = 1;
            StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM exercise_log WHERE id = ");
            if (id == null) {
                query.append("null");
            } else {
                query.append(id);
            }
            return new SqlDelightStatement(query.toString(), args.toArray(new String[args.size()]), Collections.<String>singleton("exercise_log"));
        }

        public SqlDelightStatement SelectExerciseByNameFromLog(@Nullable String exercise_name) {
            List<String> args = new ArrayList<String>();
            int currentIndex = 1;
            StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM exercise_log WHERE exercise_name = ");
            if (exercise_name == null) {
                query.append("null");
            } else {
                query.append('?').append(currentIndex++);
                args.add(exercise_name);
            }
            return new SqlDelightStatement(query.toString(), args.toArray(new String[args.size()]), Collections.<String>singleton("exercise_log"));
        }

        /**
         * @deprecated Use {@link DeleteWorkoutByDate}
         */
        @Deprecated
        public SqlDelightStatement DeleteWorkoutByDate(@Nullable String date) {
            List<String> args = new ArrayList<String>();
            int currentIndex = 1;
            StringBuilder query = new StringBuilder();
            query.append("DELETE FROM exercise_log WHERE date = ");
            if (date == null) {
                query.append("null");
            } else {
                query.append('?').append(currentIndex++);
                args.add(date);
            }
            return new SqlDelightStatement(query.toString(), args.toArray(new String[args.size()]), Collections.<String>singleton("exercise_log"));
        }

        /**
         * @deprecated Use {@link DeleteWorkoutById}
         */
        @Deprecated
        public SqlDelightStatement DeleteWorkoutById(long id) {
            List<String> args = new ArrayList<String>();
            int currentIndex = 1;
            StringBuilder query = new StringBuilder();
            query.append("DELETE FROM exercise_log WHERE id = ");
            query.append(id);
            return new SqlDelightStatement(query.toString(), args.toArray(new String[args.size()]), Collections.<String>singleton("exercise_log"));
        }

        /**
         * @deprecated Use {@link UpdateWorkoutWeightById}
         */
        @Deprecated
        public SqlDelightStatement UpdateWorkoutWeightById(@Nullable Double weight, @Nullable Long id) {
            List<String> args = new ArrayList<String>();
            int currentIndex = 1;
            StringBuilder query = new StringBuilder();
            query.append("UPDATE exercise_log\n"
                    + "SET weight = ");
            if (weight == null) {
                query.append("null");
            } else {
                query.append(weight);
            }
            query.append("\n"
                    + "WHERE id = ");
            if (id == null) {
                query.append("null");
            } else {
                query.append(id);
            }
            return new SqlDelightStatement(query.toString(), args.toArray(new String[args.size()]), Collections.<String>singleton("exercise_log"));
        }

        /**
         * @deprecated Use {@link UpdateWorkoutStatusById}
         */
        @Deprecated
        public SqlDelightStatement UpdateWorkoutStatusById(@Nullable Long succeeded, @Nullable Long id) {
            List<String> args = new ArrayList<String>();
            int currentIndex = 1;
            StringBuilder query = new StringBuilder();
            query.append("UPDATE exercise_log\n"
                    + "SET succeeded = ");
            if (succeeded == null) {
                query.append("null");
            } else {
                query.append(succeeded);
            }
            query.append("\n"
                    + "WHERE id = ");
            if (id == null) {
                query.append("null");
            } else {
                query.append(id);
            }
            return new SqlDelightStatement(query.toString(), args.toArray(new String[args.size()]), Collections.<String>singleton("exercise_log"));
        }

        public Mapper<T> selectAllMapper() {
            return new Mapper<T>(this);
        }

        public Mapper<T> selectExerciseByDateFromLogMapper() {
            return new Mapper<T>(this);
        }

        public Mapper<T> selectExerciseByIdFromLogMapper() {
            return new Mapper<T>(this);
        }

        public Mapper<T> selectExerciseByNameFromLogMapper() {
            return new Mapper<T>(this);
        }
    }

    final class InsertExerciseInLog extends SqlDelightCompiledStatement.Insert {
        public InsertExerciseInLog(SQLiteDatabase database) {
            super("exercise_log", database.compileStatement(""
                    + "INSERT INTO exercise_log(exercise_name, date, note, weight, succeeded) VALUES (?, ?, ?, ?, ?)"));
        }

        public void bind(@Nullable String exercise_name, @Nullable String date, @Nullable String note, @Nullable Double weight, @Nullable Long succeeded) {
            if (exercise_name == null) {
                program.bindNull(1);
            } else {
                program.bindString(1, exercise_name);
            }
            if (date == null) {
                program.bindNull(2);
            } else {
                program.bindString(2, date);
            }
            if (note == null) {
                program.bindNull(3);
            } else {
                program.bindString(3, note);
            }
            if (weight == null) {
                program.bindNull(4);
            } else {
                program.bindDouble(4, weight);
            }
            if (succeeded == null) {
                program.bindNull(5);
            } else {
                program.bindLong(5, succeeded);
            }
        }
    }

    final class DeleteWorkoutByDate extends SqlDelightCompiledStatement.Delete {
        public DeleteWorkoutByDate(SQLiteDatabase database) {
            super("exercise_log", database.compileStatement(""
                    + "DELETE FROM exercise_log WHERE date = ?"));
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
            super("exercise_log", database.compileStatement(""
                    + "DELETE FROM exercise_log WHERE id = ?"));
        }

        public void bind(long id) {
            program.bindLong(1, id);
        }
    }

    final class UpdateWorkoutWeightById extends SqlDelightCompiledStatement.Update {
        public UpdateWorkoutWeightById(SQLiteDatabase database) {
            super("exercise_log", database.compileStatement(""
                    + "UPDATE exercise_log\n"
                    + "SET weight = ?\n"
                    + "WHERE id = ?"));
        }

        public void bind(@Nullable Double weight, @Nullable Long id) {
            if (weight == null) {
                program.bindNull(1);
            } else {
                program.bindDouble(1, weight);
            }
            if (id == null) {
                program.bindNull(2);
            } else {
                program.bindLong(2, id);
            }
        }
    }

    final class UpdateWorkoutStatusById extends SqlDelightCompiledStatement.Update {
        public UpdateWorkoutStatusById(SQLiteDatabase database) {
            super("exercise_log", database.compileStatement(""
                    + "UPDATE exercise_log\n"
                    + "SET succeeded = ?\n"
                    + "WHERE id = ?"));
        }

        public void bind(@Nullable Long succeeded, @Nullable Long id) {
            if (succeeded == null) {
                program.bindNull(1);
            } else {
                program.bindLong(1, succeeded);
            }
            if (id == null) {
                program.bindNull(2);
            } else {
                program.bindLong(2, id);
            }
        }
    }
}

