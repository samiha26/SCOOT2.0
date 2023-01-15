package com.example.scoot20;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DAOOrder {
    private DatabaseReference databaseReference;
    private FirebaseAuth authProfile = FirebaseAuth.getInstance();
    public DAOOrder(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Order.class.getSimpleName());
    }
    public Task<Void> add(Order order){
        String mechanicKey = authProfile.getCurrentUser().getUid();
        String key = databaseReference.push().getKey();
        order.setKey(key);
        order.setStatus(false);
        return databaseReference.child(mechanicKey).child(key).setValue(order);
    }
}
