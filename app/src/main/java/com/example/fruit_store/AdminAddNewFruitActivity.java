package com.example.fruit_store;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AdminAddNewFruitActivity extends AppCompatActivity {


    ImageView addFruitImage;
    EditText inputFruitName, inputFruitPrice, inputFruitStock;
    Button btnSaveFruit;

    DatabaseReference fruitsRef;
    StorageReference imagesRef;

    Uri imageUri;

    ActivityResultLauncher<String> imagePickerLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_add_new_fruit);

        addFruitImage = findViewById(R.id.addFruitImage);
        inputFruitName = findViewById(R.id.inputFruitName);
        inputFruitPrice = findViewById(R.id.inputFruitPrice);
        inputFruitStock = findViewById(R.id.inputFruitStock);
        btnSaveFruit = findViewById(R.id.btnSaveFruit);

        fruitsRef = FirebaseDatabase.getInstance().getReference("fruits");
        imagesRef = FirebaseStorage.getInstance().getReference("fruit_images");

        // image picker
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        imageUri = uri;
                        addFruitImage.setImageURI(uri);
                    }
                });

        addFruitImage.setOnClickListener(v ->
                imagePickerLauncher.launch("image/*")
        );

        btnSaveFruit.setOnClickListener(v -> saveFruit());
    }

    private void saveFruit() {
        String name = inputFruitName.getText().toString().trim();
        String priceStr = inputFruitPrice.getText().toString().trim();
        String stockStr = inputFruitStock.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(priceStr)
                || TextUtils.isEmpty(stockStr) || imageUri == null) {
            Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        double price;
        int stock;
        try {
            price = Double.parseDouble(priceStr);
            stock = Integer.parseInt(stockStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Price and stock must be numbers", Toast.LENGTH_SHORT).show();
            return;
        }

        // create ID
        String id = fruitsRef.push().getKey();
        if (id == null) {
            Toast.makeText(this, "Error creating ID", Toast.LENGTH_SHORT).show();
            return;
        }

        StorageReference fileRef = imagesRef.child(id + ".jpg");
        UploadTask uploadTask = fileRef.putFile(imageUri);

        uploadTask.addOnSuccessListener(taskSnapshot ->
                fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    String imageUrl = uri.toString();

                    Fruit fruit = new Fruit(id, name, price, stock, imageUrl);

                    fruitsRef.child(id).setValue(fruit)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(this, "Fruit saved", Toast.LENGTH_SHORT).show();
                                finish(); // back to list
                            })
                            .addOnFailureListener(e ->
                                    Toast.makeText(this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                })
        ).addOnFailureListener(e ->
                Toast.makeText(this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show()
        );

    }
}