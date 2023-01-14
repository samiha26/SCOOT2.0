package com.example.scoot20;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DAOBookingDetails {
    private FirebaseAuth authProfile;
    private DatabaseReference databaseReference;
    public DAOBookingDetails(){

        authProfile = FirebaseAuth.getInstance();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("BookingDetails");
    }
    public Task<Void> add(BookingDetails bd){
        String key = authProfile.getCurrentUser().getUid();
        String BDKey = databaseReference.child(key).push().getKey();
        bd.setKey(BDKey);
        return databaseReference.child(key).child(BDKey).setValue(bd);
    }
}
