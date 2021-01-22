package com.ocdxsunnah.oxs.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ocdxsunnah.oxs.R;

public class WorkoutActivity extends AppCompatActivity {

    ImageButton btBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        btBack = findViewById(R.id.btBack);

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent waktu = new Intent(WorkoutActivity.this, RekomenFragment.class);
                startActivity(waktu);
            }
        });


    }
}