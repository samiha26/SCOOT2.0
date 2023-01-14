package com.example.scoot20;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class EScooterList extends ArrayAdapter<EScooter> {
    private Activity context;
    private List<EScooter> eScooterList;

    public EScooterList(Activity context, List<EScooter> eScooterList) {
        super(context, R.layout.e_scooter_list, eScooterList);
        this.context = context;
        this.eScooterList = eScooterList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        EScooter eScooter = eScooterList.get(position);

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.e_scooter_list, parent, false);
        TextView txt_ProfileScooterModel = (TextView) listViewItem.findViewById(R.id.txt_ProfileScooterModel);
        TextView txt_ProfileScooterRegistration = (TextView) listViewItem.findViewById(R.id.txt_ProfileScooterRegistration);

        txt_ProfileScooterModel.setText(eScooter.getModel());
        txt_ProfileScooterRegistration.setText(eScooter.getRegNo());

        return listViewItem;
    }

}

