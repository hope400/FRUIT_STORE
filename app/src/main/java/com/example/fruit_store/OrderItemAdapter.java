package com.example.fruit_store;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class OrderItemAdapter extends BaseAdapter {

    Context context;
    ArrayList<Order> orderList;

    public OrderItemAdapter(Context context, ArrayList<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @Override
    public int getCount() { return orderList.size(); }

    @Override
    public Object getItem(int position) { return orderList.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.activity_order_item_user, parent, false);
        }

        ImageView img = convertView.findViewById(R.id.orderFruitImage);
        TextView name = convertView.findViewById(R.id.orderFruitName);
        TextView price = convertView.findViewById(R.id.orderPrice);
        TextView date = convertView.findViewById(R.id.orderDate);
        Button buyAgainBtn = convertView.findViewById(R.id.btnBuyAgain);

        Order order = orderList.get(position);

        name.setText(order.getFruitName());
        price.setText("$" + order.getPrice());
        date.setText(order.getDate());

        if (order.getImageURL() != null)
            Glide.with(context).load(order.getImageURL()).into(img);

        buyAgainBtn.setOnClickListener(v -> addToCartAgain(order));

        return convertView;
    }

    private void addToCartAgain(Order order) {

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseDatabase.getInstance()
                .getReference("cart")
                .child(userId)
                .child(order.getFruitId())
                .setValue(new Fruit(
                        order.getFruitId(),
                        order.getFruitName(),
                        order.getPrice(),
                        1,
                        order.getImageURL()
                ))
                .addOnSuccessListener(a ->
                        Toast.makeText(context, "Added to cart again!", Toast.LENGTH_SHORT).show()
                );
    }
}
