package com.example.scoot20;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public class MechanicOrderPrice extends AppCompatActivity implements Serializable {
    private FirebaseAuth authProfile = FirebaseAuth.getInstance();
    private DatabaseReference db;
    private DatabaseReference databaseBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_order_price);
        Button BtnSubmitOrderPrice = findViewById(R.id.BtnSubmitOrderPrice);
        EditText ETOrderPrice = findViewById(R.id.ETOrderPrice);
        Order order = (Order)getIntent().getSerializableExtra("Order");
        BtnSubmitOrderPrice.setOnClickListener(v -> {
            order.setPrice(ETOrderPrice.getText().toString());
            String mechanicKey = authProfile.getCurrentUser().getUid();
            db = FirebaseDatabase.getInstance().getReference("Order").child(mechanicKey).child(order.getKey());
            db.setValue(order);
            databaseBD = FirebaseDatabase.getInstance().getReference("BookingDetails").child(order.getUserID()).child(order.getBookingID());
            databaseBD.child("price").setValue(order.getPrice());
            finish();
        });
    }
}