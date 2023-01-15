package com.example.scoot20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class SignInMechanic extends AppCompatActivity {
    private EditText editTxtMechanicLoginEmail, editTxtMechanicLoginPassword;
    private FirebaseAuth authProfile;
    private static final String TAG = "Sign In Mechanic";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_mechanic);
        getSupportActionBar().setTitle("Login BIZ");

        editTxtMechanicLoginEmail = findViewById(R.id.editTxtMechanicLoginEmail);
        editTxtMechanicLoginPassword = findViewById(R.id.editTxtMechanicLoginPassword);

        authProfile = FirebaseAuth.getInstance();

        Button btnForgotPassword = findViewById(R.id.btnMechanicForgotPassword);
        btnForgotPassword.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(SignInMechanic.this, "You can reset your password now", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignInMechanic.this, ForgotPassword.class));
            }
        });

        //SignIn Mechanic
        Button btnLogin = findViewById(R.id.btnLoginMechanic);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textEmail = editTxtMechanicLoginEmail.getText().toString();
                String textPassword = editTxtMechanicLoginPassword.getText().toString();

                if (TextUtils.isEmpty(textEmail)){
                    Toast.makeText(SignInMechanic.this, "Please enter your email", Toast.LENGTH_LONG).show();
                    editTxtMechanicLoginEmail.setError("Email is required");
                    editTxtMechanicLoginEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()){
                    Toast.makeText(SignInMechanic.this, "Please re-enter your email", Toast.LENGTH_LONG).show();
                    editTxtMechanicLoginEmail.setError("Valid email is required");
                    editTxtMechanicLoginEmail.requestFocus();
                } else if (TextUtils.isEmpty(textPassword)){
                    Toast.makeText(SignInMechanic.this, "Please enter your password", Toast.LENGTH_LONG).show();
                    editTxtMechanicLoginPassword.setError("Password is required");
                    editTxtMechanicLoginPassword.requestFocus();
                } else {
                    signInMechanic(textEmail, textPassword);
                }
            }
        });
    }

    private void signInMechanic(String email, String password) {
        authProfile.signInWithEmailAndPassword(email, password).addOnCompleteListener(SignInMechanic.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    //Get instance of the current User
                    FirebaseUser firebaseUser = authProfile.getCurrentUser();

                    //Check if email is verified before user can access their profile
                    if(firebaseUser.isEmailVerified()){
                        Toast.makeText(SignInMechanic.this, "You are Signed In now", Toast.LENGTH_SHORT).show();

                        //Open user Profile
                        //Start Home
                        startActivity(new Intent(SignInMechanic.this, MechanicRequests.class));
                        finish(); //Close the Sign In Activity

                    }
                    else{
                        firebaseUser.sendEmailVerification();
                        authProfile.signOut(); //Sign out User
                        showAlertDialog();
                    }


                } else {
                    try{
                        throw task.getException();
                    } catch(FirebaseAuthInvalidUserException e){ //if User no longer exist or deleted by admin
                        editTxtMechanicLoginEmail.setError("User does not exist or is no longer valid. Please register again");
                        editTxtMechanicLoginEmail.requestFocus();
                    } catch(FirebaseAuthInvalidCredentialsException e){ //
                        editTxtMechanicLoginEmail.setError("Invalid credentials. Kindly, check and re-enter.");
                        editTxtMechanicLoginEmail.requestFocus();
                    } catch(Exception e){
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(SignInMechanic.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void showAlertDialog() {
        //Setup the Alert Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(SignInMechanic.this);
        builder.setTitle("Email Not Verified");
        builder.setMessage("Please verify your email now. You cannot login without email verification.");

        //Open Email Apps if User clicks/taps Continue button
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //To direct to external email app in new window
                startActivity(intent);
            }
        });

        //Create the AlertDialog
        AlertDialog alertDialog = builder.create();

        //Show the AlertDialog
        alertDialog.show();
    }

    //Check if User is already logged in. In such case, straightaway take the User to Home
    @Override
    protected void onStart(){
        super.onStart();
        if(authProfile.getCurrentUser() != null) {
            Toast.makeText(SignInMechanic.this, "Already Signed In", Toast.LENGTH_LONG).show();

            //Start Home
            startActivity(new Intent(SignInMechanic.this, MechanicRequests.class));
            finish(); //Close SignIn

        } else{
            Toast.makeText(SignInMechanic.this, "You can Sign In now", Toast.LENGTH_LONG).show();
        }
    }

}