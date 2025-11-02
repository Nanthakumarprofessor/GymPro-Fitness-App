package com.gympro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "GymPro.db";
    private static final int DATABASE_VERSION = 1;
    
    // Workout sessions table
    private static final String TABLE_SESSIONS = "workout_sessions";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EXERCISE_TYPE = "exercise_type";
    private static final String COLUMN_START_TIME = "start_time";
    private static final String COLUMN_DURATION = "duration";
    private static final String COLUMN_AVG_SCORE = "average_score";
    
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSessionTable = "CREATE TABLE " + TABLE_SESSIONS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_EXERCISE_TYPE + " TEXT,"
                + COLUMN_START_TIME + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
                + COLUMN_DURATION + " INTEGER,"
                + COLUMN_AVG_SCORE + " REAL)";
        db.execSQL(createSessionTable);
    }
    
    public long addWorkoutSession(String exerciseType, int duration, double avgScore) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EXERCISE_TYPE, exerciseType);
        values.put(COLUMN_DURATION, duration);
        values.put(COLUMN_AVG_SCORE, avgScore);
        
        return db.insert(TABLE_SESSIONS, null, values);
    }
    
    public Cursor getAllSessions() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_SESSIONS, null, null, null, null, null, COLUMN_START_TIME + " DESC");
    }
}
