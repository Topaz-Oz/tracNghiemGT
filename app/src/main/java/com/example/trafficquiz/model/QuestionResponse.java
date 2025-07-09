package com.example.trafficquiz.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class QuestionResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private List<Question> questions;

    @SerializedName("message")
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public String getMessage() {
        return message;
    }
}
