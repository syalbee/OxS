package com.ocdxsunnah.oxs.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.ocdxsunnah.oxs.Database.DatabaseInit;
import com.ocdxsunnah.oxs.R;

public class WaktuActivity extends AppCompatActivity {

    ImageButton btNext, btBack;
    RadioGroup listWaktu;
    RadioButton btPat, btNam, btPan;
    private int jam = 0;
    private String uID;
    DatabaseInit db = new DatabaseInit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waktu);

        btNext = findViewById(R.id.btNextc);
        listWaktu = findViewById(R.id.list_waktu);
        btPat = findViewById(R.id.patJam);
        btNam = findViewById(R.id.namJam);
        btPan = findViewById(R.id.panJam);
        btBack = findViewById(R.id.btBack);


        FirebaseUser firebaseUser = db.firebaseAuth.getCurrentUser();
        uID = firebaseUser.getUid();

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihWaktu();
                if(jam == 0){
                    Toast.makeText(WaktuActivity.this, "pilih salah satu", Toast.LENGTH_SHORT).show();
                } else {
                    db.user.child(uID).child("status").setValue(true);
                    Intent next = new Intent(WaktuActivity.this, MenuActivity.class);
                    startActivity(next);
                    finish();
                }
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(WaktuActivity.this, BiodataActivity.class);
                startActivity(back);
                finish();
            }
        });
    }

    private void pilihWaktu() {
            if (btPat.isChecked()) {
                jam = 4;
                db.user.child(uID).child("lama").setValue(jam);
                Toast.makeText(this, "anda Memilih " + jam + " Jam", Toast.LENGTH_SHORT).show();
            } else if (btNam.isChecked()) {
                jam = 6;
                db.user.child(uID).child("lama").setValue(jam);
                Toast.makeText(this, "anda Memilih " + jam + " Jam", Toast.LENGTH_SHORT).show();
            } else if (btPan.isChecked()) {
                jam = 8;
                db.user.child(uID).child("lama").setValue(jam);
                Toast.makeText(this, "anda Memilih " + jam + " Jam", Toast.LENGTH_SHORT).show();
            }
    }
}