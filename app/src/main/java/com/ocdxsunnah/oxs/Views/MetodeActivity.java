package com.ocdxsunnah.oxs.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.ocdxsunnah.oxs.Database.DatabaseInit;
import com.ocdxsunnah.oxs.Models.UserModels;
import com.ocdxsunnah.oxs.R;

public class MetodeActivity extends AppCompatActivity implements View.OnClickListener {

    Button btNext2, btKolab, btOcd;
    DatabaseInit db = new DatabaseInit();

    private String uID;
    private String metode;
    private String nama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metode);

        btNext2 = findViewById(R.id.btNext2);
        btOcd = findViewById(R.id.btOcd);
        btKolab = findViewById(R.id.btKolab);

        FirebaseUser firebaseUser = db.firebaseAuth.getCurrentUser();

        if(firebaseUser != null){
            uID = firebaseUser.getUid();
            nama = firebaseUser.getDisplayName();
        }

        btNext2.setOnClickListener(this);
        btKolab.setOnClickListener(this);
        btOcd.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        String bIdeal = "0";
        Double bBadan = 0.0;
        Double tBadan = 0.0;
        int lama = 0;

        if(i == R.id.btNext2){
            if(metode == null){
                Toast.makeText(this, "Pilih salah satu", Toast.LENGTH_SHORT).show();
            } else {
                UserModels um = new UserModels();

                db.user.child(uID).setValue(new UserModels(bIdeal, metode, nama, uID,bBadan,tBadan,lama))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Intent biodata = new Intent(MetodeActivity.this, BiodataActivity.class);
                                startActivity(biodata);
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MetodeActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else if(i == R.id.btKolab){
            metode = "kolab";
            Toast.makeText(this, "Anda memilih OCD x Sunnah", Toast.LENGTH_SHORT).show();
        } else if(i == R.id.btOcd){
            metode = "ocd";
            Toast.makeText(this, "Anda memilih OCD", Toast.LENGTH_SHORT).show();
        }
    }


}