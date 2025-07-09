package com.example.trafficquiz.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trafficquiz.R;
import com.example.trafficquiz.model.Question;

import java.util.ArrayList;

public class WrongDetailActivity extends AppCompatActivity {

    private ArrayList<Question> questionList;
    private ArrayList<Integer> selectedAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong_detail);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        questionList = (ArrayList<Question>) getIntent().getSerializableExtra("questionList");
        selectedAnswers = (ArrayList<Integer>) getIntent().getSerializableExtra("selectedAnswers");

        RecyclerView recyclerView = findViewById(R.id.detailRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DetailAdapter adapter = new DetailAdapter(questionList, selectedAnswers, this);
        recyclerView.setAdapter(adapter);

        Button backHomeButton = findViewById(R.id.backHomeButton);
        backHomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(WrongDetailActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    // ------------------- Adapter bên trong luôn -------------------
    private static class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {

        private ArrayList<Question> questionList;
        private ArrayList<Integer> selectedAnswers;
        private Context context;

        public DetailAdapter(ArrayList<Question> questionList, ArrayList<Integer> selectedAnswers, Context context) {
            this.questionList = questionList;
            this.selectedAnswers = selectedAnswers;
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_question_detail, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Question q = questionList.get(position);
            int selected = selectedAnswers.get(position);
            int correct = q.getCorrectAnswer();

            holder.questionText.setText("Câu " + (position + 1) + ": " + q.getQuestion());

            holder.optionA.setText(q.getOptionA());
            holder.optionB.setText(q.getOptionB());
            holder.optionC.setText(q.getOptionC());
            holder.optionD.setText(q.getOptionD());

            holder.optionsGroup.clearCheck();
            if (selected == 1) holder.optionsGroup.check(holder.optionA.getId());
            else if (selected == 2) holder.optionsGroup.check(holder.optionB.getId());
            else if (selected == 3) holder.optionsGroup.check(holder.optionC.getId());
            else if (selected == 4) holder.optionsGroup.check(holder.optionD.getId());

            // Reset màu mặc định
            holder.optionA.setTextColor(Color.BLACK);
            holder.optionB.setTextColor(Color.BLACK);
            holder.optionC.setTextColor(Color.BLACK);
            holder.optionD.setTextColor(Color.BLACK);

            // Tô màu đáp án đúng (xanh)
            if (correct == 1) holder.optionA.setTextColor(Color.GREEN);
            else if (correct == 2) holder.optionB.setTextColor(Color.GREEN);
            else if (correct == 3) holder.optionC.setTextColor(Color.GREEN);
            else if (correct == 4) holder.optionD.setTextColor(Color.GREEN);

            // Nếu chọn sai, tô đỏ
            if (selected != correct && selected != -1) {
                if (selected == 1) holder.optionA.setTextColor(Color.RED);
                else if (selected == 2) holder.optionB.setTextColor(Color.RED);
                else if (selected == 3) holder.optionC.setTextColor(Color.RED);
                else if (selected == 4) holder.optionD.setTextColor(Color.RED);
            }

            // Disable chọn lại
            holder.optionA.setEnabled(false);
            holder.optionB.setEnabled(false);
            holder.optionC.setEnabled(false);
            holder.optionD.setEnabled(false);
        }

        @Override
        public int getItemCount() {
            return questionList.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView questionText;
            RadioGroup optionsGroup;
            RadioButton optionA, optionB, optionC, optionD;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                questionText = itemView.findViewById(R.id.questionText);
                optionsGroup = itemView.findViewById(R.id.optionsGroup);
                optionA = itemView.findViewById(R.id.optionA);
                optionB = itemView.findViewById(R.id.optionB);
                optionC = itemView.findViewById(R.id.optionC);
                optionD = itemView.findViewById(R.id.optionD);
            }
        }
    }
}
