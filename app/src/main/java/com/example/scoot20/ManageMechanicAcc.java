package com.example.scoot20;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ManageMechanicAcc extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_manage_mechanic_acc, container, false);
        EditText BizNameEdit = view.findViewById(R.id.BizNameEdit);
        EditText BizMailEdit = view.findViewById(R.id.BizMailEdit);
        EditText BizNumberEdit = view.findViewById(R.id.BizNumberEdit);
        TextView BizNameUpdate = view.findViewById(R.id.BizNameUpdate);
        TextView BizMailUpdate = view.findViewById(R.id.BizMailUpdate);
        TextView BizNumberUpdate = view.findViewById(R.id.BizNumberUpdate);
        Button ChangePass = view.findViewById(R.id.ChangePass);
        Bundle bundle = this.getArguments();
        BizNameUpdate.setText(bundle.getString("name"));
        BizMailUpdate.setText(bundle.getString("mail"));
        BizNumberUpdate.setText(bundle.getString("number"));
        ChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BizNameUpdate.setText(BizNameEdit.getText());
                BizMailUpdate.setText(BizMailEdit.getText());
                BizNumberUpdate.setText(BizNumberEdit.getText());
            }
        });
        return view;
    }
}