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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

public class EWalletPay extends AppCompatActivity implements Serializable {

    ImageButton backToEwalletMenuFromPay;
    private TextView txt_PayTo;
    private EditText mPayAmountEditText;
    private DatabaseReference databaseEWallet;
    private DatabaseReference databaseEWalletMechanic;
    private DatabaseReference databaseBD;
    private DatabaseReference databaseOrder;
    private FirebaseAuth authProfile = FirebaseAuth.getInstance();
    private DatabaseReference databaseTransaction;
    private DatabaseReference databaseTransactionMechanic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ewallet_pay);
        String userKey = authProfile.getCurrentUser().getUid();
        databaseTransaction = FirebaseDatabase.getInstance().getReference("Transaction").child(userKey);
        final BookingDetails[] bookingDetails = {(BookingDetails) getIntent().getSerializableExtra("BookingDetails")};
        final double[] amountEWallet = new double[1];
        final double[] amountEWalletMechanic = new double[1];
        databaseEWallet = FirebaseDatabase.getInstance().getReference("E-Wallet").child(bookingDetails[0].getParentKey());
        databaseEWalletMechanic = FirebaseDatabase.getInstance().getReference("E-Wallet").child(bookingDetails[0].getMechanicID());
        //Get value of balance in User E-Wallet
        databaseEWallet.child("balance").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                double balance = snapshot.getValue(Double.class);
                amountEWallet[0] = balance;
                databaseEWallet.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Get value of balance in Mechanic E-Wallet
        databaseEWalletMechanic.child("balance").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                double balance = snapshot.getValue(Double.class);
                amountEWalletMechanic[0] = balance;
                databaseEWalletMechanic.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        txt_PayTo = findViewById(R.id.txt_PayTo);
        mPayAmountEditText = findViewById(R.id.editTextEnterAmount);

        txt_PayTo.setText(bookingDetails[0].getMechanicName());
        //Initializing strings
        double amountOwed = Double.parseDouble(bookingDetails[0].getPrice());

        Button payButton = findViewById(R.id.BtnSubmitPay);
        payButton.setOnClickListener(v -> {
            String amountET = mPayAmountEditText.getText().toString();
            double amountPaying = Double.parseDouble(amountET);
            if(amountEWallet[0] <amountOwed){
                Toast.makeText(this, "You don't have enough money, please topup.", Toast.LENGTH_LONG).show();
                finish();
            }
            else if(amountPaying!=amountOwed){
                Toast.makeText(this,"Please enter the exact amount owed.", Toast.LENGTH_LONG).show();
            }
            else{
                amountEWallet[0] = amountEWallet[0] - amountOwed;
                bookingDetails[0].setHasPaid(true);
                databaseBD = FirebaseDatabase.getInstance().getReference("BookingDetails").child(bookingDetails[0].getParentKey()).child(bookingDetails[0].getKey()).child("hasPaid");
                databaseBD.setValue(true);
                databaseOrder = FirebaseDatabase.getInstance().getReference("Order").child(bookingDetails[0].getMechanicID()).child(bookingDetails[0].getOrderID()).child("hasPaid");
                databaseOrder.setValue(true);
                databaseEWallet.child("balance").setValue(amountEWallet[0]);
                amountEWalletMechanic[0]+=amountOwed;
                databaseEWalletMechanic.child("balance").setValue(amountEWalletMechanic[0]);
                //Transaction
                String transID = databaseTransaction.push().getKey();
                Transaction transaction = new Transaction();
                transaction.setTransID(transID);
                transaction.setPayTo(bookingDetails[0].getMechanicID());
                transaction.setPayFrom(userKey);
                transaction.setPayAmount(amountOwed);
                databaseTransaction.child(transID).setValue(transaction);
                databaseTransactionMechanic = FirebaseDatabase.getInstance().getReference("Transaction").child(bookingDetails[0].getMechanicID());
                databaseTransactionMechanic.child(transID).setValue(transaction);
                finish();
            }
        });
        backToEwalletMenuFromPay = findViewById(R.id.BtnBackToEWalletFromPay);
        backToEwalletMenuFromPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}