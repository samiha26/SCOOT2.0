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

import java.util.List;
/*
public class RequestList extends ArrayAdapter<Customer> {
    private Activity context;
    private List<Customer> requestList;
    DAOCustomer daoCustomer = new DAOCustomer();
    DAOOrder daoOrder = new DAOOrder();

    public RequestList(Activity context, List<Customer> requestList) {
        super(context, R.layout.layout_item, requestList);
        this.context = context;
        this.requestList = requestList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Customer cust = requestList.get(position);

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_item, parent, false);
        TextView txt_name = (TextView) listViewItem.findViewById(R.id.txt_name);
        TextView txt_date = (TextView) listViewItem.findViewById(R.id.txt_date);
        TextView txt_time = (TextView) listViewItem.findViewById(R.id.txt_time);
        TextView txt_model = (TextView) listViewItem.findViewById(R.id.txt_model);
        ImageButton BtnAccept = (ImageButton) listViewItem.findViewById(R.id.BtnAccept);
        BtnAccept.setOnClickListener(v -> {
            AcceptRequest(position);
        });
        ImageButton BtnReject = (ImageButton) listViewItem.findViewById(R.id.BtnReject);
        BtnReject.setOnClickListener(v -> {
            RejectRequest(position);
        });

        txt_name.setText(cust.getName());
        txt_date.setText(cust.getDate());
        txt_time.setText(cust.getTime());
        txt_model.setText(cust.getModel());

        return listViewItem;
    }
    public void AcceptRequest(int position){
        Customer customer = requestList.get(position);
        Order order = Customer.toOrder(customer);
        daoOrder.add(order);
        daoCustomer.remove(customer.getKey());
        this.notifyDataSetChanged();
    }
    public void RejectRequest(int position){
        Customer customer = requestList.get(position);
        daoCustomer.remove(customer.getKey());
        this.notifyDataSetChanged();
    }
}
*/
