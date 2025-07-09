package com.example.trafficquiz.repository;

import android.content.Context;

import com.example.trafficquiz.database.QuizDbHelper;
import com.example.trafficquiz.model.Question;

import java.util.List;

public class QuestionRepository {
    private QuizDbHelper dbHelper;

    public QuestionRepository(Context context) {
        dbHelper = new QuizDbHelper(context);
    }

    public List<Question> getRandomQuestions(int limit) {
        return dbHelper.getRandomQuestions(limit);
    }

    public List<Question> getQuestionsByCategory(String category, int limit) {
        return dbHelper.getQuestionsByCategory(category, limit);
    }
}
