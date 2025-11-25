package com.example.fruit_store;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CartItemAdapter extends ArrayAdapter<Fruit> {

    Context context;
    ArrayList<Fruit> cartList;
    DatabaseReference cartRef;
    String userId;

    public CartItemAdapter(Context context, ArrayList<Fruit> list) {
        super(context, 0, list);
        this.context = context;
        this.cartList = list;

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        cartRef = FirebaseDatabase.getInstance().getReference("cart").child(userId);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.cart_item_row, parent, false);
        }

        Fruit fruit = cartList.get(position);

        ImageView img = convertView.findViewById(R.id.cartFruitImage);
        TextView name = convertView.findViewById(R.id.cartFruitName);
        TextView price = convertView.findViewById(R.id.cartFruitPrice);
        Spinner quantitySpinner = convertView.findViewById(R.id.cartQuantitySpinner);
        Button deleteBtn = convertView.findViewById(R.id.cartDeleteBtn);

        name.setText(fruit.getName());
        price.setText("$" + fruit.getPrice() + "/kg");

        Glide.with(context).load(fruit.getImageURL()).into(img);


        Integer[] qtyList = {1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> adapter =
                new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, qtyList);
        quantitySpinner.setAdapter(adapter);

        quantitySpinner.setSelection(0);


        deleteBtn.setOnClickListener(v -> {
            cartRef.child(fruit.getId()).removeValue();
            cartList.remove(position);
            notifyDataSetChanged();

            if (context instanceof CartItemActivity) {
                ((CartItemActivity) context).updateTotal();
            }
        });


        quantitySpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int pos, long id) {
                if (context instanceof CartItemActivity) {
                    ((CartItemActivity) context).updateTotal();
                }
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        return convertView;
    }

    public int getQuantity(View view) {
        Spinner spinner = view.findViewById(R.id.cartQuantitySpinner);
        return Integer.parseInt(spinner.getSelectedItem().toString());
    }


}
