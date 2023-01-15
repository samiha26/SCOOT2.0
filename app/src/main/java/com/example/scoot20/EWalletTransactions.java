package com.example.scoot20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EWalletTransactions extends AppCompatActivity {

    DatabaseReference databaseTransaction;
    ListView listViewTransaction;
    List<Transaction> transactionList;
    FirebaseAuth authProfile;

    ImageButton backToEWalletFromTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ewallet_transactions);
        authProfile = FirebaseAuth.getInstance();
        String key = authProfile.getCurrentUser().getUid();
        databaseTransaction = FirebaseDatabase.getInstance().getReference("Transaction").child(key);

        listViewTransaction = (ListView) findViewById(R.id.listView_transactions);
        transactionList = new ArrayList<>();


        backToEWalletFromTransaction = findViewById(R.id.BtnBackToEWalletFromTransaction);
        backToEWalletFromTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EWallet.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseTransaction.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                transactionList.clear();
                for (DataSnapshot transactionSnapshot : snapshot.getChildren()) {
                    Transaction transaction = transactionSnapshot.getValue(Transaction.class);
                    transactionList.add(transaction);
                }
                TransactionListAdapter adapter = new TransactionListAdapter(EWalletTransactions.this, transactionList);
                listViewTransaction.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}