package com.ocdxsunnah.oxs.Views;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.os.Bundle;


import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import com.ocdxsunnah.oxs.Database.DatabaseInit;
import com.ocdxsunnah.oxs.R;

public class MenuActivity extends AppCompatActivity {




    MeowBottomNavigation nabar;
    DatabaseInit db = new DatabaseInit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        nabar = findViewById(R.id.navbare) ;

        nabar.add(new MeowBottomNavigation.Model(1, R.drawable.ic_update64));
        nabar.add(new MeowBottomNavigation.Model(2, R.drawable.ic_baseline_home_24));
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
                    case 2:
                        selectedFragment = new HomeFragment();
                        break;
                    case 3:
                        selectedFragment = new RekomenFragment();
                        break;
                    case 1:
                        selectedFragment = new CekFragment();
                        break;

                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentLayout
                        ,selectedFragment).commit();
            }
        });


    }
}