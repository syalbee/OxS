package com.ocdxsunnah.oxs.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.ocdxsunnah.oxs.Database.DatabaseInit;
import com.ocdxsunnah.oxs.R;

public class WaktuActivity extends AppCompatActivity {

    Button btNext;
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

        FirebaseUser firebaseUser = db.firebaseAuth.getCurrentUser();
        uID = firebaseUser.getUid();

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihWaktu();
                if(jam == 0){
                    Toast.makeText(WaktuActivity.this, "pilih salah satu", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(WaktuActivity.this, "lanjut", Toast.LENGTH_SHORT).show();
                }
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