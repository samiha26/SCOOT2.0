package com.example.scoot20;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Profile extends AppCompatActivity {

    DatabaseReference databaseEScooter;
    ListView listViewEscooter;
    List<EScooter> eScooterList;
    FirebaseAuth authProfile;
    DatabaseReference databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        authProfile = FirebaseAuth.getInstance();
        String key = authProfile.getCurrentUser().getUid();

        databaseEScooter = FirebaseDatabase.getInstance().getReference("E-Scooter").child(key);
        listViewEscooter = (ListView) findViewById(R.id.listViewScooter);
        eScooterList = new ArrayList<>();

        //Displaying Profile Information
        TextView txt_profileName = findViewById(R.id.txt_profileName);
        TextView txt_profileMail = findViewById(R.id.txt_profileMail);
        TextView txt_profileNumber = findViewById(R.id.txt_profileNumber);
        databaseUser = FirebaseDatabase.getInstance().getReference("Registered Users").child(key);
        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String firstName = snapshot.child("firstName").getValue(String.class);
                String lastName = snapshot.child("lastName").getValue(String.class);
                String mobileNo = snapshot.child("mobileNo").getValue(String.class);
                txt_profileName.setText(firstName + " " + lastName);
                txt_profileNumber.setText(mobileNo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        String email = authProfile.getCurrentUser().getEmail();
        txt_profileMail.setText(email);
        //Button Coding
        Button BtnAddEScooter = findViewById(R.id.BtnAddEScooter);
        BtnAddEScooter.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, AddEScooter.class);
            startActivity(intent);
        });
        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.profile);

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
                    case R.id.profile:
                        return true;
                    case R.id.ewallet:
                        startActivity(new Intent(getApplicationContext(),CreateEWallet.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        // Initialize and assign variable
        BottomNavigationView profileNavigationView = findViewById(R.id.profile_navigation);
        // Set Item selected
        profileNavigationView.setSelectedItemId(R.id.myescooter);
        // Perform item selected listener
        profileNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.myescooter:
                        return true;
                    case R.id.myinformation:
                        startActivity(new Intent(getApplicationContext(),ProfileInformation.class));
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
        databaseEScooter.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                eScooterList.clear();
                for(DataSnapshot scooterSnapshot : snapshot.getChildren()){
                    EScooter eScooter = scooterSnapshot.getValue(EScooter.class);
                    eScooterList.add(eScooter);
                }
                EScooterList adapter = new EScooterList(Profile.this, eScooterList);
                listViewEscooter.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}