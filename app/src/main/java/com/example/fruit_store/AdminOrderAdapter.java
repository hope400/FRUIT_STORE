package com.example.fruit_store;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdminOrderAdapter extends BaseAdapter {

    Context context;
    ArrayList<Order> orderList;
    ArrayList<String> emailList;

    public AdminOrderAdapter(Context context, ArrayList<Order> orderList, ArrayList<String> emailList) {
        this.context = context;
        this.orderList = orderList;
        this.emailList = emailList;
    }

    @Override
    public int getCount() { return orderList.size(); }

    @Override
    public Object getItem(int i) { return orderList.get(i); }

    @Override
    public long getItemId(int i) { return i; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.activity_admin_order_item, parent, false);
        }

        TextView emailTxt = convertView.findViewById(R.id.txtClientEmail);
        TextView dateTxt = convertView.findViewById(R.id.txtorderDate);
        View arrow = convertView.findViewById(R.id.orderDetailsArrow);

        Order order = orderList.get(position);

        emailTxt.setText(emailList.get(position));
        dateTxt.setText(order.getDate());

        arrow.setOnClickListener(v -> {
            Intent i = new Intent(context, AdminOrderDetailsActivity.class);
            i.putExtra("order", order);
            context.startActivity(i);
        });

        return convertView;
    }
}
