package com.example.trafficquiz.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.trafficquiz.model.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "TrafficQuiz.db";
    private static final int DATABASE_VERSION = 2;
    private final Context context;
    private static final String[] SQL_FILES = {
        "questions.sql",
        "questions_final.sql",
        "questions_final_part.sql",
        "questions_part2.sql",
        "questions_part3.sql"
    };

    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables
        db.execSQL("CREATE TABLE IF NOT EXISTS questions (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "question_text TEXT NOT NULL, " +
                "option1 TEXT NOT NULL, " +
                "option2 TEXT NOT NULL, " +
                "option3 TEXT NOT NULL, " +
                "option4 TEXT NOT NULL, " +
                "answer_nr INTEGER NOT NULL, " +
                "category TEXT, " +
                "difficulty TEXT, " +
                "image TEXT, " +
                "explanation TEXT" +
                ");");

        // Load initial questions from SQL file
        loadQuestionsFromFile(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS questions");
        onCreate(db);
    }

    private void loadQuestionsFromFile(SQLiteDatabase db) {
        try {
            db.beginTransaction();
            try {
                // Load questions from all SQL files
                for (String sqlFile : SQL_FILES) {
                    BufferedReader reader = new BufferedReader(
                        new InputStreamReader(context.getAssets().open(sqlFile))
                    );

                    String line;
                    StringBuilder statement = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        if (line.trim().startsWith("--") || line.trim().isEmpty()) {
                            continue;
                        }
                        statement.append(line);
                        if (line.trim().endsWith(";")) {
                            String sql = statement.toString();
                            if (sql.toUpperCase().trim().startsWith("INSERT")) {
                                db.execSQL(sql);
                            }
                            statement = new StringBuilder();
                        }
                    }
                    reader.close();
                }

                // Load and execute image updates
                BufferedReader reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("update_images.sql"))
                );

                String line;
                StringBuilder statement = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    if (line.trim().startsWith("--") || line.trim().isEmpty()) {
                        continue;
                    }
                    statement.append(line);
                    if (line.trim().endsWith(";")) {
                        String sql = statement.toString();
                        if (sql.toUpperCase().trim().startsWith("UPDATE")) {
                            db.execSQL(sql);
                        }
                        statement = new StringBuilder();
                    }
                }
                reader.close();

                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Question> getRandomQuestions(int limit) {
        List<Question> questionList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM questions ORDER BY RANDOM() LIMIT " + limit, null);
        if (cursor.moveToFirst()) {
            do {
                Question q = new Question();
                q.setId(cursor.getInt(0));
                q.setQuestion(cursor.getString(1));
                q.setOptionA(cursor.getString(2));
                q.setOptionB(cursor.getString(3));
                q.setOptionC(cursor.getString(4));
                q.setOptionD(cursor.getString(5));
                q.setCorrectAnswer(cursor.getInt(6));

                // Handle optional columns safely
                if (!cursor.isNull(7)) {
                    q.setCategory(cursor.getString(7));
                }
                if (!cursor.isNull(8)) {
                    q.setDifficulty(cursor.getString(8));
                }
                if (!cursor.isNull(9)) {
                    q.setImage(cursor.getString(9));
                }
                if (!cursor.isNull(10)) {
                    q.setExplanation(cursor.getString(10));
                }

                questionList.add(q);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return questionList;
    }

    public List<Question> getQuestionsByCategory(String category, int limit) {
        List<Question> questionList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = "category = ?";
        String[] selectionArgs = {category};
        Cursor cursor = db.query("questions", null, selection, selectionArgs, null, null, "RANDOM()", String.valueOf(limit));

        if (cursor.moveToFirst()) {
            do {
                Question q = new Question();
                q.setId(cursor.getInt(0));
                q.setQuestion(cursor.getString(1));
                q.setOptionA(cursor.getString(2));
                q.setOptionB(cursor.getString(3));
                q.setOptionC(cursor.getString(4));
                q.setOptionD(cursor.getString(5));
                q.setCorrectAnswer(cursor.getInt(6));

                // Handle optional columns safely
                if (!cursor.isNull(7)) {
                    q.setCategory(cursor.getString(7));
                }
                if (!cursor.isNull(8)) {
                    q.setDifficulty(cursor.getString(8));
                }
                if (!cursor.isNull(9)) {
                    q.setImage(cursor.getString(9));
                }
                if (!cursor.isNull(10)) {
                    q.setExplanation(cursor.getString(10));
                }

                questionList.add(q);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return questionList;
    }
}
