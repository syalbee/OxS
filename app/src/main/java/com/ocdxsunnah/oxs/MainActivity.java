package com.ocdxsunnah.oxs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ocdxsunnah.oxs.Views.StepActivity;

public class MainActivity extends AppCompatActivity {

    Button btMulai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btMulai = findViewById(R.id.btYumulai);

        btMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(MainActivity.this, StepActivity.class);
                startActivity(next);
                finish();
            }
        });
    }

}