package com.ocdxsunnah.oxs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.ocdxsunnah.oxs.Views.AwalActivity;
import com.ocdxsunnah.oxs.Views.LoginActivity;
import com.ocdxsunnah.oxs.Views.StepActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent awal = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(awal);
                finish();
            }
        }, 3000);
    }

}