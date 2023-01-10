package com.example.scoot20;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class EWalletSetting extends AppCompatActivity {

    Button paymentMethods;
    Button topupMethods;
    Button pinEwallet;
    ImageButton backtoEwallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ewallet_setting);

        paymentMethods = findViewById(R.id.BtnAllPaymentMethodEWalletSetting);
        topupMethods = findViewById(R.id.BtnTopupMethodEwalletSetting);
        pinEwallet = findViewById(R.id.BtnPinEWalletSetting);
        backtoEwallet = findViewById(R.id.BtnBackToEwallet);

        backtoEwallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create new intent to go back to e-wallet menu
                Intent ewalletMenuIntent = new Intent(EWalletSetting.this, EWallet.class);

                //start the activity
                startActivity(ewalletMenuIntent);
            }
        });
    }
}