package com.example.scoot20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewBookingActivity extends AppCompatActivity {
    DatabaseReference databaseBookingDetails;
    ListView listViewBooking;
    List<BookingDetails> bookingDetailsList;
    FirebaseAuth authProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_booking);
        authProfile = FirebaseAuth.getInstance();
        String key = authProfile.getCurrentUser().getUid();
        databaseBookingDetails = FirebaseDatabase.getInstance().getReference("BookingDetails").child(key);

        listViewBooking = (ListView) findViewById(R.id.listViewBooking);
        bookingDetailsList = new ArrayList<>();


        Button BtnBookNow = findViewById(R.id.BtnBookNow);
        BtnBookNow.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),QuickBooking.class));
        });

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.activities);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.activities:
                        return true;
                    case R.id.ewallet:
                        startActivity(new Intent(getApplicationContext(),EWallet.class));
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseBookingDetails.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookingDetailsList.clear();
                for(DataSnapshot bookingSnapshot : snapshot.getChildren()){
                    BookingDetails bookingDetails = bookingSnapshot.getValue(BookingDetails.class);
                    bookingDetailsList.add(bookingDetails);
                }
                BookingDetailList adapter = new BookingDetailList(ViewBookingActivity.this, bookingDetailsList);
                listViewBooking.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}