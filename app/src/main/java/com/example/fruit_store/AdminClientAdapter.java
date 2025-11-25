package com.example.fruit_store;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.fruit_store.R;
import com.example.fruit_store.UserModel;

import java.util.ArrayList;

public class AdminClientAdapter extends BaseAdapter {

    Context context;
    ArrayList<UserModel> list;

    public AdminClientAdapter(Context context, ArrayList<UserModel> list) {
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
                    .inflate(R.layout.activity_admin_client_view, parent, false);
        }

        TextView email = convertView.findViewById(R.id.clientEmail);
        TextView date = convertView.findViewById(R.id.clientPostalAddress);

        UserModel user = list.get(position);

        email.setText(user.email);
        date.setText(user.registeredDate);

        return convertView;
    }
}


