package com.ocdxsunnah.oxs.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.ocdxsunnah.oxs.Database.DatabaseInit;
import com.ocdxsunnah.oxs.R;

public class MenuActivity extends AppCompatActivity {


    private final static int homeNav = 1;
    private final static int updateNav = 2;
    private final static int rekomendasiNav = 3;

    Button btLogout;
    MeowBottomNavigation nabar;
    DatabaseInit db = new DatabaseInit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btLogout = findViewById(R.id.btLogout);
        nabar = findViewById(R.id.navbare);

        nabar.add(new MeowBottomNavigation.Model(1, R.drawable.ic_update64));
        nabar.add(new MeowBottomNavigation.Model(2, R.drawable.ic_baseline_person_24));
        nabar.add(new MeowBottomNavigation.Model(3, R.drawable.ic_baseline_format_list_bulleted_6));

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentLayout, new HomeFragment()).commit();


        nabar.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

            }
        });

        nabar.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {

            }
        });

        nabar.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment selectedFragment = null;
                switch (item.getId()){
                    case homeNav:
                        selectedFragment = new HomeFragment();
                        break;
                    case rekomendasiNav:
                        selectedFragment = new RekomenFragment();
                        break;
                    case updateNav:
                        selectedFragment = new UpdateFragment();
                        break;

                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentLayout
                        ,selectedFragment).commit();
            }
        });


        db.googleSignInClient = GoogleSignIn.getClient(MenuActivity.this, GoogleSignInOptions.DEFAULT_SIGN_IN);

        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
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