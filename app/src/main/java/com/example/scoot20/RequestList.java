package com.example.scoot20;

import android.app.Activity;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class RequestList extends ArrayAdapter<BookingDetails> {
    FirebaseAuth authProfile = FirebaseAuth.getInstance();
    DatabaseReference databaseMechanic;
    DatabaseReference databaseBD;
    private Activity context;
    private List<BookingDetails> requestList;
    DAOBookingDetails daoBookingDetails = new DAOBookingDetails();
    DAOOrder daoOrder = new DAOOrder();

    public RequestList(Activity context, List<BookingDetails> requestList) {
        super(context, R.layout.list_item_request, requestList);
        this.context = context;
        this.requestList = requestList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        BookingDetails cust = requestList.get(position);

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_item_request, parent, false);
        TextView txt_name = (TextView) listViewItem.findViewById(R.id.txt_name);
        TextView txt_date = (TextView) listViewItem.findViewById(R.id.txt_date);
        TextView txt_time = (TextView) listViewItem.findViewById(R.id.txt_time);
        TextView txt_model = (TextView) listViewItem.findViewById(R.id.txt_model);
        ImageButton BtnAccept = (ImageButton) listViewItem.findViewById(R.id.BtnAccept);
        BtnAccept.setOnClickListener(v -> {
            AcceptRequest(position);
        });

        txt_name.setText(cust.getName());
        txt_date.setText(cust.getDate());
        txt_time.setText(cust.getTime());
        txt_model.setText(cust.getModel());

        return listViewItem;
    }
    public void AcceptRequest(int position){
        BookingDetails BookingDetails = requestList.get(position);
        Order order = BookingDetails.toOrder(BookingDetails);
        databaseBD = FirebaseDatabase.getInstance().getReference("BookingDetails").child(BookingDetails.getParentKey()).child(BookingDetails.getKey());
        order.setUserID(BookingDetails.getParentKey());
        order.setBookingID(BookingDetails.getKey());
        daoOrder.add(order);
        String key = authProfile.getCurrentUser().getUid();
        BookingDetails.setHasMechanic(true);
        BookingDetails.setMechanicID(key);
        BookingDetails.setOrderID(order.getKey());
        databaseBD.setValue(BookingDetails);
        databaseMechanic = FirebaseDatabase.getInstance().getReference("Registered Mechanics").child(key).child("fullName");
        databaseMechanic.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String mechanicName = snapshot.getValue(String.class);
                BookingDetails.setMechanicName(mechanicName);
                databaseBD.setValue(BookingDetails);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        this.notifyDataSetChanged();
    }

}

