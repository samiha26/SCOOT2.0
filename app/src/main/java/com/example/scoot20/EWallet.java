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

public class EWallet extends AppCompatActivity {

    private SharedPreferences mPrefs;
    private final String AMOUNT_KEY = "amount";
    TextView balanceTextView;
    int balance = 0;

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
        mPrefs =getPreferences(MODE_PRIVATE);

        //Retrieve the initial amount from sharedPreferences
        double initialAmount = mPrefs.getFloat(AMOUNT_KEY, 0);
        balanceTextView.setText(String.format("%.2f", initialAmount));
        // ...

        ImageButton topupButton = findViewById(R.id.BtnTopupEwallet);
        ImageButton payButton = findViewById(R.id.BtnPayEwallet);
        ImageButton transactionButton = findViewById(R.id.BtnTransactionEwallet);
        ImageButton ewalletSetting = findViewById(R.id.BtnEwalletSetting);

        //set initial balance amount
        //balanceTextView.setText(String.format("%d.5", balance));

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check which activity returned a result
        if (requestCode == 1) {
            // Top-up activity returned a result
            if (resultCode == RESULT_OK) {
                // Get top-up amount from intent
                int topUpAmount = data.getIntExtra("topUpAmount", 0);
                // Add top-up amount to balance
                balance += topUpAmount;
                // Update balance text view
                balanceTextView.setText(String.format("%d", balance));
            }
        } else if (requestCode == 2) {
            // Pay activity returned a result
            if (resultCode == RESULT_OK) {
                // Get pay amount from intent
                int payAmount = data.getIntExtra("payAmount", 0);
                // Subtract pay amount from balance
                balance -= payAmount;
                // Update balance text view
                balanceTextView.setText(String.format("%d", balance));
            }
        }
    }
}