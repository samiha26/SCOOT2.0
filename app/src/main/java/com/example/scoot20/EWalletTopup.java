package com.example.scoot20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class EWalletTopup extends AppCompatActivity {

    ImageButton backToEwalletMenu;
    Button topupbutton;
    EditText topupAmountEditText;
    //int balance = 0;
    private SharedPreferences mPrefs;
    private final String AMOUNT_KEY = "amount";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ewallet_topup);

        // Initialize SharedPreferences object
        mPrefs = getSharedPreferences(AMOUNT_KEY, MODE_PRIVATE);

        backToEwalletMenu = findViewById(R.id.BtnBackToEWalletFromTopup);
        topupbutton = findViewById(R.id.BtnTopupMoney);
        topupAmountEditText = findViewById(R.id.editTextEnterMoneyAmount);

        backToEwalletMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EWallet.class));
            }
        });

        topupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String topUpAmountString = topupAmountEditText.getText().toString();
                double topUpAmount =Double.parseDouble(topUpAmountString);

                //retrieve current amount from sharedPreferences
                double currentAmount =mPrefs.getFloat(AMOUNT_KEY, 0);
                currentAmount += topUpAmount;

                //store updated amount
                SharedPreferences.Editor editor =mPrefs.edit();
                editor.putFloat(AMOUNT_KEY, (float) currentAmount);
                editor.apply();

                //add topUpAMountto current amount in E-wallet activity
                Intent intent = new Intent();
                intent.putExtra("topUpAMount", topUpAmount);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}