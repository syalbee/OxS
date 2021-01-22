package com.ocdxsunnah.oxs.Views;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ocdxsunnah.oxs.Database.DatabaseInit;
import com.ocdxsunnah.oxs.R;

import static androidx.core.content.ContextCompat.getSystemService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CekFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CekFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String uID;

    //Widget
    TextView tvPersen;
    Button btUpdate;
    EditText etUpdate;
    ProgressBar pbUpdate;

    public CekFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CekFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CekFragment newInstance(String param1, String param2) {
        CekFragment fragment = new CekFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_cek, container, false);

        tvPersen = root.findViewById(R.id.tvPersentase);
        btUpdate = root.findViewById(R.id.btUpdate);
        pbUpdate = root.findViewById(R.id.pbUpdate);
        etUpdate = (EditText) root.findViewById(R.id.etUpdate);

        DatabaseInit db = new DatabaseInit();


        InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        etUpdate.requestFocus();




        db.user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                FirebaseUser firebaseUser = db.firebaseAuth.getCurrentUser();
                if (firebaseUser != null ) {
                    uID = firebaseUser.getUid();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String berat = etUpdate.getText().toString();
                db.user.child(uID).child("variabelBerat").setValue(berat);

                etUpdate.requestFocus();
                InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imgr.showSoftInput(etUpdate, InputMethodManager.SHOW_IMPLICIT);
            }
        });


        return root;
    }

}
//
//    String beratSekarang = snapshot.child(firebaseUser.getUid()).child("beratBadan").getValue().toString();
//    String beratIdeal = snapshot.child(firebaseUser.getUid()).child("beratIdeal").getValue().toString();
//    //
////
//    float bSekrang = Float.parseFloat(beratSekarang);
//    float bIdeal = Float.parseFloat(beratIdeal);
//
//    float berat = bSekrang-bIdeal;
//    float conversi = berat - (bSekrang-bIdeal);
//    float updateBerat = 100 * conversi / berat;
//
//                   tvPersen.setText(String.valueOf(updateBerat) + "%");
//
////                   pbUpdate.setProgress(updateBerat);