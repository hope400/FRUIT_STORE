package com.example.fruit_store;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminFruitListActivity extends AppCompatActivity {

    LinearLayout fruitListContainer;
    Button btnAddFruit;
    TextView backArrow;

    DatabaseReference fruitsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_fruit_list);

        fruitListContainer = findViewById(R.id.fruitListContainer);
        btnAddFruit = findViewById(R.id.btnAddFruit);
        backArrow = findViewById(R.id.backArrow);

        fruitsRef = FirebaseDatabase.getInstance().getReference("fruits");

        backArrow.setOnClickListener(v -> {
            Intent intent = new Intent(AdminFruitListActivity.this, AdminMainActivity.class);
            startActivity(intent);
            finish();
        });


        btnAddFruit.setOnClickListener(v -> {
            Intent intent = new Intent(AdminFruitListActivity.this, AdminAddNewFruitActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFruits();
    }

    private void loadFruits() {
        fruitsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fruitListContainer.removeAllViews();

                for (DataSnapshot child : snapshot.getChildren()) {
                    Fruit fruit = child.getValue(Fruit.class);
                    if (fruit == null) continue;


                    android.view.View itemView = getLayoutInflater()
                            .inflate(R.layout.activity_admin_fruit_item, fruitListContainer, false);

                    ImageView fruitImage = itemView.findViewById(R.id.fruitImage);
                    TextView fruitName = itemView.findViewById(R.id.fruitName);
                    TextView fruitPrice = itemView.findViewById(R.id.fruitPrice);
                    TextView fruitArrow = itemView.findViewById(R.id.fruitArrow);

                    fruitName.setText(fruit.getName());
                    fruitPrice.setText(String.format("$%.2f/kg", fruit.getPrice()));

                    if (fruit.getImageURL() != null && !fruit.getImageURL().isEmpty()) {
                        Glide.with(AdminFruitListActivity.this)
                                .load(fruit.getImageURL())
                                .into(fruitImage);
                    }


                    android.view.View.OnClickListener openDetailsListener = v -> {
                        Intent intent = new Intent(AdminFruitListActivity.this, AdminFruitDetailsActivity.class);
                        intent.putExtra("id", fruit.getId());
                        intent.putExtra("name", fruit.getName());
                        intent.putExtra("price", fruit.getPrice());
                        intent.putExtra("stock", fruit.getStock());
                        intent.putExtra("imageURL", fruit.getImageURL());
                        startActivity(intent);
                    };

                    fruitArrow.setOnClickListener(openDetailsListener);
                    itemView.setOnClickListener(openDetailsListener);

                    fruitListContainer.addView(itemView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}