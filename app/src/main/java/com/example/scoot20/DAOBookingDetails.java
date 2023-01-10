package com.example.scoot20;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DAOBookingDetails {

    private DatabaseReference databaseReference;
    public DAOBookingDetails(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference= db.getReference(BookingDetails.class.getSimpleName());
    }
    public Task<Void> add(BookingDetails bd){ return databaseReference.push().setValue(bd);
    }
}
