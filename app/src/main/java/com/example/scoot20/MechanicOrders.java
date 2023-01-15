package com.example.scoot20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MechanicOrders extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_orders);
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
}