package com.example.trafficquiz.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.trafficquiz.model.Question;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class QuestionDataLoader {

    private final SQLiteDatabase db;

    public QuestionDataLoader(Context context) {
        File dbPath = context.getDatabasePath("question.db"); // đổi tên cho đúng file
        db = SQLiteDatabase.openDatabase(dbPath.getPath(), null, SQLiteDatabase.OPEN_READWRITE);
    }

    public List<Question> getRandomQuestions(int limit) {
        List<Question> questions = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM questions ORDER BY RANDOM() LIMIT ?", new String[]{String.valueOf(limit)});

        while (cursor.moveToNext()) {
            Question q = new Question();
            q.setId(cursor.getInt(0));
            q.setQuestion(cursor.getString(1));
            q.setOptionA(cursor.getString(2));
            q.setOptionB(cursor.getString(3));
            q.setOptionC(cursor.getString(4));
            q.setOptionD(cursor.getString(5));
            q.setCorrectAnswer(cursor.getInt(6));
            // Nếu có thêm image thì lấy thêm tại cursor.getString(7);
            questions.add(q);
        }
        cursor.close();
        return questions;
    }

    public void insertQuestion(Question q) {
        ContentValues values = new ContentValues();
        values.put("id", q.getId());
        values.put("question", q.getQuestion());
        values.put("optionA", q.getOptionA());
        values.put("optionB", q.getOptionB());
        values.put("optionC", q.getOptionC());
        values.put("optionD", q.getOptionD());
        values.put("correctAnswer", q.getCorrectAnswer());
        // values.put("image", q.getImage()); // nếu có cột image

        db.insert("questions", null, values);
    }

    public void resetQuestions() {
        db.execSQL("DELETE FROM questions");
    }
}
