package com.example.scoot20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class EWalletPay extends AppCompatActivity {

    ImageButton backToEwalletMenuFromPay;
    private EditText mPayToEditText;
    private EditText mPayAmountEditText;
    private SharedPreferences mPrefs;
    private final String AMOUNT_KEY = "amount";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ewallet_pay);

        //initialize sharedPreferences
        mPrefs = getSharedPreferences(AMOUNT_KEY, MODE_PRIVATE);

        mPayToEditText = findViewById(R.id.editTextPayTo);
        mPayAmountEditText = findViewById(R.id.editTextEnterAmount);

        backToEwalletMenuFromPay = findViewById(R.id.BtnBackToEWalletFromPay);
        backToEwalletMenuFromPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EWallet.class));
            }
        });

        Button payButton =findViewById(R.id.BtnSubmitPay);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String payTo = mPayToEditText.getText().toString();
                String payAmountString = mPayAmountEditText.getText().toString();
                double payAmount = Double.parseDouble(payAmountString);

                //retrieve current amount from sharedPreferences
                double currentAmount =mPrefs.getFloat(AMOUNT_KEY, 0);
                currentAmount -= payAmount;

                //store the updated amount
                SharedPreferences.Editor editor = mPrefs.edit();
                editor.putFloat(AMOUNT_KEY, (float) currentAmount);
                editor.apply();

                //to subtract payAmount from current amount in EWallet activity
                Intent intent =new Intent();
                intent.putExtra("payTo", payTo);
                intent.putExtra("payAMount", payAmount);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}