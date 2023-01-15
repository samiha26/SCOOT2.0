package com.example.scoot20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EWalletTopup extends AppCompatActivity {

    ImageButton backToEwalletMenu;
    Button topupbutton;
    EditText topupAmountEditText;
    FirebaseAuth authProfile;
    DatabaseReference databaseEwallet;
    DatabaseReference databaseTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ewallet_topup);

        backToEwalletMenu = findViewById(R.id.BtnBackToEWalletFromTopup);
        topupbutton = findViewById(R.id.BtnTopupMoney);
        topupAmountEditText = findViewById(R.id.editTextEnterMoneyAmount);
        authProfile = FirebaseAuth.getInstance();
        String userKey = authProfile.getCurrentUser().getUid();
        databaseEwallet = FirebaseDatabase.getInstance().getReference("E-Wallet").child(userKey);
        databaseTransaction = FirebaseDatabase.getInstance().getReference("Transaction").child(userKey);

        backToEwalletMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EWallet.class));
                finish();
            }
        });

        topupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String topUpAmountString = topupAmountEditText.getText().toString();
                if(topUpAmountString.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please enter topup amount", Toast.LENGTH_SHORT).show();
                    return;
                }
                double topUpAmount = Double.parseDouble(topUpAmountString);
                String transID = databaseTransaction.push().getKey();
                Transaction transaction = new Transaction();
                transaction.setTransID(transID);
                transaction.setPayTo(userKey);
                transaction.setPayAmount(topUpAmount);
                databaseTransaction.child(transID).setValue(transaction);

                databaseEwallet.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild("balance")){
                            Double amount = snapshot.child("balance").getValue(Double.class);
                            if (amount != null) {
                                double amountreal = amount.doubleValue();
                                double newBalance = amountreal + topUpAmount;
                                Map<String, Object> update = new HashMap<>();
                                update.put("balance", newBalance);
                                databaseEwallet.updateChildren(update);
                                databaseEwallet.removeEventListener(this);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle error
                    }
                });
                startActivity(new Intent(EWalletTopup.this, EWallet.class));
                finish(); //Close SignIn
            }
        });

    }
}