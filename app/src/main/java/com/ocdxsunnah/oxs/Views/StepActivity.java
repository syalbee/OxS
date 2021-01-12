package com.ocdxsunnah.oxs.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.ocdxsunnah.oxs.MainActivity;
import com.ocdxsunnah.oxs.R;

public class StepActivity extends AppCompatActivity {

    ImageButton btNext1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        btNext1 = findViewById(R.id.btNext1);

        btNext1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(StepActivity.this, LoginActivity.class);
                startActivity(next);
                finish();
            }
        });

    }
}