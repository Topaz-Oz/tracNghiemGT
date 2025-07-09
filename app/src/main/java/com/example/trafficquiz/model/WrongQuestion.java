package com.example.trafficquiz.model;

import java.util.List;

public class WrongQuestion {
    private int id;
    private String questionText;
    private List<String> options;
    private String correctAnswer;
    private String explanation;

    public WrongQuestion(int id, String questionText, List<String> options, String correctAnswer, String explanation) {
        this.id = id;
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.explanation = explanation;
    }

    public int getId() {
        return id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getExplanation() {
        return explanation;
    }
}
