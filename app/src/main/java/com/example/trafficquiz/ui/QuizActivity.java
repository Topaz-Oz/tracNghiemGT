package com.example.trafficquiz.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trafficquiz.R;
import com.example.trafficquiz.database.QuizDbHelper;
import com.example.trafficquiz.model.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity {

    private TextView questionText, timerText, questionCount;
    private RadioButton optionA, optionB, optionC, optionD;
    private RadioGroup optionsGroup;
    private Button nextButton;
    private RecyclerView questionNavRecyclerView;
    private QuestionNavAdapter navAdapter;
    private List<Boolean> markedForReview = new ArrayList<>();
    private List<Question> questionList;
    private int currentIndex = 0;
    private int score = 0;
    private CountDownTimer timer;

    // Trạng thái đã trả lời
    private List<Boolean> answeredStatus = new ArrayList<>();
    // Lưu đáp án đã chọn
    private List<Integer> selectedAnswers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        questionText = findViewById(R.id.questionText);
        timerText = findViewById(R.id.timerText);
        questionCount = findViewById(R.id.questionCount);
        optionsGroup = findViewById(R.id.optionsGroup);
        optionA = findViewById(R.id.optionA);
        optionB = findViewById(R.id.optionB);
        optionC = findViewById(R.id.optionC);
        optionD = findViewById(R.id.optionD);
        nextButton = findViewById(R.id.nextButton);
        questionNavRecyclerView = findViewById(R.id.questionNavRecyclerView);
        Button markButton = findViewById(R.id.markButton);
        QuizDbHelper dbHelper = new QuizDbHelper(this);
        questionList = dbHelper.getRandomQuestions(25);
        markButton.setOnClickListener(view -> {
            boolean currentStatus = markedForReview.get(currentIndex);
            markedForReview.set(currentIndex, !currentStatus);
            navAdapter.notifyDataSetChanged();
        });
        for (int i = 0; i < questionList.size(); i++) {
            answeredStatus.add(false);
            selectedAnswers.add(-1); // -1: chưa chọn
            markedForReview.add(false);
        }

        // 💡 Đây! Gán sự kiện cho nút
        markButton.setOnClickListener(view -> {
            boolean currentStatus = markedForReview.get(currentIndex);
            markedForReview.set(currentIndex, !currentStatus);
            navAdapter.notifyDataSetChanged();
        });
        List<Integer> questionNumbers = new ArrayList<>();
        for (int i = 1; i <= questionList.size(); i++) {
            questionNumbers.add(i);
        }

        questionNavRecyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        navAdapter = new QuestionNavAdapter(questionNumbers, answeredStatus, markedForReview, this, position -> {
            currentIndex = position;
            animateQuestionChange(this::showQuestion);
        });

        questionNavRecyclerView.setAdapter(navAdapter);

        // Khi chọn radio button, lưu trạng thái
        optionsGroup.setOnCheckedChangeListener((group, checkedId) -> {
            int answer = -1;
            if (checkedId == optionA.getId()) answer = 1;
            else if (checkedId == optionB.getId()) answer = 2;
            else if (checkedId == optionC.getId()) answer = 3;
            else if (checkedId == optionD.getId()) answer = 4;

            selectedAnswers.set(currentIndex, answer);

            // Nếu đã chọn, đánh dấu đã trả lời
            if (answer != -1) {
                answeredStatus.set(currentIndex, true);
                navAdapter.notifyDataSetChanged();
            }
        });

        startTimer();
        showQuestion();

        nextButton.setOnClickListener(view -> {
            if (selectedAnswers.get(currentIndex) != -1 && !answeredStatus.get(currentIndex)) {
                score++;
                answeredStatus.set(currentIndex, true);
                navAdapter.notifyDataSetChanged();
            }

            if (currentIndex < questionList.size() - 1) {
                currentIndex++;
                animateQuestionChange(this::showQuestion);
            } else {
                timer.cancel();
                showResult();
            }
        });

    }
    private void animateQuestionChange(Runnable onAnimationEnd) {
        questionText.animate()
                .alpha(0f)
                .setDuration(200)
                .withEndAction(() -> {
                    onAnimationEnd.run();
                    questionText.animate().alpha(1f).setDuration(200).start();
                }).start();

        optionA.animate().alpha(0f).setDuration(200).withEndAction(() -> optionA.animate().alpha(1f).setDuration(200).start()).start();
        optionB.animate().alpha(0f).setDuration(200).withEndAction(() -> optionB.animate().alpha(1f).setDuration(200).start()).start();
        optionC.animate().alpha(0f).setDuration(200).withEndAction(() -> optionC.animate().alpha(1f).setDuration(200).start()).start();
        optionD.animate().alpha(0f).setDuration(200).withEndAction(() -> optionD.animate().alpha(1f).setDuration(200).start()).start();
    }

    private void showQuestion() {
        Question q = questionList.get(currentIndex);

        questionCount.setText("Câu " + (currentIndex + 1) + "/" + questionList.size());

        String rawQuestion = q.getQuestion();
        String formattedQuestion = rawQuestion;
        if (rawQuestion.startsWith("Câu hỏi ")) {
            int index = rawQuestion.indexOf(":");
            if (index != -1 && index + 1 < rawQuestion.length()) {
                formattedQuestion = rawQuestion.substring(index + 1).trim();
            }
        }
        questionText.setText(formattedQuestion);

        optionA.setText(q.getOptionA());
        optionB.setText(q.getOptionB());
        optionC.setText(q.getOptionC());
        optionD.setText(q.getOptionD());

        optionsGroup.setOnCheckedChangeListener(null);
        optionsGroup.clearCheck();

        int savedAnswer = selectedAnswers.get(currentIndex);
        if (savedAnswer == 1) optionsGroup.check(optionA.getId());
        else if (savedAnswer == 2) optionsGroup.check(optionB.getId());
        else if (savedAnswer == 3) optionsGroup.check(optionC.getId());
        else if (savedAnswer == 4) optionsGroup.check(optionD.getId());

        // Đặt lại listener
        optionsGroup.setOnCheckedChangeListener((group, checkedId) -> {
            int answer = -1;
            if (checkedId == optionA.getId()) answer = 1;
            else if (checkedId == optionB.getId()) answer = 2;
            else if (checkedId == optionC.getId()) answer = 3;
            else if (checkedId == optionD.getId()) answer = 4;

            selectedAnswers.set(currentIndex, answer);
            if (answer != -1) {
                answeredStatus.set(currentIndex, true);
                navAdapter.notifyDataSetChanged();
            }
        });

        // 🔥 Thêm đoạn này
        if (currentIndex == questionList.size() - 1) {
            nextButton.setText("Nộp bài");
        } else {
            nextButton.setText("Tiếp theo");
        }
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
        // Tính điểm lại chính xác
        score = 0;
        for (int i = 0; i < questionList.size(); i++) {
            if (selectedAnswers.get(i) == questionList.get(i).getCorrectAnswer()) {
                score++;
            }
        }

        Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("questionList", new ArrayList<>(questionList));
        intent.putExtra("selectedAnswers", new ArrayList<>(selectedAnswers));
        startActivity(intent);
        finish();
    }
}
