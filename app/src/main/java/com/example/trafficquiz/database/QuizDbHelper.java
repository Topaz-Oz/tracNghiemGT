package com.example.trafficquiz.database;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.trafficquiz.model.Question;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper {
    private static final String DB_NAME = "questions.db";
    private final Context context;
    private final File dbPath;

    public QuizDbHelper(Context context) {
        this.context = context.getApplicationContext();
        this.dbPath = context.getDatabasePath(DB_NAME);
        copyDatabaseIfNeeded();
    }

    private void copyDatabaseIfNeeded() {
        if (!dbPath.exists()) {
            dbPath.getParentFile().mkdirs();
            try (
                    InputStream is = context.getAssets().open(DB_NAME);
                    OutputStream os = new FileOutputStream(dbPath)
            ) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Question> getRandomQuestions(int limit) {
        List<Question> questionList = new ArrayList<>();
        SQLiteDatabase db = SQLiteDatabase.openDatabase(
                dbPath.getPath(), null, SQLiteDatabase.OPEN_READONLY
        );

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
                questionList.add(q);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return questionList;
    }
}
