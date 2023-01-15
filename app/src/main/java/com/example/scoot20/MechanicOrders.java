package com.example.scoot20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MechanicOrders extends AppCompatActivity implements Serializable {

    DatabaseReference databaseOrder;
    ListView listViewOrder;
    List<Order> orderList;
    FirebaseAuth authProfile = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_orders);
        String mechanicKey = authProfile.getCurrentUser().getUid();
        databaseOrder = FirebaseDatabase.getInstance().getReference("Order").child(mechanicKey);
        listViewOrder = (ListView) findViewById(R.id.listViewOrder);
        orderList = new ArrayList<>();
        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.mech_bottom_navigation);
        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.order);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.request:
                        startActivity(new Intent(getApplicationContext(),MechanicRequests.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.order:
                        return true;
                    case R.id.ewalletmechanic:
                        startActivity(new Intent(getApplicationContext(),CreateEWalletMechanic.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.mechanicprofile:
                        startActivity(new Intent(getApplicationContext(),MechanicProfile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderList.clear();
                for(DataSnapshot orderSnapshot : snapshot.getChildren()){
                    Order order = orderSnapshot.getValue(Order.class);
                    if(!order.isHasPaid())
                        orderList.add(order);
                }
                OrderList adapter = new OrderList(MechanicOrders.this, orderList);
                listViewOrder.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}