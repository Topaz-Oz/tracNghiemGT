package com.example.trafficquiz.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trafficquiz.R;
import com.example.trafficquiz.model.Question;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends AppCompatActivity {

    private TextView questionNumber, questionText, answerStatus, explanationText;
    private ImageView questionImage;
    private RadioGroup optionsGroup;
    private RadioButton optionA, optionB, optionC, optionD;
    private Button prevButton, nextButton;

    private List<Question> questions;
    private ArrayList<Integer> userAnswers;
    private int currentIndex = 0;

    // Đặt key constant riêng, không phụ thuộc ResultActivity
    public static final String EXTRA_QUESTIONS = "EXTRA_QUESTIONS";
    public static final String EXTRA_USER_ANSWERS = "USER_ANSWERS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        // Ánh xạ view
        questionNumber = findViewById(R.id.questionNumber);
        questionText = findViewById(R.id.questionText);
        questionImage = findViewById(R.id.questionImage);
        optionsGroup = findViewById(R.id.optionsGroup);
        optionA = findViewById(R.id.optionA);
        optionB = findViewById(R.id.optionB);
        optionC = findViewById(R.id.optionC);
        optionD = findViewById(R.id.optionD);
        answerStatus = findViewById(R.id.answerStatus);
        explanationText = findViewById(R.id.explanationText);
        prevButton = findViewById(R.id.prevButton);
        nextButton = findViewById(R.id.nextButton);

        // Lấy dữ liệu từ Intent
        questions = (ArrayList<Question>) getIntent().getSerializableExtra(EXTRA_QUESTIONS);
        userAnswers = getIntent().getIntegerArrayListExtra(EXTRA_USER_ANSWERS);

        if (questions == null || userAnswers == null) {
            // Nếu dữ liệu bị null, kết thúc activity
            finish();
            return;
        }

        // Hiển thị câu đầu
        showQuestion();

        prevButton.setOnClickListener(v -> {
            if (currentIndex > 0) {
                currentIndex--;
                showQuestion();
            }
        });

        nextButton.setOnClickListener(v -> {
            if (currentIndex < questions.size() - 1) {
                currentIndex++;
                showQuestion();
            }
        });
    }

    private void showQuestion() {
        Question q = questions.get(currentIndex);
        int userAnswer = userAnswers.get(currentIndex);

        // Số thứ tự
        questionNumber.setText(String.format("Câu %d/%d", currentIndex + 1, questions.size()));

        // Nội dung
        questionText.setText(q.getQuestion());

        // Ảnh
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

        // Đáp án
        optionA.setText(q.getOptionA());
        optionB.setText(q.getOptionB());
        optionC.setText(q.getOptionC());
        optionD.setText(q.getOptionD());

        // Reset màu và trạng thái
        optionA.setTextColor(Color.BLACK);
        optionB.setTextColor(Color.BLACK);
        optionC.setTextColor(Color.BLACK);
        optionD.setTextColor(Color.BLACK);
        optionsGroup.clearCheck();

        RadioButton correctButton = getOptionByNumber(q.getCorrectAnswer());
        RadioButton selectedButton = getOptionByNumber(userAnswer);

        // Hiển thị đáp án đúng
        if (correctButton != null) {
            correctButton.setTextColor(Color.GREEN);
            correctButton.setChecked(true);
        }

        // Nếu sai, tô đỏ đáp án người dùng chọn
        if (selectedButton != null && selectedButton != correctButton) {
            selectedButton.setTextColor(Color.RED);
            selectedButton.setChecked(true);
        }

        // Trạng thái đúng/sai
        if (userAnswer == q.getCorrectAnswer()) {
            answerStatus.setText("✓ Chính xác!");
            answerStatus.setTextColor(Color.GREEN);
        } else {
            answerStatus.setText("✗ Chưa chính xác");
            answerStatus.setTextColor(Color.RED);
        }

        // Giải thích
        String explanation = "Đáp án đúng: " + getAnswerText(q.getCorrectAnswer());
        if (userAnswer > 0) {
            explanation += "\nBạn đã chọn: " + getAnswerText(userAnswer);
        } else {
            explanation += "\nBạn chưa chọn đáp án";
        }
        explanationText.setText(explanation);

        // Enable/disable nút
        prevButton.setEnabled(currentIndex > 0);
        nextButton.setEnabled(currentIndex < questions.size() - 1);
    }

    private RadioButton getOptionByNumber(int number) {
        switch (number) {
            case 1: return optionA;
            case 2: return optionB;
            case 3: return optionC;
            case 4: return optionD;
            default: return null;
        }
    }

    private String getAnswerText(int answerNr) {
        RadioButton option = getOptionByNumber(answerNr);
        return option != null ? option.getText().toString() : "";
    }
}
