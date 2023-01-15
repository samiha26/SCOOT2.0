package com.example.scoot20;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.List;

public class OrderList extends ArrayAdapter<Order> implements Serializable {
    private Activity context;
    private List<Order> orderList;
    private DatabaseReference databaseOrder;
    private DatabaseReference databaseBD;
    private FirebaseAuth authProfile = FirebaseAuth.getInstance();

    public OrderList(Activity context, List<Order> orderList){
        super(context, R.layout.list_item_order, orderList);
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Order order = orderList.get(position);

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_item_order, parent, false);
        TextView txt_Ordername = (TextView) listViewItem.findViewById(R.id.txt_Ordername);
        TextView txt_Orderdate = (TextView) listViewItem.findViewById(R.id.txt_Orderdate);
        TextView txt_Ordertime = (TextView) listViewItem.findViewById(R.id.txt_Ordertime);
        TextView txt_Ordermodel = (TextView) listViewItem.findViewById(R.id.txt_Ordermodel);
        TextView txt_Orderprice = (TextView) listViewItem.findViewById(R.id.txt_Orderprice);
        TextView txt_Orderstatus = (TextView) listViewItem.findViewById(R.id.txt_Orderstatus);
        ImageButton BtnDone = (ImageButton) listViewItem.findViewById(R.id.BtnDone);
        BtnDone.setOnClickListener(v -> {
            updateOrderStatus(position,true);
            Intent intent = new Intent(getContext(),MechanicOrderPrice.class).putExtra("Order",order);
            context.startActivity(intent);
        });

        txt_Ordername.setText(order.getName());
        txt_Orderdate.setText(order.getDate());
        txt_Ordertime.setText(order.getTime());
        txt_Ordermodel.setText(order.getModel());
        txt_Orderprice.setText(order.getPrice());
        if(order.isStatus()) {
            txt_Orderstatus.setText("Done");
            BtnDone.setEnabled(false);
            BtnDone.setVisibility(View.GONE);
        }
        else
            txt_Orderstatus.setText("Fixing");
        return listViewItem;
    }
    public void updateOrderStatus(int position, boolean status){
        Order order = orderList.get(position);
        String mechanicKey = authProfile.getCurrentUser().getUid();
        order.setStatus(status);
        databaseBD = FirebaseDatabase.getInstance().getReference("BookingDetails").child(order.getUserID()).child(order.getBookingID());
        databaseBD.child("mechanicDone").setValue(status);
        databaseOrder = FirebaseDatabase.getInstance().getReference("Order");
        databaseOrder.child(mechanicKey).child(order.getKey()).child("status").setValue(status);
        this.notifyDataSetChanged();
    }
}
