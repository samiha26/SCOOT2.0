package com.example.scoot20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class QuickBooking extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_booking);

        //back button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //
        Button BookNowButton = findViewById(R.id.BookNowButton);
        BookNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ViewBookingActivity.class));
            }
        });

        ImageView backButton = findViewById(R.id.back_button);

        final EditText edit_servD = findViewById(R.id.servDate);
        final EditText edit_servT = findViewById(R.id.servTime);
        final EditText edit_pickAdd = findViewById(R.id.pickupAddText);
        final EditText edit_pickPhone = findViewById(R.id.pickphone);
        final EditText edit_dropAdd = findViewById(R.id.dropoffAddText);
        final EditText edit_dropPhone = findViewById(R.id.dropphone);
        final EditText ETModel = findViewById(R.id.ETModel);
        DAOBookingDetails dao = new DAOBookingDetails();

        BookNowButton.setOnClickListener(v -> {

            BookingDetails db = new BookingDetails(edit_servD.getText().toString(),edit_servT.getText().toString(),
                    edit_pickAdd.getText().toString(),edit_pickPhone.getText().toString(),edit_dropAdd.getText().toString(),edit_dropPhone.getText().toString(),
                    ETModel.getText().toString());
            dao.add(db).addOnSuccessListener(suc ->{
                Toast.makeText(this, "Recorded!", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(er ->{
                Toast.makeText(this, er.getMessage(), Toast.LENGTH_SHORT).show();
            });

        });
    }
    // this event will enable the back
    // function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBookNow(View v){
        Intent intent = new Intent(this, ViewBookingActivity.class);
        startActivity(intent);
    }
}
