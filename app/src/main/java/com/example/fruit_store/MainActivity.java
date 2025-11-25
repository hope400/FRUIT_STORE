package com.example.fruit_store;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    EditText searchBar;
    ListView fruitListView;

    LinearLayout navHome, navCart, navOrders;

    DatabaseReference fruitsRef, cartRef;
    FirebaseAuth auth;

    ArrayList<Fruit> fruitList = new ArrayList<>();
    UserFruitAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        searchBar = findViewById(R.id.searchBar);
        fruitListView = findViewById(R.id.fruitListView);

        navHome = findViewById(R.id.navHome);
        navCart = findViewById(R.id.navCart);
        navOrders = findViewById(R.id.navOrders);




        auth = FirebaseAuth.getInstance();
        fruitsRef = FirebaseDatabase.getInstance().getReference("fruits");
        cartRef = FirebaseDatabase.getInstance().getReference("cart");

        adapter = new UserFruitAdapter(this, fruitList);
        fruitListView.setAdapter(adapter);

        loadFruits();
        setupBottomNav();

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterFruits(s.toString());
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void loadFruits() {
        fruitsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fruitList.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Fruit fruit = snap.getValue(Fruit.class);
                    if (fruit != null) fruitList.add(fruit);
                }
                adapter.notifyDataSetChanged();
            }

            @Override public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void filterFruits(String text) {
        ArrayList<Fruit> filtered = new ArrayList<>();
        for (Fruit f : fruitList) {
            if (f.getName().toLowerCase().contains(text.toLowerCase()))
                filtered.add(f);
        }
        adapter = new UserFruitAdapter(this, filtered);
        fruitListView.setAdapter(adapter);
    }

    public void addToCart(Fruit fruit) {
        String userId = auth.getCurrentUser().getUid();
        cartRef.child(userId).child(fruit.getId())
                .setValue(fruit)
                .addOnSuccessListener(a ->
                        Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show()
                );
    }

    private void setupBottomNav() {
        navHome.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, MainActivity.class));
        });

        navCart.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CartItemActivity.class));
        });

        navOrders.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, OrderHistoryActivity.class));
        });

    }

    private void checkCartBeforeOpening() {
        String userId = auth.getCurrentUser().getUid();

        cartRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (!snapshot.exists()) {
                    // Cart is empty → go to empty cart page
                    startActivity(new Intent(MainActivity.this, EmptyCartActivity.class));
                } else {
                    // Cart has items → go to cart page
                    startActivity(new Intent(MainActivity.this, CartItemActivity.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

}