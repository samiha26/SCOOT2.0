package com.example.scoot20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class EWalletTransactions extends AppCompatActivity {

    private ListView mTransactionListView;
    private ArrayList<Transaction> mTransactionList;
    private TransactionListAdapter mTransactionListAdapter;

    ImageButton backToEWalletFromTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ewallet_transactions);

        mTransactionListView = findViewById(R.id.listView_transactions);
        mTransactionList = new ArrayList<>();
        mTransactionListAdapter =new TransactionListAdapter(this, mTransactionList);
        mTransactionListView.setAdapter(mTransactionListAdapter);

        //retrieve payTo and payAmount from the intent and add to transaction list
        Intent intent =getIntent();
        String payTo =intent.getStringExtra("payTo");
        double payAmount =intent.getDoubleExtra("payAmount", 0);
        if (payTo != null && payAmount > 0) {
            addTransaction(payTo, payAmount);
        }

        backToEWalletFromTransaction = findViewById(R.id.BtnBackToEWalletFromTransaction);
        backToEWalletFromTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EWallet.class));
            }
        });
    }

    public void addTransaction(String payTo, double payAmount) {
        mTransactionList.add(new Transaction(payTo, payAmount));
        mTransactionListAdapter.notifyDataSetChanged();
    }
}