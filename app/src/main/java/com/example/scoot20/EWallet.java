package com.example.scoot20;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EWallet extends AppCompatActivity {

    TextView balanceTextView;
    FirebaseAuth authProfile;
    DatabaseReference databaseEwallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ewallet);

        //initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.ewallet);

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
                    case R.id.ewallet:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        //initialize views
        balanceTextView = findViewById(R.id.TxtAmountEwallet);


        ImageButton topupButton = findViewById(R.id.BtnTopupEwallet);
        ImageButton payButton = findViewById(R.id.BtnPayEwallet);
        ImageButton transactionButton = findViewById(R.id.BtnTransactionEwallet);
        ImageButton ewalletSetting = findViewById(R.id.BtnEwalletSetting);
        //Set money amount
        authProfile = FirebaseAuth.getInstance();
        String userKey = authProfile.getCurrentUser().getUid();
        databaseEwallet = FirebaseDatabase.getInstance().getReference("E-Wallet").child(userKey);
        databaseEwallet.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Double amount = snapshot.child("balance").getValue(Double.class);
                balanceTextView.setText(amount.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //set onClick Listeners
        topupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open topup activity
                Intent intent = new Intent(EWallet.this, EWalletTopup.class);
                startActivity(intent);
            }
        });

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open pay activity
                Intent intent = new Intent(EWallet.this, EWalletPay.class);
                startActivity(intent);
            }
        });

        transactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(), EWalletTransactions.class));
                Intent intent = new Intent(EWallet.this, EWalletTransactions.class);
                startActivity(intent);
            }
        });

        ewalletSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create a new intent to open the e-wallet setting activity
                startActivity(new Intent(getApplicationContext(), EWalletSetting.class));
            }
        });
    }
}