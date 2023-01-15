package com.example.scoot20;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class TransactionListAdapter extends ArrayAdapter<Transaction> {
    private Activity context;
    private List<Transaction> transactionList;

    public TransactionListAdapter(Activity context, List<Transaction> transactionList) {
        super(context, R.layout.list_item_transaction, transactionList);
        this.context = context;
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Transaction transaction = transactionList.get(position);

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_item_transaction, parent, false);
        TextView textview_transID = (TextView) listViewItem.findViewById(R.id.textView_transID);
        TextView textview_payTo = (TextView) listViewItem.findViewById(R.id.textView_payTo);
        TextView textview_payAmount = (TextView) listViewItem.findViewById(R.id.textView_payAmount);

        textview_transID.setText(transaction.getTransID());
        textview_payTo.setText(transaction.getPayTo());
        textview_payAmount.setText(String.valueOf(transaction.getPayAmount()));

        return listViewItem;
    }

}

