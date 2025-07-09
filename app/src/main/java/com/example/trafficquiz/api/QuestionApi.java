package com.example.trafficquiz.api;

import com.example.trafficquiz.model.QuestionResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface QuestionApi {
    @GET("questions")
    Call<QuestionResponse> getQuestions(
        @Query("limit") int limit,
        @Query("category") String category
    );

    @GET("questions/random")
    Call<QuestionResponse> getRandomQuestions(
        @Query("limit") int limit
    );
}
