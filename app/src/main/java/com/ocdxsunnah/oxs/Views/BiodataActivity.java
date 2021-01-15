package com.ocdxsunnah.oxs.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.ocdxsunnah.oxs.Database.DatabaseInit;
import com.ocdxsunnah.oxs.R;

import java.util.ArrayList;
import java.util.List;

public class BiodataActivity extends AppCompatActivity {

    Button btCek;
    ImageButton btNextb, btBack;
    TextView tvHasil;
    Spinner spJk;
    EditText etBerat, etTinggi;

    private String uID;


    DatabaseInit db = new DatabaseInit();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biodata);

        btCek = findViewById(R.id.btCek);
        btNextb = findViewById(R.id.btNextb);
        spJk = findViewById(R.id.spJk);
        etBerat = findViewById(R.id.etBeratbadan);
        etTinggi = findViewById(R.id.etTinggibadan);
        tvHasil = findViewById(R.id.tvHasil);
        btBack = findViewById(R.id.btBack);

        FirebaseUser firebaseUser = db.firebaseAuth.getCurrentUser();
        uID = firebaseUser.getUid();

        List<String> jeKel = new ArrayList<String>();
        jeKel.add("Pria");
        jeKel.add("Wanita");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, jeKel);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spJk.setAdapter(dataAdapter);

        btCek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etBerat.getText().toString().isEmpty() || etTinggi.getText().toString().isEmpty()) {
                    Toast.makeText(BiodataActivity.this, "Isikan data dengan benar", Toast.LENGTH_SHORT).show();
                }
                else {
                    Double hasil;
                    String jk = spJk.getSelectedItem().toString();
                    Double Berat = Double.parseDouble(etBerat.getText().toString());
                    Double Tinggi = Double.parseDouble(etTinggi.getText().toString());
                    if (jk == "Wanita") {
                        hasil = (Tinggi - 100) - ((Tinggi - 100) * 15 / 100);
                        String res = String.valueOf(hasil);
                        tvHasil.setText(res);
                        setData(Berat, Tinggi, res, jk);
                        Toast.makeText(BiodataActivity.this, jk, Toast.LENGTH_SHORT).show();
                    } else if (jk == "Pria") {
                        hasil = (Tinggi - 100) - ((Tinggi - 100) * 10 / 100);
                        String res = String.valueOf(hasil);
                        tvHasil.setText(res);
                        setData(Berat, Tinggi, res, jk);
                        Toast.makeText(BiodataActivity.this, jk, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btNextb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Double.parseDouble(tvHasil.getText().toString()) <= 0 ){
                    Toast.makeText(BiodataActivity.this, "Isi data dahulu", Toast.LENGTH_SHORT).show();
                } else {
                    Intent waktu = new Intent(BiodataActivity.this, WaktuActivity.class);
                    startActivity(waktu);
                    finish();
                }
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(BiodataActivity.this, MetodeActivity.class);
                startActivity(back);
                finish();
            }
        });
    }

    private void setData(Double berat, Double tinggi, String res, String jk) {
        db.user.child(uID).child("beratBadan").setValue(berat);
        db.user.child(uID).child("tinggiBadan").setValue(tinggi);
        db.user.child(uID).child("beratIdeal").setValue(res);
        db.user.child(uID).child("jeKel").setValue(jk);
    }
}