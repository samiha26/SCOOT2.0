package com.example.scoot20;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class TransactionListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Transaction> mTransactionList;

    public TransactionListAdapter(Context context, List<Transaction> transactionList) {
        mContext = context;
        mTransactionList = transactionList;
    }

    @Override
    public int getCount() {
        return mTransactionList.size();
    }

    @Override
    public Object getItem(int position) {
        return mTransactionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_transaction, parent, false);
        }

        Transaction transaction = (Transaction) getItem(position);

        TextView paytoTextView = view.findViewById(R.id.textView_payTo);
        paytoTextView.setText(transaction.getPayTo());

        TextView payAmountTextView = view.findViewById(R.id.textView_payAmount);
        payAmountTextView.setText(String.format("%.2f", transaction.getPayAmount()));

        return view;
    }
}
