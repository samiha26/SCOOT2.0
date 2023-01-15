package com.example.scoot20;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SignInAndRegister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_and_register);

        //open Sign In Activity
        Button btnSignIn = findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInAndRegister.this, SignInUser.class);
                startActivity(intent);
            }
        });

        //open Register Activity
        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInAndRegister.this, RegisterUser.class);
                startActivity(intent);
            }
        });

        //open Sign In Mechanic Activity
        Button btnSignInMechanic = findViewById(R.id.btnSignInMechanic);
        btnSignInMechanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInAndRegister.this, SignInMechanic.class);
                startActivity(intent);
            }
        });

        //open Register Mechanic Activity
        Button btnRegisterMechanic = findViewById(R.id.btnRegisterMechanic);
        btnRegisterMechanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInAndRegister.this, RegisterMechanic.class);
                startActivity(intent);
            }
        });
    }
}