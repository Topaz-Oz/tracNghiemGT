package com.example.trafficquiz.ui;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trafficquiz.R;
import com.example.trafficquiz.model.Question;

import java.util.ArrayList;
import java.util.HashMap;

public class WrongDetailActivity extends AppCompatActivity {

    TextView tvWrongDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong_detail);

        tvWrongDetails = findViewById(R.id.tvWrongDetails);

        ArrayList<Question> questions = (ArrayList<Question>) getIntent().getSerializableExtra("questions");
        HashMap<Integer, Integer> userAnswers = (HashMap<Integer, Integer>) getIntent().getSerializableExtra("userAnswersMap");

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            int userAnswer = userAnswers.getOrDefault(i, -1);
            if (userAnswer != q.getCorrectAnswer()) {
                sb.append("Câu ").append(i + 1).append(": ").append(q.getQuestion()).append("\n");
                sb.append("Bạn chọn: ").append(getOptionText(q, userAnswer)).append("\n");
                sb.append("Đáp án đúng: ").append(getOptionText(q, q.getCorrectAnswer())).append("\n\n");
            }
        }

        if (sb.length() == 0) {
            sb.append("Bạn không sai câu nào. Tuyệt vời!");
        }

        tvWrongDetails.setText(sb.toString());
    }

    private String getOptionText(Question q, int index) {
        switch (index) {
            case 0: return q.getOptionA();
            case 1: return q.getOptionB();
            case 2: return q.getOptionC();
            case 3: return q.getOptionD();
            default: return "(Không chọn)";
        }
    }
}
