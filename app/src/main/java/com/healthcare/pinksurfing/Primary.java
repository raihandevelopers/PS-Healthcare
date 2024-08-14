package com.healthcare.pinksurfing;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.healthcare.pinksurfing.LoginScreenPage;

public class Primary extends AppCompatActivity {

    private AdView mAdView;
    private String user;
    private int p;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary);

        mAuth = FirebaseAuth.getInstance();
        ImageButton HearRated = this.findViewById(R.id.HRone);
        ImageButton BloodPressure = this.findViewById(R.id.BP);
        ImageButton Ox2 = this.findViewById(R.id.O2);
        ImageButton RRate = this.findViewById(R.id.RR);
        ImageButton VitalSigns = this.findViewById(R.id.VS);
        ImageButton Abt = this.findViewById(R.id.About);
        ImageButton bmi = this.findViewById(R.id.bmi);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = extras.getString("Usr");
            //The key argument here must match that used in the other activity
        }

        Abt.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), AboutApp.class);
            startActivity(i);
            finish();
        });

        AdView adView = new AdView(this);

        adView.setAdSize(AdSize.BANNER);

        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        //Every Test Button sends the username + the test number, to go to the wanted test after the instructions activity


        HearRated.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), MainActivity.class);
            startActivity(i);
            finish();
        });

        bmi.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), MainActivity2.class);
            startActivity(i);
            finish();
        });

        BloodPressure.setOnClickListener(v -> {
            p = 2;
            Intent i = new Intent(v.getContext(), StartVitalSigns.class);
            i.putExtra("Usr", user);
            i.putExtra("Page", p);
            startActivity(i);
            finish();
        });

        RRate.setOnClickListener(v -> {
            p = 3;
            Intent i = new Intent(v.getContext(), StartVitalSigns.class);
            i.putExtra("Usr", user);
            i.putExtra("Page", p);
            startActivity(i);
            finish();
        });

        Ox2.setOnClickListener(v -> {
            p = 4;
            Intent i = new Intent(v.getContext(), StartVitalSigns.class);
            i.putExtra("Usr", user);
            i.putExtra("Page", p);
            startActivity(i);
            finish();

        });

        VitalSigns.setOnClickListener(v -> {
            p = 5;
            Intent i = new Intent(v.getContext(), StartVitalSigns.class);
            i.putExtra("Usr", user);
            i.putExtra("Page", p);
            startActivity(i);
            finish();
        });

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, (arg0, arg1) -> {

                    Primary.super.onBackPressed();
                    finish();
                    System.exit(0);
                }).create().show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(Primary.this, LoginScreenPage.class));
        }
    }
}

