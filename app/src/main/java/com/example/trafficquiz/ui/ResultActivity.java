package com.example.trafficquiz.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trafficquiz.R;
import com.example.trafficquiz.model.Question;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    private ArrayList<Question> questionList;
    private ArrayList<Integer> selectedAnswers;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        questionList = (ArrayList<Question>) getIntent().getSerializableExtra("questionList");
        selectedAnswers = (ArrayList<Integer>) getIntent().getSerializableExtra("selectedAnswers");
        score = getIntent().getIntExtra("score", 0);

        TextView scoreText = findViewById(R.id.scoreText);
        scoreText.setText("Bạn trả lời đúng " + score + "/" + questionList.size() + " câu");

        TextView resultMessage = findViewById(R.id.resultMessage);
        if (score >= 21) { // VD: >=21/25 là đậu
            resultMessage.setText("🎉 Chúc mừng! Bạn đã ĐẬU kỳ thi.");
            resultMessage.setTextColor(ContextCompat.getColor(this, android.R.color.holo_green_dark));
        } else {
            resultMessage.setText("😢 Rất tiếc! Bạn chưa đạt, vui lòng thử lại.");
            resultMessage.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_dark));
        }

        RecyclerView recyclerView = findViewById(R.id.questionNavRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5));

        // Adapter riêng để tô màu ô câu hỏi
        ResultQuestionNavAdapter adapter = new ResultQuestionNavAdapter(questionList, selectedAnswers, this);
        recyclerView.setAdapter(adapter);

        Button retryButton = findViewById(R.id.retryButton);
        retryButton.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, QuizActivity.class);
            startActivity(intent);
            finish();
        });

        Button viewWrongButton = findViewById(R.id.viewWrongButton);
        viewWrongButton.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, WrongDetailActivity.class);
            intent.putExtra("questionList", questionList);
            intent.putExtra("selectedAnswers", selectedAnswers);
            startActivity(intent);
        });
    }
}
