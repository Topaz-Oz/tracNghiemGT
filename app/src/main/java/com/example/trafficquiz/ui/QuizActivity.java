package com.example.trafficquiz.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trafficquiz.R;
import com.example.trafficquiz.model.Question;
import com.example.trafficquiz.repository.QuestionRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity {

    private TextView questionText, timerText;
    private ImageView questionImage;
    private RadioGroup optionsGroup;
    private RadioButton optionA, optionB, optionC, optionD;
    private Button nextButton;

    private List<Question> questionList;
    private ArrayList<Integer> userAnswers;
    private int currentIndex = 0;
    private int score = 0;

    private CountDownTimer timer;
    private long timeRemaining = 15 * 60 * 1000; // 15 phút

    private QuestionRepository questionRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Ánh xạ view
        questionText = findViewById(R.id.questionText);
        questionImage = findViewById(R.id.questionImage);
        timerText = findViewById(R.id.timerText);
        optionsGroup = findViewById(R.id.optionsGroup);
        optionA = findViewById(R.id.optionA);
        optionB = findViewById(R.id.optionB);
        optionC = findViewById(R.id.optionC);
        optionD = findViewById(R.id.optionD);
        nextButton = findViewById(R.id.nextButton);

        // Khởi tạo danh sách câu trả lời
        userAnswers = new ArrayList<>();

        // Lấy câu hỏi từ repository
        questionRepository = new QuestionRepository(this);
        questionList = questionRepository.getRandomQuestions(25);

        // Khởi tạo danh sách câu trả lời
        for (int i = 0; i < questionList.size(); i++) {
            userAnswers.add(0);
        }

        // Bắt đầu đếm giờ
        startTimer();

        // Hiển thị câu hỏi đầu tiên
        showQuestion();

        nextButton.setOnClickListener(view -> {
            // Lưu câu trả lời của người dùng
            int selectedId = optionsGroup.getCheckedRadioButtonId();
            int answer = 0;
            if (selectedId == optionA.getId()) answer = 1;
            else if (selectedId == optionB.getId()) answer = 2;
            else if (selectedId == optionC.getId()) answer = 3;
            else if (selectedId == optionD.getId()) answer = 4;
            userAnswers.set(currentIndex, answer);

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

        // Hiển thị hình ảnh nếu có
        if (q.getImage() != null && !q.getImage().isEmpty()) {
            try {
                InputStream is = getAssets().open("images/" + q.getImage());
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                questionImage.setImageBitmap(bitmap);
                questionImage.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
                questionImage.setVisibility(View.GONE);
            }
        } else {
            questionImage.setVisibility(View.GONE);
        }

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
        timer = new CountDownTimer(timeRemaining, 1000) {
            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished;
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
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(ResultActivity.EXTRA_SCORE, score);
        intent.putExtra(ResultActivity.EXTRA_TIME, timerText.getText().toString());
        intent.putExtra(ResultActivity.EXTRA_QUESTIONS, new ArrayList<>(questionList));
        intent.putIntegerArrayListExtra("USER_ANSWERS", userAnswers);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (timeRemaining > 0) {
            startTimer();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}
