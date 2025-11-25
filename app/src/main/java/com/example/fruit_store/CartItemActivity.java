package com.example.fruit_store;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartItemActivity extends AppCompatActivity {

    ListView listView;
    TextView totalText;

    FirebaseAuth auth;
    DatabaseReference cartRef;

    ArrayList<Fruit> cartList = new ArrayList<>();
    CartItemAdapter adapter;

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_item);

        listView = findViewById(R.id.fruitListView);
        totalText = findViewById(R.id.cartTotalAmount);

        auth = FirebaseAuth.getInstance();
        String userId = auth.getCurrentUser().getUid();

        cartRef = FirebaseDatabase.getInstance()
                .getReference("cart")
                .child(userId);

        adapter = new CartItemAdapter(this, cartList);
        listView.setAdapter(adapter);

        loadCartItems();


        findViewById(R.id.btnCheckOut).setOnClickListener(v -> {
            Intent intent = new Intent(CartItemActivity.this, PaymentAndDeliveryActivity.class);

            long totalAmount = 0;
            intent.putExtra("totalAmount", totalAmount);
            intent.putExtra("cartList", cartList);
            startActivity(intent);

        });

    }

    private void loadCartItems() {
        cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cartList.clear();

                if (!snapshot.exists()) {
                    startActivity(new Intent(CartItemActivity.this, EmptyCartActivity.class));
                    finish();
                    return;
                }

                for (DataSnapshot snap : snapshot.getChildren()) {
                    Fruit fruit = snap.getValue(Fruit.class);
                    if (fruit != null) cartList.add(fruit);
                }
                
                

                adapter.notifyDataSetChanged();
                updateTotal();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void updateTotal() {
        double total = 0;

        for (int i = 0; i < listView.getChildCount(); i++) {
            View item = listView.getChildAt(i);

            Spinner spinner = item.findViewById(R.id.cartQuantitySpinner);
            TextView priceText = item.findViewById(R.id.cartFruitPrice);

            int quantity = Integer.parseInt(spinner.getSelectedItem().toString());
            double price = Double.parseDouble(priceText.getText().toString().replace("$", "").replace("/kg", ""));

            total += quantity * price;
        }

        totalText.setText("$" + String.format("%.2f", total));
    }
}
