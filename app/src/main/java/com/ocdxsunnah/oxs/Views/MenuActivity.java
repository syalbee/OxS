package com.ocdxsunnah.oxs.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.ocdxsunnah.oxs.Database.DatabaseInit;
import com.ocdxsunnah.oxs.R;

public class MenuActivity extends AppCompatActivity {
    private String Menu;

    Button btLogout;
    DatabaseInit db = new DatabaseInit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btLogout = findViewById(R.id.btLogout);

        db.googleSignInClient = GoogleSignIn.getClient(MenuActivity.this, GoogleSignInOptions.DEFAULT_SIGN_IN);

        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            db.firebaseAuth.signOut();
                            Toast.makeText(getApplicationContext()
                                    , "Logout Sukses", Toast.LENGTH_SHORT).show();

                            finish();
                        }
                    }
                });
            }
        });

    }
}