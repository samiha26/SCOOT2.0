package com.example.scoot20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    private EditText editTxtFirstName, editTxtLastName, editTextMobileNo, editTxtEmailAddress, editTxtConfirmPassword, editTxtPassword;
    private CheckBox checkBox;
    private static final String TAG = "Register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setTitle("Register");

        Toast.makeText(Register.this, "You can register now", Toast.LENGTH_LONG).show();

        editTxtFirstName = findViewById(R.id.editTxtFirstName);
        editTxtLastName = findViewById(R.id.editTxtLastName);
        editTextMobileNo = findViewById(R.id.editTxtMobileNo);
        editTxtEmailAddress = findViewById(R.id.editTxtEmailAddress);
        editTxtPassword = findViewById(R.id.editTxtPassword);
        editTxtConfirmPassword = findViewById(R.id.editTxtConfirmPassword);

        //for checkbox
        checkBox = findViewById(R.id.checkBox);


        Button buttonRegister = findViewById(R.id.btnCreateAccount);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //obtaining input data
                String textFirstName = editTxtFirstName.getText().toString();
                String textLastName = editTxtLastName.getText().toString();
                String textMobileNo = editTextMobileNo.getText().toString();
                String textEmailAddress = editTxtEmailAddress.getText().toString();
                String textPassword = editTxtPassword.getText().toString();
                String textConfirmPassword = editTxtConfirmPassword.getText().toString();

                //validate Mobile Number using Matcher and Pattern (Regular Expression)
                String mobileRegex = "[6][0-9]{9}"; //First no. can be only be 6 and rest can be any no.
                Matcher mobileMatcher;
                Pattern mobilePattern = Pattern.compile(mobileRegex);
                mobileMatcher = mobilePattern.matcher(textMobileNo);


                if (TextUtils.isEmpty(textFirstName)) {
                    Toast.makeText(Register.this, "Please enter your first name", Toast.LENGTH_LONG).show();
                    editTxtFirstName.setError("First Name is required");
                    editTxtFirstName.requestFocus();
                } else if (TextUtils.isEmpty(textLastName)) {
                    Toast.makeText(Register.this, "Please enter your last name", Toast.LENGTH_LONG).show();
                    editTxtLastName.setError("Last Name is required");
                    editTxtLastName.requestFocus();
                } else if (TextUtils.isEmpty(textMobileNo)) {
                    Toast.makeText(Register.this, "Please enter your mobile no", Toast.LENGTH_LONG).show();
                    editTextMobileNo.setError("Mobile Number is required");
                    editTextMobileNo.requestFocus();
                } else if (!mobileMatcher.find()){
                    Toast.makeText(Register.this, "Please re-enter your mobile no", Toast.LENGTH_LONG).show();
                    editTextMobileNo.setError("Mobile Number is invalid");
                    editTextMobileNo.requestFocus();
                } else if (TextUtils.isEmpty(textEmailAddress)) {
                    Toast.makeText(Register.this, "Please enter your email address", Toast.LENGTH_LONG).show();
                    editTxtEmailAddress.setError("Email Address is required");
                    editTxtEmailAddress.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmailAddress).matches()) {
                    Toast.makeText(Register.this, "Please re-enter your email", Toast.LENGTH_LONG).show();
                    editTxtEmailAddress.setError("Valid email is required");
                    editTxtEmailAddress.requestFocus();
                } else if (TextUtils.isEmpty(textPassword)) {
                    Toast.makeText(Register.this, "Please enter your password", Toast.LENGTH_LONG).show();
                    editTxtPassword.setError("Password is required");
                    editTxtPassword.requestFocus();
                } else if (TextUtils.isEmpty(textConfirmPassword)) {
                    Toast.makeText(Register.this, "Please enter your confirmed password", Toast.LENGTH_LONG).show();
                    editTxtConfirmPassword.setError("Password Confirmation is required");
                    editTxtConfirmPassword.requestFocus();
                } else if (!textPassword.equals(textConfirmPassword)){
                    Toast.makeText(Register.this, "Please set the same password", Toast.LENGTH_LONG).show();
                    editTxtConfirmPassword.setError("Password Confirmation is required");
                    editTxtConfirmPassword.requestFocus();
                    //Clear the entered passwords
                    editTxtPassword.clearComposingText();
                    editTxtConfirmPassword.clearComposingText();
                } else if (!checkBox.isChecked()){
                    Toast.makeText(Register.this, "Please check this box to agree to our Terms of Service and Privacy Policy", Toast.LENGTH_LONG).show();
                    checkBox.setError("Your agreement to the Terms of Service and Privacy Policy is required");
                    checkBox.requestFocus();
                } else {

                    registerUser(textFirstName, textLastName, textMobileNo, textEmailAddress, textPassword);
                }
            }
        });
    }
    //Register User with given credentials
    private void registerUser(String textFirstName, String textLastName, String textMobileNo, String textEmailAddress, String textPassword) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(textEmailAddress, textPassword).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {

            String textFullName = textFirstName + " " + textLastName;
            @Override
            //TO CREATE USER
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    FirebaseUser firebaseUser = auth.getCurrentUser();//to make sure it is the user's email

                    //Update Display Name of User
                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(textFullName).build();
                    firebaseUser.updateProfile(profileChangeRequest);

                    //Enter User Data into the Firebase Realtime Database
                    ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(textFirstName, textLastName, textMobileNo);

                    //Extracting user reference from Database for "Registered Users"
                    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");

                    referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                //SendVerification Email
                                firebaseUser.sendEmailVerification();

                                Toast.makeText(Register.this, "User registration is successful! :D. Please verify your email", Toast.LENGTH_LONG).show();

                                //Open MainActivity after successful registration
                                Intent intent = new Intent(Register.this, MainActivity.class);

                                //To Prevent User from returning back to Register upon pressing back button after registration
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();   //to close Register

                            } else {
                                Toast.makeText(Register.this, "User registration is unsuccessful! :( Please try again", Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                } else {
                    try{
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        editTxtPassword.setError("Your password is too weak. Kindly use a mix of alphabets, numbers and special characters");
                        editTxtPassword.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        editTxtEmailAddress.setError("Your email is invalid or already in use. Kindly re-enter");
                        editTxtEmailAddress.requestFocus();
                    } catch (FirebaseAuthUserCollisionException e){
                        editTxtEmailAddress.setError("User is already registered with this email. Use another email.");
                        editTxtEmailAddress.requestFocus();
                    } catch (Exception e){
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
}