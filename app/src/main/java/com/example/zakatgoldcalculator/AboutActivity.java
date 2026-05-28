package com.example.zakatgoldcalculator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView tvGithubLink = findViewById(R.id.tvGithubLink);

        // Sets text view listener to trigger a browser intent safely (Clickable URL requirement)
        tvGithubLink.setOnClickListener(v -> {
            String url = tvGithubLink.getText().toString().trim();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });
    }
}