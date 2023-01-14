package com.example.scoot20;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddEScooter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_escooter);
        DatabaseReference databaseEScooter = FirebaseDatabase.getInstance().getReference("E-Scooter");
        FirebaseAuth authProfile = FirebaseAuth.getInstance();

        EditText ETScooterModel = findViewById(R.id.ETScooterModel);
        EditText ETScooterRegistration = findViewById(R.id.ETScooterRegistration);
        Button CreateEScooter = findViewById(R.id.BtnCreateEScooter);
        CreateEScooter.setOnClickListener(v -> {
            EScooter eScooter = new EScooter(ETScooterModel.getText().toString(), ETScooterRegistration.getText().toString());
            String key = authProfile.getCurrentUser().getUid();
            String scooterKey = databaseEScooter.child(key).push().getKey();
            eScooter.setScooterKey(scooterKey);
            databaseEScooter.child(key).child(scooterKey).setValue(eScooter);
            Toast.makeText(this, "Record has been inserted", Toast.LENGTH_SHORT).show();
        });
    }
}