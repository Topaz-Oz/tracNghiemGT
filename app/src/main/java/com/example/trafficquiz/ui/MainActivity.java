package com.example.trafficquiz.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.trafficquiz.R;

public class MainActivity extends AppCompatActivity {
    private ImageView logoImage;
    private int[] imageIds = {R.drawable.logo,R.drawable.logo1, R.drawable.logo2, R.drawable.logo3}; // Thêm hình bạn có
    private int currentIndex = 0;
    private Handler handler = new Handler();
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        // Ánh xạ ImageView
        logoImage = findViewById(R.id.logoImage);

        // Gán hình đầu tiên
        logoImage.setImageResource(imageIds[currentIndex]);

        // Định nghĩa runnable
        runnable = new Runnable() {
            @Override
            public void run() {
                // Tăng index
                currentIndex++;
                if (currentIndex >= imageIds.length) {
                    currentIndex = 0;
                }

                // Animation fade out
                logoImage.animate().alpha(0f).setDuration(500).withEndAction(() -> {
                    // Đổi hình
                    logoImage.setImageResource(imageIds[currentIndex]);
                    // Animation fade in
                    logoImage.animate().alpha(1f).setDuration(500).start();
                }).start();

                // Lặp lại sau 5 giây
                handler.postDelayed(this, 5000);
            }
        };

        // Bắt đầu chạy sau 5 giây
        handler.postDelayed(runnable, 5000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
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
