package com.example.fruit_store;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminLoginActivity extends AppCompatActivity {


    EditText adminUsername, adminPassword;
    Button adminLoginBtn;

    private final String ADMIN_USERNAME = "Admin";
    private final String ADMIN_PASSWORD = "88888888";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_login);

        adminUsername = findViewById(R.id.adminUsername);
        adminPassword = findViewById(R.id.adminPassword);
        adminLoginBtn = findViewById(R.id.adminLoginBtn);

        adminLoginBtn.setOnClickListener(v -> adminLogin());
    }

    private void adminLogin() {
        String username = adminUsername.getText().toString().trim();
        String password = adminPassword.getText().toString().trim();


        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }


        if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {

            Toast.makeText(this, "Admin Login Successful", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(AdminLoginActivity.this, AdminMainActivity.class);
            startActivity(intent);
            finish();

        } else {
            Toast.makeText(this, "Incorrect admin username or password", Toast.LENGTH_SHORT).show();
        }

    }
}