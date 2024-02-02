package com.example.fwm21;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DonationActivity extends AppCompatActivity {
    private CardView googlepay, paytm, phonepay;
    private EditText amountEditText;
    private EditText nameEditText;
    private EditText emailEditText;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);

        googlepay = findViewById(R.id.googlepay);
        paytm = findViewById(R.id.paytm);
        phonepay = findViewById(R.id.phonepay);

        googlepay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchGooglePay();
            }
        });

        paytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchPaytm();
            }
        });

        phonepay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchPhonePe();
            }
        });

        // Initialize views
        amountEditText = findViewById(R.id.amountEditText);
        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        Button donateButton = findViewById(R.id.donateButton);

        // Get a reference to the Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference();

        donateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the data from the EditText fields
                double amount = Double.parseDouble(amountEditText.getText().toString());
                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();

                // Create a new DonationData object
                DonationData donationData = new DonationData(amount, name, email);

                // Save the data to Firebase
                String donationId = databaseReference.child("donations").push().getKey();
                databaseReference.child("donations").child(donationId).setValue(donationData);

                // Show success message using Toast
                Toast.makeText(DonationActivity.this, "Payment successful!", Toast.LENGTH_SHORT).show();

                // Return to the main page
                Intent intent = new Intent(DonationActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Optional: Finish this activity to prevent going back to it when pressing back from the main page
            }
        });
    }

    // Method to launch Google Pay
    private void launchGooglePay() {
        // Replace "your_googlepay_upi_id" with your actual Google Pay UPI ID
        String googlePayUPI = "your_googlepay_upi_id";
        String amount = amountEditText.getText().toString();
        String name = nameEditText.getText().toString();

        String uri = "upi://pay?pa=" + googlePayUPI + "&pn=" + name + "&am=" + amount + "&cu=INR";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(uri));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "Google Pay not installed.", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to launch Paytm
    private void launchPaytm() {
        // Replace "your_paytm_upi_id" with your actual Paytm UPI ID
        String paytmUPI = "your_paytm_upi_id";
        String amount = amountEditText.getText().toString();
        String name = nameEditText.getText().toString();

        String uri = "upi://pay?pa=" + paytmUPI + "&pn=" + name + "&am=" + amount + "&cu=INR";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(uri));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "Paytm not installed.", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to launch PhonePe
    private void launchPhonePe() {
        // Replace "your_phonepe_upi_id" with your actual PhonePe UPI ID
        String phonePeUPI = "your_phonepe_upi_id";
        String amount = amountEditText.getText().toString();
        String name = nameEditText.getText().toString();

        String uri = "upi://pay?pa=" + phonePeUPI + "&pn=" + name + "&am=" + amount + "&cu=INR";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(uri));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "PhonePe not installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
