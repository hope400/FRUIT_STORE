package com.example.fruit_store;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class AdminFruitDetailsActivity extends AppCompatActivity {

    ImageView detailsImage;
    EditText detailsName, detailsPrice, detailsStock;
    TextView detailsBack;
    Button btnSaveChanges;

    DatabaseReference fruitsRef;

    String fruitId;
    String imageURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_fruit_details);


        detailsImage = findViewById(R.id.detailsImage);
        detailsName = findViewById(R.id.detailsName);
        detailsPrice = findViewById(R.id.detailsPrice);
        detailsStock = findViewById(R.id.detailsStock);
        detailsBack = findViewById(R.id.detailsBack);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);

        fruitsRef = FirebaseDatabase.getInstance().getReference("fruits");

        // get data from intent
        fruitId = getIntent().getStringExtra("id");
        String name = getIntent().getStringExtra("name");
        double price = getIntent().getDoubleExtra("price", 0.0);
        int stock = getIntent().getIntExtra("stock", 0);
        imageURL = getIntent().getStringExtra("imageURL");

        // display
        detailsName.setText(name);
        detailsPrice.setText(String.valueOf(price));
        detailsStock.setText(String.valueOf(stock));


        if (imageURL != null && !imageURL.isEmpty()) {
            Glide.with(this).load(imageURL).into(detailsImage);
        }

        detailsBack.setOnClickListener(v -> finish());


        // save button
        btnSaveChanges.setOnClickListener(v -> saveChanges());
    }


    private void saveChanges() {

        String newName = detailsName.getText().toString().trim();
        String newPriceStr = detailsPrice.getText().toString().trim();
        String newStockStr = detailsStock.getText().toString().trim();

        if (newName.isEmpty() || newPriceStr.isEmpty() || newStockStr.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double newPrice;
        int newStock;

        try {
            newPrice = Double.parseDouble(newPriceStr);
            newStock = Integer.parseInt(newStockStr);
        } catch (Exception e) {
            Toast.makeText(this, "Price and stock must be numbers", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, Object> updateMap = new HashMap<>();
        updateMap.put("name", newName);
        updateMap.put("price", newPrice);
        updateMap.put("stock", newStock);

        fruitsRef.child(fruitId).updateChildren(updateMap)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Fruit updated successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to update fruit!", Toast.LENGTH_SHORT).show();
                });
    }
}

