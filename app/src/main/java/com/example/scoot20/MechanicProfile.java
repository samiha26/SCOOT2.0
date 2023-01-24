package com.example.scoot20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MechanicProfile extends AppCompatActivity {

    FirebaseAuth authProfile;
    DatabaseReference databaseUser;
    private ImageView btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_profile);

        ImageView btnLogOut = findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authProfile.signOut();
                startActivity(new Intent(MechanicProfile.this, SignInAndRegister.class));
                finish();
            }
        });

        authProfile = FirebaseAuth.getInstance();
        String key = authProfile.getCurrentUser().getUid();
        //Displaying Profile Information
        TextView txt_profileName = findViewById(R.id.txt_profileName);
        TextView txt_profileMail = findViewById(R.id.txt_profileMail);
        TextView txt_profileNumber = findViewById(R.id.txt_profileNumber);
        databaseUser = FirebaseDatabase.getInstance().getReference("Registered Mechanics").child(key);
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
        //Edit User Details
        EditText ETFirstNameEdit = findViewById(R.id.ETFirstNameEdit);
        EditText ETLastNameEdit = findViewById(R.id.ETLastNameEdit);
        EditText ETPhoneNumberEdit = findViewById(R.id.ETPhoneNumberEdit);
        Button BtnEditUser = findViewById(R.id.BtnEditUser);
        BtnEditUser.setOnClickListener(v -> {
            String firstName = ETFirstNameEdit.getText().toString();
            String lastName = ETLastNameEdit.getText().toString();
            String phoneNumber = ETPhoneNumberEdit.getText().toString();
            if(!firstName.isEmpty()) {
                databaseUser.child("firstName").setValue(firstName);
                Toast.makeText(this, "Record has been updated", Toast.LENGTH_SHORT).show();
            }
            if(!lastName.isEmpty()) {
                databaseUser.child("lastName").setValue(lastName);
                Toast.makeText(this, "Record has been updated", Toast.LENGTH_SHORT).show();
            }
            if(!phoneNumber.isEmpty()) {
                databaseUser.child("mobileNo").setValue(phoneNumber);
                Toast.makeText(this, "Record has been updated", Toast.LENGTH_SHORT).show();
            }
        });
        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.mech_bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.mechanicprofile);

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
                    case R.id.mechanicprofile:
                        return true;
                    case R.id.ewalletmechanic:
                        startActivity(new Intent(getApplicationContext(),CreateEWalletMechanic.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.order:
                        startActivity(new Intent(getApplicationContext(),MechanicOrders.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }
}