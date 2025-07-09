package com.example.trafficquiz.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trafficquiz.R;
import com.example.trafficquiz.model.Question;

import java.util.List;

public class ResultQuestionNavAdapter extends RecyclerView.Adapter<ResultQuestionNavAdapter.ViewHolder> {

    private final List<Question> questionList;
    private final List<Integer> selectedAnswers;
    private final Context context;

    public ResultQuestionNavAdapter(List<Question> questionList, List<Integer> selectedAnswers, Context context) {
        this.questionList = questionList;
        this.selectedAnswers = selectedAnswers;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_question_number, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.btnQuestionNumber.setText(String.valueOf(position + 1));

        int selectedAnswer = selectedAnswers.get(position);
        int correctAnswer = questionList.get(position).getCorrectAnswer();

        if (selectedAnswer == correctAnswer) {
            // Trả lời đúng
            holder.btnQuestionNumber.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_green_light));
        } else {
            // Trả lời sai
            holder.btnQuestionNumber.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_red_light));
        }
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Button btnQuestionNumber;

        public ViewHolder(View itemView) {
            super(itemView);
            btnQuestionNumber = itemView.findViewById(R.id.btnQuestionNumber);
        }
    }
}
