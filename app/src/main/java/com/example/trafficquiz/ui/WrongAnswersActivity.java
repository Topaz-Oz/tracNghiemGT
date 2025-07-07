package com.example.trafficquiz.ui;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.trafficquiz.R;
import com.example.trafficquiz.model.Question;

import java.util.List;
import java.util.Map;

public class WrongAnswersActivity extends AppCompatActivity {

    TextView tvWrongDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong_answers);

        tvWrongDetails = findViewById(R.id.tvWrongDetails);

        List<Question> questions = (List<Question>) getIntent().getSerializableExtra("questions");
        Map<Integer, Integer> userAnswers = (Map<Integer, Integer>) getIntent().getSerializableExtra("userAnswersMap");

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            Integer selected = userAnswers.get(i);

            if (selected == null || selected != q.getCorrectIndex()) {
                sb.append("Câu ").append(i + 1).append(": ").append(q.getQuestion()).append("\n");
                sb.append("Đáp án đúng: ").append(q.getOptions().get(q.getCorrectIndex())).append("\n");
                sb.append("Bạn chọn: ")
                        .append(selected != null && selected >= 0 && selected < q.getOptions().size()
                                ? q.getOptions().get(selected)
                                : "Không chọn")
                        .append("\n\n");
            }
        }

        tvWrongDetails.setText(sb.toString());
    }
}
