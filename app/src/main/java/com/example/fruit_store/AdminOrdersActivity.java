package com.example.fruit_store;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminOrdersActivity extends AppCompatActivity {

    ListView ordersListView;
    ArrayList<Order> orderList = new ArrayList<>();
    ArrayList<String> userEmails = new ArrayList<>();

    DatabaseReference adminOrdersRef, usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_orders);

        ordersListView = findViewById(R.id.ordersListView);

        adminOrdersRef = FirebaseDatabase.getInstance().getReference("adminOrders");
        usersRef = FirebaseDatabase.getInstance().getReference("users");

        loadOrders();
    }

    private void loadOrders() {
        adminOrdersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                orderList.clear();
                userEmails.clear();

                for (DataSnapshot snap : snapshot.getChildren()) {
                    Order order = snap.getValue(Order.class);

                    if (order != null)
                        orderList.add(order);
                }


                for (Order order : orderList) {
                    usersRef.child(order.getUserId())
                            .child("email")
                            .get()
                            .addOnSuccessListener(data -> {
                                userEmails.add(data.getValue(String.class));

                                if (userEmails.size() == orderList.size()) {
                                    AdminOrderAdapter adapter =
                                            new AdminOrderAdapter(AdminOrdersActivity.this, orderList, userEmails);
                                    ordersListView.setAdapter(adapter);
                                }
                            });
                }
            }

            @Override public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
}
