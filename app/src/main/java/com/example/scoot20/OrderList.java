package com.example.scoot20;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
/*
public class OrderList extends ArrayAdapter<BookingDetails> {
    private Activity context;
    private List<BookingDetails> orderList;
    private DatabaseReference databaseOrder;

    public OrderList(Activity context, List<BookingDetails> orderList){
        super(context, R.layout.list_layout, orderList);
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Order order = orderList.get(position);

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout, parent, false);
        TextView txt_Ordername = (TextView) listViewItem.findViewById(R.id.txt_Ordername);
        TextView txt_Orderdate = (TextView) listViewItem.findViewById(R.id.txt_Orderdate);
        TextView txt_Ordertime = (TextView) listViewItem.findViewById(R.id.txt_Ordertime);
        TextView txt_Ordermodel = (TextView) listViewItem.findViewById(R.id.txt_Ordermodel);
        ImageButton BtnDone = (ImageButton) listViewItem.findViewById(R.id.BtnDone);
        BtnDone.setOnClickListener(v -> {
            updateOrderStatus(position,true);
        });

        txt_Ordername.setText(order.getName());
        txt_Orderdate.setText(order.getDate());
        txt_Ordertime.setText(order.getTime());
        txt_Ordermodel.setText(order.getModel());

        return listViewItem;
    }
    public void updateOrderStatus(int position, boolean status){
        BookingDetails order = orderList.get(position);
        order.setStatus(status);
        databaseOrder = FirebaseDatabase.getInstance().getReference("Order");
        databaseOrder.child(order.getKey()).child("status").setValue(status);
        this.notifyDataSetChanged();
    }
}
*/