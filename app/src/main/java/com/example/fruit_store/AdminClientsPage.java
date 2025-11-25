package com.example.fruit_store;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdminClientsPage extends AppCompatActivity {

    ListView clientsList;
    TextView backArrow;

    DatabaseReference usersRef;
    ArrayList<UserModel> userList = new ArrayList<>();
    AdminClientAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_clients_page);

        clientsList = findViewById(R.id.clientsListView);
        backArrow = findViewById(R.id.backArrow);

        usersRef = FirebaseDatabase.getInstance().getReference("users");

        adapter = new AdminClientAdapter(this, userList);
        clientsList.setAdapter(adapter);

        backArrow.setOnClickListener(v -> finish());

        loadUsers();
    }

    private void loadUsers() {

        usersRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                userList.clear();

                for (DataSnapshot snap : snapshot.getChildren()) {

                    UserModel user = snap.getValue(UserModel.class);

                    if (user != null) {
                        userList.add(user);
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

}
