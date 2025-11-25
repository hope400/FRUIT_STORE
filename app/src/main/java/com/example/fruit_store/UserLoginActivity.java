package com.example.fruit_store;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class UserLoginActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText;
    TextView forgotPasswordText, signInText;
    CheckBox rememberCheckBox;
    Button loginBtn, adminBtn;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_login);

        emailEditText = findViewById(R.id.etEmail);
        passwordEditText = findViewById(R.id.etPassword);
        forgotPasswordText = findViewById(R.id.tvForgotPassword);
        signInText = findViewById(R.id.tvSignIn);
        rememberCheckBox = findViewById(R.id.cbRemember);
        loginBtn = findViewById(R.id.btnLogin);
        adminBtn = findViewById(R.id.btnAdmin);

        firebaseAuth = FirebaseAuth.getInstance();


        loginBtn.setOnClickListener(v -> loginUser());


        signInText.setOnClickListener(v ->
                startActivity(new Intent(UserLoginActivity.this, RegistrationActivity.class))
        );


        adminBtn.setOnClickListener(v ->
                startActivity(new Intent(UserLoginActivity.this, AdminLoginActivity.class))
        );


        forgotPasswordText.setOnClickListener(v ->
               startActivity(new Intent(UserLoginActivity.this, ChangePasswordActivity.class))
        );
    }

    private void loginUser() {

        String email = emailEditText.getText().toString().trim();
        String pass = passwordEditText.getText().toString().trim();


        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Email required");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Invalid email");
            return;
        }

        if (TextUtils.isEmpty(pass)) {
            passwordEditText.setError("Password required");
            return;
        }


        firebaseAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        Toast.makeText(UserLoginActivity.this,
                                "Login successful", Toast.LENGTH_SHORT).show();


                        startActivity(new Intent(UserLoginActivity.this, MainActivity.class));
                        finish();

                    } else {
                        Toast.makeText(UserLoginActivity.this,
                                "Login failed: " + task.getException().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });

    }
}