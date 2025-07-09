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

    private TextView questionNumber;
    private TextView questionText;
    private ImageView questionImage;
    private RadioGroup optionsGroup;
    private RadioButton optionA, optionB, optionC, optionD;
    private TextView answerStatus;
    private TextView explanationText;
    private Button prevButton, nextButton;

    private List<Question> questions;
    private ArrayList<Integer> userAnswers;
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        // Ánh xạ views
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

        // Lấy dữ liệu từ intent
        questions = (ArrayList<Question>) getIntent().getSerializableExtra(ResultActivity.EXTRA_QUESTIONS);
        userAnswers = getIntent().getIntegerArrayListExtra("USER_ANSWERS");

        // Hiển thị câu hỏi đầu tiên
        showQuestion();

        // Xử lý sự kiện click nút Trước/Sau
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

        // Hiển thị số thứ tự câu hỏi
        questionNumber.setText(String.format("Câu %d/%d", currentIndex + 1, questions.size()));

        // Hiển thị nội dung câu hỏi
        questionText.setText(q.getQuestion());

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

        // Hiển thị các đáp án
        optionA.setText(q.getOptionA());
        optionB.setText(q.getOptionB());
        optionC.setText(q.getOptionC());
        optionD.setText(q.getOptionD());

        // Reset màu sắc của tất cả các RadioButton
        optionA.setTextColor(Color.BLACK);
        optionB.setTextColor(Color.BLACK);
        optionC.setTextColor(Color.BLACK);
        optionD.setTextColor(Color.BLACK);

        // Đánh dấu đáp án người dùng đã chọn và đáp án đúng
        RadioButton selectedButton = null;
        RadioButton correctButton = null;

        switch (userAnswer) {
            case 1: selectedButton = optionA; break;
            case 2: selectedButton = optionB; break;
            case 3: selectedButton = optionC; break;
            case 4: selectedButton = optionD; break;
        }

        switch (q.getCorrectAnswer()) {
            case 1: correctButton = optionA; break;
            case 2: correctButton = optionB; break;
            case 3: correctButton = optionC; break;
            case 4: correctButton = optionD; break;
        }

        // Hiển thị đáp án đúng bằng màu xanh
        if (correctButton != null) {
            correctButton.setTextColor(Color.GREEN);
            correctButton.setChecked(true);
        }

        // Nếu người dùng chọn sai, hiển thị màu đỏ
        if (selectedButton != null && selectedButton != correctButton) {
            selectedButton.setTextColor(Color.RED);
            selectedButton.setChecked(true);
        }

        // Hiển thị trạng thái đúng/sai
        if (userAnswer == q.getCorrectAnswer()) {
            answerStatus.setText("✓ Chính xác!");
            answerStatus.setTextColor(Color.GREEN);
        } else {
            answerStatus.setText("✗ Chưa chính xác");
            answerStatus.setTextColor(Color.RED);
        }

        // Hiển thị giải thích
        String explanation = "Đáp án đúng: " + getAnswerText(q.getCorrectAnswer());
        if (userAnswer > 0) {
            explanation += "\nBạn đã chọn: " + getAnswerText(userAnswer);
        } else {
            explanation += "\nBạn chưa chọn đáp án";
        }
        explanationText.setText(explanation);

        // Enable/disable nút Trước/Sau
        prevButton.setEnabled(currentIndex > 0);
        nextButton.setEnabled(currentIndex < questions.size() - 1);
    }

    private String getAnswerText(int answerNr) {
        switch (answerNr) {
            case 1: return optionA.getText().toString();
            case 2: return optionB.getText().toString();
            case 3: return optionC.getText().toString();
            case 4: return optionD.getText().toString();
            default: return "";
        }
    }
}
