package com.example.trafficquiz.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trafficquiz.R;

import java.util.List;

public class QuestionNavAdapter extends RecyclerView.Adapter<QuestionNavAdapter.ViewHolder> {

    private final List<Integer> questionNumbers;
    private final List<Boolean> answeredStatus;
    private final List<Boolean> markedForReview;
    private final Context context;
    private final OnQuestionClickListener listener;

    public interface OnQuestionClickListener {
        void onQuestionClick(int position);
    }

    public QuestionNavAdapter(List<Integer> questionNumbers, List<Boolean> answeredStatus, List<Boolean> markedForReview, Context context, OnQuestionClickListener listener) {
        this.questionNumbers = questionNumbers;
        this.answeredStatus = answeredStatus;
        this.markedForReview = markedForReview;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_question_number, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int questionNumber = questionNumbers.get(position);
        holder.btnQuestionNumber.setText(String.valueOf(questionNumber));

        // Kiểm tra trạng thái
        if (markedForReview.get(position)) {
            // Được đánh dấu: màu vàng
            holder.btnQuestionNumber.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_orange_light));
        } else if (answeredStatus.get(position)) {
            // Đã trả lời: xám
            holder.btnQuestionNumber.setBackgroundColor(ContextCompat.getColor(context, android.R.color.darker_gray));
        } else {
            // Chưa trả lời: trắng
            holder.btnQuestionNumber.setBackgroundColor(ContextCompat.getColor(context, android.R.color.white));
        }

        holder.btnQuestionNumber.setOnClickListener(v -> listener.onQuestionClick(position));
    }

    @Override
    public int getItemCount() {
        return questionNumbers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Button btnQuestionNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnQuestionNumber = itemView.findViewById(R.id.btnQuestionNumber);
        }
    }
}

