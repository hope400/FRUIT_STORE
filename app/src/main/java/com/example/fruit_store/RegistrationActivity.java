package com.example.fruit_store;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity {

    EditText fullNameEditText, emailEditText, passwordEditText, postalEditText;
    Button registerBtn;
    TextView loginText;

    FirebaseAuth firebaseAuth;
    DatabaseReference clientsRef, usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);

        fullNameEditText = findViewById(R.id.fullName);
        emailEditText = findViewById(R.id.regEmail);
        passwordEditText = findViewById(R.id.regPassword);
        postalEditText = findViewById(R.id.PostalAddress);


        registerBtn = findViewById(R.id.btnRegister);
        loginText = findViewById(R.id.loginText);

        firebaseAuth = FirebaseAuth.getInstance();
        clientsRef = FirebaseDatabase.getInstance().getReference("clients");
        usersRef = FirebaseDatabase.getInstance().getReference("users");

        loginText.setOnClickListener(v ->
                startActivity(new Intent(RegistrationActivity.this, UserLoginActivity.class)));

        registerBtn.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String fullName = fullNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String pass = passwordEditText.getText().toString().trim();
        String postal = postalEditText.getText().toString().trim();

        if (fullName.isEmpty() || email.isEmpty() || pass.isEmpty() || postal.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, pass)
                .addOnSuccessListener(authResult -> {

                    String uid = authResult.getUser().getUid();

                    // Save date as: Nov 24, 2025
                    String registeredDate = java.text.DateFormat
                            .getDateInstance()
                            .format(new java.util.Date());

                    AdminUserModel user = new AdminUserModel(email, registeredDate);

                    FirebaseDatabase.getInstance()
                            .getReference("users")
                            .child(uid)
                            .setValue(user);

                    Toast.makeText(RegistrationActivity.this,
                            "Registration successful!", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(RegistrationActivity.this, UserLoginActivity.class));
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(RegistrationActivity.this,
                                "Error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show()
                );

    }
}
