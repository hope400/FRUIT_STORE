package com.example.fruit_store;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderHistoryActivity extends AppCompatActivity {

    ListView orderListView;

    FirebaseAuth auth;
    DatabaseReference ordersRef;

    ArrayList<Order> orderList = new ArrayList<>();
    OrderItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);


        orderListView = findViewById(R.id.orderListView);

        auth = FirebaseAuth.getInstance();
        String userId = auth.getCurrentUser().getUid();

        ordersRef = FirebaseDatabase.getInstance()
                .getReference("orders")
                .child(userId);

        adapter = new OrderItemAdapter(this, orderList);
        orderListView.setAdapter(adapter);

        loadOrderHistory();
    }

    private void loadOrderHistory() {
        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                orderList.clear();

                for (DataSnapshot snap : snapshot.getChildren()) {
                    Order order = snap.getValue(Order.class);
                    if (order != null) {
                        orderList.add(order);
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
