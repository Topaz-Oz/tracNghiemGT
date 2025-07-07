package com.example.trafficquiz.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trafficquiz.R;
import com.example.trafficquiz.database.QuizDbHelper;
import com.example.trafficquiz.model.Question;

import java.util.List;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity {

    private TextView questionText, timerText;
    private RadioButton optionA, optionB, optionC, optionD;
    private RadioGroup optionsGroup;
    private Button nextButton;

    private List<Question> questionList;
    private int currentIndex = 0;
    private int score = 0;

    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Ánh xạ view
        questionText = findViewById(R.id.questionText);
        timerText = findViewById(R.id.timerText);
        optionsGroup = findViewById(R.id.optionsGroup);
        optionA = findViewById(R.id.optionA);
        optionB = findViewById(R.id.optionB);
        optionC = findViewById(R.id.optionC);
        optionD = findViewById(R.id.optionD);
        nextButton = findViewById(R.id.nextButton);

        // Lấy danh sách câu hỏi
        QuizDbHelper dbHelper = new QuizDbHelper(this);
        questionList = dbHelper.getRandomQuestions(25);

        // Khởi động đếm ngược
        startTimer();

        // Hiển thị câu đầu tiên
        showQuestion();

        nextButton.setOnClickListener(view -> {
            if (checkAnswer()) score++;
            currentIndex++;

            if (currentIndex < questionList.size()) {
                showQuestion();
            } else {
                timer.cancel();
                showResult();
            }
        });
    }

    private void showQuestion() {
        Question q = questionList.get(currentIndex);
        questionText.setText(String.format(Locale.getDefault(), "Câu %d: %s", currentIndex + 1, q.getQuestion()));
        optionA.setText(q.getOptionA());
        optionB.setText(q.getOptionB());
        optionC.setText(q.getOptionC());
        optionD.setText(q.getOptionD());
        optionsGroup.clearCheck();
    }

    private boolean checkAnswer() {
        int selectedId = optionsGroup.getCheckedRadioButtonId();
        if (selectedId == -1) return false;

        int answer = -1;
        if (selectedId == optionA.getId()) answer = 1;
        else if (selectedId == optionB.getId()) answer = 2;
        else if (selectedId == optionC.getId()) answer = 3;
        else if (selectedId == optionD.getId()) answer = 4;

        return answer == questionList.get(currentIndex).getCorrectAnswer();
    }

    private void startTimer() {
        timer = new CountDownTimer(20 * 60 * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                long min = millisUntilFinished / 60000;
                long sec = (millisUntilFinished % 60000) / 1000;
                timerText.setText(String.format(Locale.getDefault(), "%02d:%02d", min, sec));
            }

            public void onFinish() {
                showResult();
            }
        }.start();
    }

    private void showResult() {
        Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
        intent.putExtra("score", score);
        startActivity(intent);
        finish();
    }
}
