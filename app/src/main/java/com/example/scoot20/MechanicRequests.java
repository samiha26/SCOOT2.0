package com.example.scoot20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MechanicRequests extends AppCompatActivity {

    DatabaseReference databaseBD;
    ListView listViewRequest;
    List<BookingDetails> requestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_requests);

        databaseBD = FirebaseDatabase.getInstance().getReference("BookingDetails");
        listViewRequest = (ListView) findViewById(R.id.listViewRequest);
        requestList = new ArrayList<>();

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.mech_bottom_navigation);
        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.request);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.order:
                        startActivity(new Intent(getApplicationContext(),MechanicOrders.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.request:
                        return true;
                    case R.id.ewalletmechanic:
                        startActivity(new Intent(getApplicationContext(),MechanicEWallet.class));
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
        databaseBD.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                requestList.clear();
                for(DataSnapshot customerSnapshot : snapshot.getChildren()){
                    for(DataSnapshot uniqueSnapshot : customerSnapshot.getChildren()){
                        BookingDetails bd = uniqueSnapshot.getValue(BookingDetails.class);
                        if(!bd.isHasMechanic())
                            requestList.add(bd);
                    }
                }
                RequestList adapter = new RequestList(MechanicRequests.this, requestList);
                listViewRequest.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}