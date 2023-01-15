package com.example.scoot20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MechanicEWallet extends AppCompatActivity {
    TextView balanceTextView;
    FirebaseAuth authProfile;
    DatabaseReference databaseEwallet;
    ListView listViewTransaction;
    List<Transaction> transactionList;
    DatabaseReference databaseTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_ewallet);
        authProfile = FirebaseAuth.getInstance();
        String key = authProfile.getCurrentUser().getUid();
        databaseTransaction = FirebaseDatabase.getInstance().getReference("Transaction").child(key);

        listViewTransaction = (ListView) findViewById(R.id.listView_mechanicTransaction);
        transactionList = new ArrayList<>();

        //initialize views
        balanceTextView = findViewById(R.id.TxtAmountEwallet);

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
        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.mech_bottom_navigation);
        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.ewalletmechanic);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.request:
                        startActivity(new Intent(getApplicationContext(),MechanicRequests.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.ewalletmechanic:
                        return true;
                    case R.id.order:
                        startActivity(new Intent(getApplicationContext(),MechanicOrders.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.mechanicprofile:
                        startActivity(new Intent(getApplicationContext(),MechanicProfile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
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
                TransactionListAdapter adapter = new TransactionListAdapter(MechanicEWallet.this, transactionList);
                listViewTransaction.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}