package com.example.fruit_store;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class UserFruitAdapter extends BaseAdapter {

    Context context;
    ArrayList<Fruit> list;

    public UserFruitAdapter(Context context, ArrayList<Fruit> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() { return list.size(); }

    @Override
    public Object getItem(int position) { return list.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.activity_fruit_item_user, parent, false);
        }

        ImageView img = convertView.findViewById(R.id.fruitImageUser);
        TextView name = convertView.findViewById(R.id.fruitNameUser);
        TextView price = convertView.findViewById(R.id.fruitPriceUser);
        Button buyBtn = convertView.findViewById(R.id.btnBuy);

        Fruit fruit = list.get(position);

        name.setText(fruit.getName());
        price.setText("$" + fruit.getPrice() + "/kg");

        Glide.with(context).load(fruit.getImageURL()).into(img);

        buyBtn.setOnClickListener(v -> {
            if (context instanceof MainActivity) {
                ((MainActivity) context).addToCart(fruit);
            }
        });

        return convertView;
    }
}
