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

public class RegisterMechanic extends AppCompatActivity {
    private EditText editTxtMechanicFirstName, editTxtMechanicLastName, editTextMechanicMobileNo, editTxtMechanicEmailAddress, editTxtMechanicConfirmPassword, editTxtMechanicPassword;
    private CheckBox checkBoxMechanic;
    private static final String TAG = "Register Mechanic";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_mechanic);

        getSupportActionBar().setTitle("Register Mechanic");

        Toast.makeText(RegisterMechanic.this, "You can register now", Toast.LENGTH_LONG).show();

        editTxtMechanicFirstName = findViewById(R.id.editTxtMechanicFirstName);
        editTxtMechanicLastName = findViewById(R.id.editTxtMechanicLastName);
        editTextMechanicMobileNo = findViewById(R.id.editTxtMechanicMobileNo);
        editTxtMechanicEmailAddress = findViewById(R.id.editTxtMechanicEmailAddress);
        editTxtMechanicPassword = findViewById(R.id.editTxtMechanicPassword);
        editTxtMechanicConfirmPassword = findViewById(R.id.editTxtMechanicConfirmPassword);

        //for checkbox
        checkBoxMechanic = findViewById(R.id.checkBoxMechanic);


        Button buttonRegisterMechanic = findViewById(R.id.btnCreateBIZAccount);
        buttonRegisterMechanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //obtaining input data
                String textFirstName = editTxtMechanicFirstName.getText().toString();
                String textLastName = editTxtMechanicLastName.getText().toString();
                String textMobileNo = editTextMechanicMobileNo.getText().toString();
                String textEmailAddress = editTxtMechanicEmailAddress.getText().toString();
                String textPassword = editTxtMechanicPassword.getText().toString();
                String textConfirmPassword = editTxtMechanicConfirmPassword.getText().toString();

                //validate Mobile Number using Matcher and Pattern (Regular Expression)
                String mobileRegex = "[6][0-9]{9}"; //First no. can be only be 6 and rest can be any no.
                Matcher mobileMatcher;
                Pattern mobilePattern = Pattern.compile(mobileRegex);
                mobileMatcher = mobilePattern.matcher(textMobileNo);


                if (TextUtils.isEmpty(textFirstName)) {
                    Toast.makeText(RegisterMechanic.this, "Please enter your first name", Toast.LENGTH_LONG).show();
                    editTxtMechanicFirstName.setError("First Name is required");
                    editTxtMechanicFirstName.requestFocus();
                } else if (TextUtils.isEmpty(textLastName)) {
                    Toast.makeText(RegisterMechanic.this, "Please enter your last name", Toast.LENGTH_LONG).show();
                    editTxtMechanicLastName.setError("Last Name is required");
                    editTxtMechanicLastName.requestFocus();
                } else if (TextUtils.isEmpty(textMobileNo)) {
                    Toast.makeText(RegisterMechanic.this, "Please enter your mobile no", Toast.LENGTH_LONG).show();
                    editTextMechanicMobileNo.setError("Mobile Number is required");
                    editTextMechanicMobileNo.requestFocus();
                } else if (!mobileMatcher.find()){
                    Toast.makeText(RegisterMechanic.this, "Please re-enter your mobile no", Toast.LENGTH_LONG).show();
                    editTextMechanicMobileNo.setError("Mobile Number is invalid");
                    editTextMechanicMobileNo.requestFocus();
                } else if (TextUtils.isEmpty(textEmailAddress)) {
                    Toast.makeText(RegisterMechanic.this, "Please enter your email address", Toast.LENGTH_LONG).show();
                    editTxtMechanicEmailAddress.setError("Email Address is required");
                    editTxtMechanicEmailAddress.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmailAddress).matches()) {
                    Toast.makeText(RegisterMechanic.this, "Please re-enter your email", Toast.LENGTH_LONG).show();
                    editTxtMechanicEmailAddress.setError("Valid email is required");
                    editTxtMechanicEmailAddress.requestFocus();
                } else if (TextUtils.isEmpty(textPassword)) {
                    Toast.makeText(RegisterMechanic.this, "Please enter your password", Toast.LENGTH_LONG).show();
                    editTxtMechanicPassword.setError("Password is required");
                    editTxtMechanicPassword.requestFocus();
                } else if (TextUtils.isEmpty(textConfirmPassword)) {
                    Toast.makeText(RegisterMechanic.this, "Please enter your confirmed password", Toast.LENGTH_LONG).show();
                    editTxtMechanicConfirmPassword.setError("Password Confirmation is required");
                    editTxtMechanicConfirmPassword.requestFocus();
                } else if (!textPassword.equals(textConfirmPassword)){
                    Toast.makeText(RegisterMechanic.this, "Please set the same password", Toast.LENGTH_LONG).show();
                    editTxtMechanicConfirmPassword.setError("Password Confirmation is required");
                    editTxtMechanicConfirmPassword.requestFocus();
                    //Clear the entered passwords
                    editTxtMechanicPassword.clearComposingText();
                    editTxtMechanicConfirmPassword.clearComposingText();
                } else if (!checkBoxMechanic.isChecked()){
                    Toast.makeText(RegisterMechanic.this, "Please check this box to agree to our Terms of Service and Privacy Policy", Toast.LENGTH_LONG).show();
                    checkBoxMechanic.setError("Your agreement to the Terms of Service and Privacy Policy is required");
                    checkBoxMechanic.requestFocus();
                } else {

                    registerMechanic(textFirstName, textLastName, textMobileNo, textEmailAddress, textPassword);
                }
            }
        });
    }
    //Register Mechanic with given credentials
    private void registerMechanic(String textFirstName, String textLastName, String textMobileNo, String textEmailAddress, String textPassword) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(textEmailAddress, textPassword).addOnCompleteListener(RegisterMechanic.this, new OnCompleteListener<AuthResult>() {

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
                    ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(textFirstName, textLastName, textFullName, textEmailAddress, textMobileNo);

                    //Extracting user reference from Database for "Registered Users"
                    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Mechanics");

                    referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                //SendVerification Email
                                firebaseUser.sendEmailVerification();

                                Toast.makeText(RegisterMechanic.this, "User registration is successful! :D. Please verify your email", Toast.LENGTH_LONG).show();

                                //Open MainActivity after successful registration
                                Intent intent = new Intent(RegisterMechanic.this, SignInMechanic.class);

                                //To Prevent User from returning back to Register upon pressing back button after registration
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();   //to close Register

                            } else {
                                Toast.makeText(RegisterMechanic.this, "User registration is unsuccessful! :( Please try again", Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                } else {
                    try{
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        editTxtMechanicPassword.setError("Your password is too weak. Kindly use a mix of alphabets, numbers and special characters");
                        editTxtMechanicPassword.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        editTxtMechanicEmailAddress.setError("Your email is invalid or already in use. Kindly re-enter");
                        editTxtMechanicEmailAddress.requestFocus();
                    } catch (FirebaseAuthUserCollisionException e){
                        editTxtMechanicEmailAddress.setError("Mechanic is already registered with this email. Use another email.");
                        editTxtMechanicEmailAddress.requestFocus();
                    } catch (Exception e){
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(RegisterMechanic.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
}