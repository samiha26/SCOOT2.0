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

import java.util.List;

public class BookingDetailList extends ArrayAdapter<BookingDetails> {
    private Activity context;
    private List<BookingDetails> bookingDetailsList;

    public BookingDetailList(Activity context, List<BookingDetails> bookingDetailsList) {
        super(context, R.layout.booking_detail_list, bookingDetailsList);
        this.context = context;
        this.bookingDetailsList = bookingDetailsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        BookingDetails bookingDetails = bookingDetailsList.get(position);

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.booking_detail_list, parent, false);
        TextView txt_phoneNumber = (TextView) listViewItem.findViewById(R.id.txt_phoneNumber);
        TextView txt_escooterModel = (TextView) listViewItem.findViewById(R.id.txt_escooterModel);
        TextView txt_sDate = (TextView) listViewItem.findViewById(R.id.txt_sDate);
        TextView txt_MechanicName = (TextView) listViewItem.findViewById(R.id.txt_MechanicName);
        TextView txt_Status = (TextView) listViewItem.findViewById(R.id.txt_Status);
        TextView txt_Price = (TextView) listViewItem.findViewById(R.id.txt_Price);
        ImageButton BtnPayOrder = (ImageButton) listViewItem.findViewById(R.id.BtnPayOrder);

        txt_phoneNumber.setText(bookingDetails.getdPhone());
        txt_escooterModel.setText(bookingDetails.getModel());
        txt_sDate.setText(bookingDetails.getDate());
        if(bookingDetails.isHasMechanic() && !bookingDetails.isMechanicDone()) {
            txt_MechanicName.setText(bookingDetails.getMechanicName());
            txt_Status.setText("Mechanic fixing");
        }
        if(bookingDetails.isMechanicDone()){
            BtnPayOrder.setVisibility(View.VISIBLE);
            BtnPayOrder.setEnabled(true);
            txt_Price.setText(bookingDetails.getPrice());
            txt_Status.setText("Mechanic done. Please pay.");
        }
        BtnPayOrder.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(),EWalletPay.class).putExtra("BookingDetails",bookingDetails);
            context.startActivity(intent);
        });
        return listViewItem;
    }

}

