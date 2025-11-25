package com.example.fruit_store;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class AdminMainActivity extends AppCompatActivity {

    Button btnClients, btnOrders, btnFruits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_main);

        btnClients = findViewById(R.id.btnClients);
        btnOrders = findViewById(R.id.btnOrders);
        btnFruits = findViewById(R.id.btnFruits);


        btnClients.setOnClickListener(v -> {
            Intent intent = new Intent(AdminMainActivity.this, AdminClientsPage.class);
            startActivity(intent);
        });


        btnOrders.setOnClickListener(v -> {
            Intent intent = new Intent(AdminMainActivity.this, AdminOrdersActivity.class);
            startActivity(intent);
        });


        btnFruits.setOnClickListener(v -> {
            Intent intent = new Intent(AdminMainActivity.this, AdminFruitListActivity.class);
            startActivity(intent);
        });

    }
}