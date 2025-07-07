package com.example.trafficquiz.ui;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.trafficquiz.R;

public class ResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        int score = getIntent().getIntExtra("score", 0);
        TextView resultText = findViewById(R.id.resultText);
        resultText.setText("Bạn trả lời đúng " + score + "/25 câu");
    }
}
