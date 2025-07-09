package com.example.trafficquiz.ui;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trafficquiz.R;
import com.example.trafficquiz.model.WrongQuestion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class WrongQuestionsActivity extends AppCompatActivity {

    private RecyclerView recyclerWrongQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong_questions);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        // Nút back
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        // RecyclerView
        recyclerWrongQuestions = findViewById(R.id.recyclerWrongQuestions);
        recyclerWrongQuestions.setLayoutManager(new LinearLayoutManager(this));

        // Load data
        List<WrongQuestion> questionList = loadQuestionsFromJson();
        WrongQuestionsAdapter adapter = new WrongQuestionsAdapter(questionList);
        recyclerWrongQuestions.setAdapter(adapter);
    }

    private List<WrongQuestion> loadQuestionsFromJson() {
        List<WrongQuestion> list = new ArrayList<>();
        try {
            AssetManager assetManager = getAssets();
            InputStream is = assetManager.open("wrong_questions.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String jsonStr = new String(buffer, StandardCharsets.UTF_8);

            JSONArray jsonArray = new JSONArray(jsonStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                int id = obj.getInt("id");
                String questionText = obj.getString("questionText");

                JSONArray optionsArray = obj.getJSONArray("options");
                List<String> options = new ArrayList<>();
                for (int j = 0; j < optionsArray.length(); j++) {
                    options.add(optionsArray.getString(j));
                }

                String correctAnswer = obj.getString("correctAnswer");
                String explanation = obj.getString("explanation");

                list.add(new WrongQuestion(id, questionText, options, correctAnswer, explanation));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Adapter gộp trong file này
    public static class WrongQuestionsAdapter extends RecyclerView.Adapter<WrongQuestionsAdapter.ViewHolder> {

        private final List<WrongQuestion> questionList;

        public WrongQuestionsAdapter(List<WrongQuestion> questionList) {
            this.questionList = questionList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_wrong_question, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            WrongQuestion question = questionList.get(position);

            holder.tvQuestion.setText((position + 1) + ". " + question.getQuestionText());

            if (question.getOptions().size() >= 4) {
                holder.tvOptions.setText(
                        "A. " + question.getOptions().get(0) + "\n" +
                                "B. " + question.getOptions().get(1) + "\n" +
                                "C. " + question.getOptions().get(2) + "\n" +
                                "D. " + question.getOptions().get(3)
                );
            } else {
                holder.tvOptions.setText("Không đủ phương án");
            }

            holder.tvAnswer.setText("Đáp án đúng: " + question.getCorrectAnswer());
            holder.tvExplanation.setText("Giải thích: " + question.getExplanation());
        }

        @Override
        public int getItemCount() {
            return questionList.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvQuestion, tvOptions, tvAnswer, tvExplanation;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvQuestion = itemView.findViewById(R.id.tvQuestion);
                tvOptions = itemView.findViewById(R.id.tvOptions);
                tvAnswer = itemView.findViewById(R.id.tvAnswer);
                tvExplanation = itemView.findViewById(R.id.tvExplanation);
            }
        }
    }
}
