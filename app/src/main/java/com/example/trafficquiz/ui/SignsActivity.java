package com.example.trafficquiz.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.trafficquiz.R;
import com.example.trafficquiz.model.Sign;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SignsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ArrayList<Sign> signList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signs);
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
        recyclerView = findViewById(R.id.recyclerSigns);
        progressBar = findViewById(R.id.progressBar);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Gọi hàm load dữ liệu từ assets
        fetchSignsFromAssets();
    }

    private void fetchSignsFromAssets() {
        progressBar.setVisibility(View.VISIBLE);
        try {
            InputStream is = getAssets().open("signs.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");

            JSONArray jsonArray = new JSONArray(json);
            signList.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Sign sign = new Sign();
                sign.setName(obj.getString("name"));
                sign.setDescription(obj.getString("description"));
                sign.setImageUrl(obj.getString("imageUrl"));
                signList.add(sign);
            }

            runOnUiThread(() -> {
                recyclerView.setAdapter(new SignsAdapter(signList));
                progressBar.setVisibility(View.GONE);
            });

        } catch (IOException | JSONException ex) {
            ex.printStackTrace();
            progressBar.setVisibility(View.GONE);
        }
    }

    // Adapter gộp luôn bên trong
    private class SignsAdapter extends RecyclerView.Adapter<SignsAdapter.SignViewHolder> {
        private ArrayList<Sign> signs;

        public SignsAdapter(ArrayList<Sign> signs) {
            this.signs = signs;
        }

        @NonNull
        @Override
        public SignViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(SignsActivity.this).inflate(R.layout.item_sign, parent, false);
            return new SignViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SignViewHolder holder, int position) {
            Sign sign = signs.get(position);
            holder.txtName.setText(sign.getName());
            holder.txtDescription.setText(sign.getDescription());
            Glide.with(SignsActivity.this).load(sign.getImageUrl()).into(holder.imgSign);
        }

        @Override
        public int getItemCount() {
            return signs.size();
        }

        class SignViewHolder extends RecyclerView.ViewHolder {
            ImageView imgSign;
            TextView txtName, txtDescription;

            public SignViewHolder(@NonNull View itemView) {
                super(itemView);
                imgSign = itemView.findViewById(R.id.imgSign);
                txtName = itemView.findViewById(R.id.txtName);
                txtDescription = itemView.findViewById(R.id.txtDescription);
            }
        }
    }
}
