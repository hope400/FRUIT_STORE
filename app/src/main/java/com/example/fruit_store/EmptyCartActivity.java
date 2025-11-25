package com.example.fruit_store;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EmptyCartActivity extends AppCompatActivity {

    Button btnHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_empty_cart);

        btnHome = findViewById(R.id.btnRegister);

        btnHome.setOnClickListener(v -> {
            startActivity(new Intent(EmptyCartActivity.this, MainActivity.class));
            finish();
        });
    }
}