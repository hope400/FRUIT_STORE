package com.example.fruit_store;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class PaymentAndDeliveryActivity extends AppCompatActivity {

    RadioGroup paymentMethodGroup;
    Button confirmBtn;

    FirebaseAuth auth;
    DatabaseReference ordersRef, adminOrdersRef, cartRef;

    ArrayList<Fruit> cartList = new ArrayList<>();
    double totalAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment_and_delivery);

        paymentMethodGroup = findViewById(R.id.paymentMethodGroup);
        confirmBtn = findViewById(R.id.confirmPaymentMethodBtn);

        auth = FirebaseAuth.getInstance();
        String userId = auth.getCurrentUser().getUid();

        ordersRef = FirebaseDatabase.getInstance().getReference("orders").child(userId);
        adminOrdersRef = FirebaseDatabase.getInstance().getReference("adminOrders");
        cartRef = FirebaseDatabase.getInstance().getReference("cart").child(userId);

        totalAmount = getIntent().getDoubleExtra("totalAmount", 0);
        cartList = (ArrayList<Fruit>) getIntent().getSerializableExtra("cartList");

        confirmBtn.setOnClickListener(v -> {

            int selected = paymentMethodGroup.getCheckedRadioButtonId();

            if (selected == -1) {
                Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show();
                return;
            }

            String method = (selected == R.id.cashOnDelivery)
                    ? "Cash on Delivery"
                    : "Stripe Payment";

            if (method.equals("Stripe Payment")) {

                Intent i = new Intent(PaymentAndDeliveryActivity.this, StipePaymentActivity.class);
                i.putExtra("totalAmount", totalAmount);
                startActivity(i);
                return;
            }

            createOrder("Cash on Delivery");
        });
    }

    private void createOrder(String paymentMethod) {

        String userId = auth.getCurrentUser().getUid();
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());


        if (cartList == null || cartList.isEmpty()) {
            Toast.makeText(this, "Your cart is empty!", Toast.LENGTH_SHORT).show();


            cartRef.removeValue();


            startActivity(new Intent(PaymentAndDeliveryActivity.this, OrderHistoryActivity.class));
            finish();
            return;
        }


        for (Fruit fruit : cartList) {

            String orderId = ordersRef.push().getKey();

            Order order = new Order(
                    orderId,
                    userId,
                    paymentMethod,
                    date,
                    totalAmount,
                    fruit.getId(),
                    fruit.getName(),
                    fruit.getPrice(),
                    fruit.getImageURL()
            );

            ordersRef.child(orderId).setValue(order);
            adminOrdersRef.child(orderId).setValue(order);
        }


        cartRef.removeValue();

        Toast.makeText(this, "Order Created Successfully!", Toast.LENGTH_LONG).show();


        startActivity(new Intent(PaymentAndDeliveryActivity.this, OrderHistoryActivity.class));
        finish();
    }

}
