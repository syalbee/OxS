package com.ocdxsunnah.oxs.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ocdxsunnah.oxs.R;

public class UpdateActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private TextView inputUpdate, txtPesentase;
    private Button btnUpdate;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    public static final String PROGRESS = "progress";

    private String text;
    private int progress;

    int beratSekarang, beratIdeal, berat, updateBerat, conversi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        //dari firebase
        beratSekarang = 80;
        beratIdeal = 60;

        inputUpdate = (TextView) findViewById(R.id.txtUpdate);
        txtPesentase = (TextView) findViewById(R.id.textPersentase);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);

        mProgressBar.setProgress(0);
        berat = beratSekarang-beratIdeal;

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                beratSekarang = Integer.parseInt(inputUpdate.getText().toString());
                conversi = berat - (beratSekarang-beratIdeal);
                updateBerat = 100*conversi/berat;
                txtPesentase.setText(String.valueOf(updateBerat)+"%");
                mProgressBar.setProgress(updateBerat);

                saveData();
            }
        });

        loadData();
        updateViews();

    }
    public void setToast(int text){
        Toast.makeText(this, ""+text, Toast.LENGTH_SHORT).show();
    }
    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(TEXT, String.valueOf(updateBerat));
        editor.putInt(PROGRESS, updateBerat);

        editor.apply();
    }
    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        text = sharedPreferences.getString(TEXT, "0");
        progress = sharedPreferences.getInt(PROGRESS, 0);
    }

    public void updateViews(){
        txtPesentase.setText(text+"%");
        mProgressBar.setProgress(progress);
    }
}