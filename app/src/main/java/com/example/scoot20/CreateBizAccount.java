package com.example.scoot20;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class CreateBizAccount extends Fragment {
    Button UploadBizLicense;
    Button RegisterBizAccount;
    ImageView PreviewBizLicense;
    EditText BizNameEdit1;
    EditText BizMailEdit1;
    EditText BizNumberEdit1;
    EditText BizAddressEdit1;
    int SELECT_PICTURE = 200;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_create_biz_account, container, false);
        //setting id to the corresponding type
        UploadBizLicense = v.findViewById(R.id.UploadBizLicense);
        PreviewBizLicense = v.findViewById(R.id.PreviewBizLicense);
        RegisterBizAccount = v.findViewById(R.id.RegisterBizAccount);
        //setting id for mechanic profile
        BizNameEdit1 = v.findViewById(R.id.BizNameEdit1);
        BizMailEdit1 = v.findViewById(R.id.BizMailEdit1);
        BizNumberEdit1 = v.findViewById(R.id.BizNumberEdit1);
        BizAddressEdit1 = v.findViewById(R.id.BizAddressEdit1);
        UploadBizLicense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
        RegisterBizAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("name",BizNameEdit1.getText().toString());
                bundle.putString("mail",BizMailEdit1.getText().toString());
                bundle.putString("number",BizNumberEdit1.getText().toString());
                Navigation.findNavController(v).navigate(R.id.CreateBizAccToManageMechanicAcc, bundle);
            }
        });

        return v;
    }
    //code for picture chooser
    void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == -1) {
            if(requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if(null != selectedImageUri){
                    PreviewBizLicense.setImageURI(selectedImageUri);
                }
            }
        }
    }
}