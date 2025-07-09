package com.example.trafficquiz.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.trafficquiz.R;

public class ResultActivity extends AppCompatActivity {

    public static final String EXTRA_SCORE = "extraScore";
    public static final String EXTRA_TIME = "extraTime";
    public static final String EXTRA_QUESTIONS = "extraQuestions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        int score = getIntent().getIntExtra(EXTRA_SCORE, 0);
        String time = getIntent().getStringExtra(EXTRA_TIME);

        TextView scoreText = findViewById(R.id.scoreText);
        TextView timeText = findViewById(R.id.timeText);
        TextView resultMessage = findViewById(R.id.resultMessage);
        Button reviewButton = findViewById(R.id.reviewButton);
        Button retryButton = findViewById(R.id.retryButton);
        Button homeButton = findViewById(R.id.homeButton);

        scoreText.setText(String.format("Điểm của bạn: %d/25", score));
        timeText.setText(String.format("Thời gian làm bài: %s/15:00", time));

        // Hiển thị thông báo kết quả
        if (score >= 21) { // Đạt >= 21/25 điểm (>= 80%)
            resultMessage.setText("Chúc mừng! Bạn đã vượt qua bài thi.");
            resultMessage.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        } else {
            resultMessage.setText("Bạn chưa đạt. Hãy cố gắng lần sau!");
            resultMessage.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        }

        // Xem lại bài thi
        reviewButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ReviewActivity.class);
            intent.putExtra(EXTRA_QUESTIONS, getIntent().getSerializableExtra(EXTRA_QUESTIONS));
            intent.putIntegerArrayListExtra("USER_ANSWERS", getIntent().getIntegerArrayListExtra("USER_ANSWERS"));
            startActivity(intent);
        });

        // Làm lại bài thi
        retryButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, QuizActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });

        // Về trang chủ
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }
}
