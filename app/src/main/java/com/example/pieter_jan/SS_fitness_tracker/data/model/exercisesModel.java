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
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface exercisesModel {
    String TABLE_NAME = "exercises";

    String NAME = "name";

    String NOTE = "note";

    String CREATE_TABLE = ""
            + "CREATE TABLE exercises (\n"
            + "    name TEXT PRIMARY KEY,\n"
            + "    note TEXT\n"
            + ")";

    String SELECTALL = ""
            + "SELECT * FROM exercises";

    String SELECTEXERCISEBYNAME = ""
            + "SELECT * FROM exercises WHERE name = ?";

    @Nullable
    String name();

    @Nullable
    String note();

    interface Creator<T extends exercisesModel> {
        T create(@Nullable String name, @Nullable String note);
    }

    final class Mapper<T extends exercisesModel> implements RowMapper<T> {
        private final Factory<T> exercisesModelFactory;

        public Mapper(Factory<T> exercisesModelFactory) {
            this.exercisesModelFactory = exercisesModelFactory;
        }

        @Override
        public T map(@NonNull Cursor cursor) {
            return exercisesModelFactory.creator.create(
                    cursor.isNull(0) ? null : cursor.getString(0),
                    cursor.isNull(1) ? null : cursor.getString(1)
            );
        }
    }

    final class Marshal {
        protected final ContentValues contentValues = new ContentValues();

        Marshal(@Nullable exercisesModel copy) {
            if (copy != null) {
                this.name(copy.name());
                this.note(copy.note());
            }
        }

        public ContentValues asContentValues() {
            return contentValues;
        }

        public Marshal name(String name) {
            contentValues.put("name", name);
            return this;
        }

        public Marshal note(String note) {
            contentValues.put("note", note);
            return this;
        }
    }

    final class Factory<T extends exercisesModel> {
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
        public Marshal marshal(exercisesModel copy) {
            return new Marshal(copy);
        }

        public SqlDelightStatement SelectExerciseByName(@Nullable String name) {
            List<String> args = new ArrayList<String>();
            int currentIndex = 1;
            StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM exercises WHERE name = ");
            if (name == null) {
                query.append("null");
            } else {
                query.append('?').append(currentIndex++);
                args.add(name);
            }
            return new SqlDelightStatement(query.toString(), args.toArray(new String[args.size()]), Collections.<String>singleton("exercises"));
        }

        /**
         * @deprecated Use {@link InsertExercise}
         */
        @Deprecated
        public SqlDelightStatement InsertExercise(@Nullable String name, @Nullable String note) {
            List<String> args = new ArrayList<String>();
            int currentIndex = 1;
            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO exercises(name, note) VALUES (");
            if (name == null) {
                query.append("null");
            } else {
                query.append('?').append(currentIndex++);
                args.add(name);
            }
            query.append(", ");
            if (note == null) {
                query.append("null");
            } else {
                query.append('?').append(currentIndex++);
                args.add(note);
            }
            query.append(")");
            return new SqlDelightStatement(query.toString(), args.toArray(new String[args.size()]), Collections.<String>singleton("exercises"));
        }

        /**
         * @deprecated Use {@link DeleteExercise}
         */
        @Deprecated
        public SqlDelightStatement DeleteExercise(@Nullable String name) {
            List<String> args = new ArrayList<String>();
            int currentIndex = 1;
            StringBuilder query = new StringBuilder();
            query.append("DELETE FROM exercises WHERE name = ");
            if (name == null) {
                query.append("null");
            } else {
                query.append('?').append(currentIndex++);
                args.add(name);
            }
            return new SqlDelightStatement(query.toString(), args.toArray(new String[args.size()]), Collections.<String>singleton("exercises"));
        }

        public Mapper<T> selectAllMapper() {
            return new Mapper<T>(this);
        }

        public Mapper<T> selectExerciseByNameMapper() {
            return new Mapper<T>(this);
        }
    }

    final class InsertExercise extends SqlDelightCompiledStatement.Insert {
        public InsertExercise(SQLiteDatabase database) {
            super("exercises", database.compileStatement(""
                    + "INSERT INTO exercises(name, note) VALUES (?, ?)"));
        }

        public void bind(@Nullable String name, @Nullable String note) {
            if (name == null) {
                program.bindNull(1);
            } else {
                program.bindString(1, name);
            }
            if (note == null) {
                program.bindNull(2);
            } else {
                program.bindString(2, note);
            }
        }
    }

    final class DeleteExercise extends SqlDelightCompiledStatement.Delete {
        public DeleteExercise(SQLiteDatabase database) {
            super("exercises", database.compileStatement(""
                    + "DELETE FROM exercises WHERE name = ?"));
        }

        public void bind(@Nullable String name) {
            if (name == null) {
                program.bindNull(1);
            } else {
                program.bindString(1, name);
            }
        }
    }
}
