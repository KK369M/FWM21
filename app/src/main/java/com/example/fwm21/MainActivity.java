package com.example.fwm21;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    CardView donate, receive, logout, foodmap, about, contact, mypin, history,volunter,charity;

    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        volunter = findViewById(R.id.cardvolunter);
        donate = findViewById(R.id.cardDonate);
        receive = findViewById(R.id.cardReceive);
        logout = findViewById(R.id.cardLogout);
        foodmap = findViewById(R.id.cardFoodmap);
        mypin = findViewById(R.id.cardMyPin);
        history = findViewById(R.id.cardHistory);
        about = findViewById(R.id.cardAboutus);
        contact = findViewById(R.id.cardContact);
        charity = findViewById(R.id.cardcharity);
        fAuth= FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser() ==null){
            Intent intent = new Intent(MainActivity.this, landingpage.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }


        logout.setOnClickListener(new View.OnClickListener ()
        {

            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, landingpage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        donate.setOnClickListener(new View.OnClickListener ()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Donate.class);
                startActivity(intent);
            }
        });
        receive.setOnClickListener(new View.OnClickListener ()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Receive.class);
                startActivity(intent);
            }
        });
        foodmap.setOnClickListener(new View.OnClickListener ()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FoodMap.class);
                startActivity(intent);
            }
        });
        about.setOnClickListener(new View.OnClickListener ()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), About.class);
                startActivity(intent);
            }
        });
        mypin.setOnClickListener(new View.OnClickListener ()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyPin.class);
                startActivity(intent);
            }
        });
        history.setOnClickListener(new View.OnClickListener ()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), History.class);
                startActivity(intent);
            }
        });
        contact.setOnClickListener(new View.OnClickListener ()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Contact.class);
                startActivity(intent);
            }
        });
        volunter.setOnClickListener(new View.OnClickListener ()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), volunter.class);
                startActivity(intent);
            }
        });
        charity.setOnClickListener(new View.OnClickListener ()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DonationActivity.class);
                startActivity(intent);
            }
        });


    }
}