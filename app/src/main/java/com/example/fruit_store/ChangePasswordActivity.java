package com.example.fruit_store;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText emailReset;
    Button savePasswordBtn;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_password);

        emailReset = findViewById(R.id.emailReset);
        savePasswordBtn = findViewById(R.id.savePasswordBtn);
        firebaseAuth = FirebaseAuth.getInstance();

        savePasswordBtn.setOnClickListener(v -> sendResetEmail());
    }

    private void sendResetEmail() {
        String email = emailReset.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your email!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailReset.setError("Enter valid email");
            emailReset.requestFocus();
            return;
        }

        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this,
                                "Reset link sent. Check your email.",
                                Toast.LENGTH_LONG).show();
                        finish();   // Go back to login page
                    } else {
                        Toast.makeText(this,
                                "Email not found in system.",
                                Toast.LENGTH_LONG).show();
                    }
                });



    }
}