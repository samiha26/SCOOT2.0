package com.example.scoot20;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DAOOrder {
    private DatabaseReference databaseReference;
    public DAOOrder(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Order.class.getSimpleName());
    }
    public Task<Void> add(Order order){
        String key = databaseReference.push().getKey();
        order.setKey(key);
        order.setStatus(false);
        return databaseReference.child(key).setValue(order);
    }
}
