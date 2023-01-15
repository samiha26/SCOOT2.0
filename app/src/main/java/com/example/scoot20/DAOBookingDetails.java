package com.example.scoot20;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DAOBookingDetails {
    private FirebaseAuth authProfile;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseUsers;
    public DAOBookingDetails(){

        authProfile = FirebaseAuth.getInstance();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("BookingDetails");
        databaseUsers = db.getReference("Registered Users");
    }
    public Task<Void> add(BookingDetails bd){
        String key = authProfile.getCurrentUser().getUid();
        String BDKey = databaseReference.child(key).push().getKey();
        databaseUsers.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("fullName").getValue(String.class);
                bd.setName(name);
                databaseReference.child(key).child(BDKey).setValue(bd);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        bd.setKey(BDKey);
        return databaseReference.child(key).child(BDKey).setValue(bd);
    }
    public Task<Void> remove(String key){
        return databaseReference.child(key).removeValue();
    }
}
