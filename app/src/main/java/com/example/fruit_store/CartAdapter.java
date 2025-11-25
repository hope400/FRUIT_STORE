package com.example.fruit_store;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {


    Context context;
    ArrayList<Fruit> cartList;
    QuantityChangeListener listener;

    public interface QuantityChangeListener {
        void onQuantityChanged();
    }

    public CartAdapter(Context context, ArrayList<Fruit> cartList, QuantityChangeListener listener) {
        this.context = context;
        this.cartList = cartList;
        this.listener = listener;
    }

    @Override
    public int getCount() { return cartList.size(); }

    @Override
    public Object getItem(int position) { return cartList.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.cart_item_row, parent, false);
        }

        ImageView img = convertView.findViewById(R.id.cartFruitImage);
        TextView name = convertView.findViewById(R.id.cartFruitName);
        TextView price = convertView.findViewById(R.id.cartFruitPrice);
        Spinner quantitySpinner = convertView.findViewById(R.id.cartQuantitySpinner);

        Fruit fruit = cartList.get(position);

        name.setText(fruit.getName());
        price.setText("$" + fruit.getPrice() + "/kg");

        Glide.with(context).load(fruit.getImageURL()).into(img);


        ArrayAdapter<String> quantityAdapter = new ArrayAdapter<>(
                context,
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"1","2","3","4","5"}
        );
        quantitySpinner.setAdapter(quantityAdapter);

        quantitySpinner.setSelection(0);


        quantitySpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int i, long l) {
                fruit.setSelectedQuantity(i + 1);
                listener.onQuantityChanged();
            }
            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        return convertView;
    }
}
