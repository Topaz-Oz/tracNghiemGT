package com.example.trafficquiz.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.trafficquiz.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

    }

    // Thi thử
    public void onClickTrialTest(View view) {
        Intent intent = new Intent(this, TrialIntroActivity.class);
        startActivity(intent);
    }

    // Biển báo
    public void onClickSigns(View view) {
        Intent intent = new Intent(this, SignsActivity.class);
        startActivity(intent);
    }

    // Câu hay sai
    public void onClickWrongQuestions(View view) {
        Intent intent = new Intent(this, WrongQuestionsActivity.class);
        startActivity(intent);
    }
}
