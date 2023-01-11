package com.example.scoot20;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileInformation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_information);
        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.profile);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.activities:
                        startActivity(new Intent(getApplicationContext(),ViewBookingActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        return true;
                    case R.id.ewallet:
                        startActivity(new Intent(getApplicationContext(),EWallet.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.message:
                        startActivity(new Intent(getApplicationContext(),Message.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        // Initialize and assign variable
        BottomNavigationView profileNavigationView = findViewById(R.id.profile_navigation);
        // Set Item selected
        profileNavigationView.setSelectedItemId(R.id.myinformation);
        // Perform item selected listener
        profileNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.myaddress:
                        startActivity(new Intent(getApplicationContext(),ProfileAddress.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.myinformation:
                        return true;
                    case R.id.myescooter:
                        startActivity(new Intent(getApplicationContext(),Profile.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });
        // Perform Fix With Scoot
        /*
        Button FixWithScootButton = findViewById(R.id.FixWithScootButton);
        FixWithScootButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CreateBizAccount.class));
            }
        }); */
    }
}