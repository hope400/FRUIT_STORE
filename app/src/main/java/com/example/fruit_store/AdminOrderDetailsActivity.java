package com.example.fruit_store;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class AdminOrderDetailsActivity extends AppCompatActivity {

    ImageView fruitImage;
    EditText detailFruitName, detailQuantity, detailPrice;

    Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order_details);

        fruitImage = findViewById(R.id.fruitImage);
        detailFruitName = findViewById(R.id.detailFruitName);
        detailQuantity = findViewById(R.id.detailQuantity);
        detailPrice = findViewById(R.id.detailPrice);

        order = (Order) getIntent().getSerializableExtra("order");

        loadDetails();
    }

    private void loadDetails() {


        Glide.with(this).load(order.getImageURL()).into(fruitImage);


        detailFruitName.setText(order.getFruitName());


        detailQuantity.setText("1");


        detailPrice.setText("$" + order.getPrice());
    }
}
