package com.example.fwm21;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class volunter extends AppCompatActivity {
    EditText name, age, gender, phone, address, email, message;
    Button submit;
    boolean isNameValid, isAgeValid, isGenderValid, isPhoneValid, isAddressValid, isEmailValid, isMessageValid;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    public static final String TAG = "TAG";
    TextInputLayout nameError, ageError, genderError, phoneError, addressError, emailError, messageError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiity_volunter);

        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        gender = findViewById(R.id.gender);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        email = findViewById(R.id.email);
        message = findViewById(R.id.message);
        submit = findViewById(R.id.submit);

        nameError = findViewById(R.id.nameError);
        ageError = findViewById(R.id.ageError);
        genderError = findViewById(R.id.genderError);
        phoneError = findViewById(R.id.phoneError);
        addressError = findViewById(R.id.addressError);
        emailError = findViewById(R.id.emailError);
        messageError = findViewById(R.id.messageError);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetValidation();
            }
        });
    }

    public void SetValidation() {
        // Check for a valid name.
        if (name.getText().toString().isEmpty()) {
            nameError.setError(getResources().getString(R.string.name_error));
            isNameValid = false;
        } else {
            isNameValid = true;
            nameError.setErrorEnabled(false);
        }

        // Check for a valid email address.
        if (email.getText().toString().isEmpty()) {
            emailError.setError(getResources().getString(R.string.email_error));
            isEmailValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            emailError.setError(getResources().getString(R.string.error_invalid_email));
            isEmailValid = false;
        } else {
            isEmailValid = true;
            emailError.setErrorEnabled(false);
        }

        // Check for a valid phone number.
        if (phone.getText().toString().isEmpty()) {
            phoneError.setError(getResources().getString(R.string.phone_error));
            isPhoneValid = false;
        } else {
            isPhoneValid = true;
            phoneError.setErrorEnabled(false);
        }

        // Check for valid age.
        if (age.getText().toString().isEmpty()) {
            ageError.setError(getResources().getString(R.string.age_error));
            isAgeValid = false;
        } else {
            isAgeValid = true;
            ageError.setErrorEnabled(false);
        }

        // Check for valid gender.
        if (gender.getText().toString().isEmpty()) {
            genderError.setError(getResources().getString(R.string.gender_error));
            isGenderValid = false;
        } else {
            isGenderValid = true;
            genderError.setErrorEnabled(false);
        }

        // Check for valid address.
        if (address.getText().toString().isEmpty()) {
            addressError.setError(getResources().getString(R.string.address_error));
            isAddressValid = false;
        } else {
            isAddressValid = true;
            addressError.setErrorEnabled(false);
        }

        // Check for a valid message.
        if (message.getText().toString().isEmpty()) {
            messageError.setError(getResources().getString(R.string.message_error));
            isMessageValid = false;
        } else {
            isMessageValid = true;
            messageError.setErrorEnabled(false);
        }

        if (isNameValid && isEmailValid && isMessageValid && isAddressValid && isGenderValid && isAgeValid && isPhoneValid) {
            String Name = name.getText().toString().trim();
            String Email = email.getText().toString().trim();
            String Message = message.getText().toString().trim();
            String Address = address.getText().toString().trim();
            String Phone = phone.getText().toString().trim();
            String Age = age.getText().toString().trim();
            String Gender = gender.getText().toString().trim();

            userID = fAuth.getCurrentUser().getUid();
            CollectionReference collectionReference = fStore.collection("user data");

            Map<String, Object> user = new HashMap<>();
            user.put("timestamp", FieldValue.serverTimestamp());
            user.put("name", Name);
            user.put("email", Email);
            user.put("message", Message);
            user.put("userid", userID);
            user.put("Phone", Phone);
            user.put("Address", Address);
            user.put("Age", Age);
            user.put("Gender", Gender);

            collectionReference.add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getApplicationContext(), "Success! now you are volunter", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Successfully! We will shortly revert you back.");
                            Intent intent = new Intent(volunter.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                            Log.w(TAG, "Error!", e);
                        }
                    });
        }
    }
}

