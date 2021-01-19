package com.ocdxsunnah.oxs.Views;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ocdxsunnah.oxs.Database.DatabaseInit;
import com.ocdxsunnah.oxs.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateFragment extends Fragment {

    private ProgressBar mProgressBar;
    private TextView inputUpdate, txtPesentase;
    private Button btnUpdate;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    public static final String PROGRESS = "progress";
    DatabaseInit db = new DatabaseInit();
    private String text;
    private int progress;
    Context mBase;

    int beratSekarang, beratIdeal, berat, updateBerat, conversi;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpdateFragment() {
        // Required empty public constructor
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateFragment newInstance(String param1, String param2) {
        UpdateFragment fragment = new UpdateFragment();
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
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_update, container, false);

        //dari firebases
        db.user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                FirebaseUser firebaseUser = db.firebaseAuth.getCurrentUser();
                if (firebaseUser != null){
                    String beratAwal = snapshot.child(firebaseUser.getUid()).child("beratBadan").getValue().toString();
                    String beratIdeals = snapshot.child(firebaseUser.getUid()).child("beratIdeal").getValue().toString();
                    beratSekarang = Integer.parseInt(beratAwal);
                    beratIdeal = Integer.parseInt(beratIdeals);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        inputUpdate = (TextView) root.findViewById(R.id.txtUpdate);
        txtPesentase = (TextView) root.findViewById(R.id.textPersentase);
        btnUpdate = (Button) root.findViewById(R.id.btnUpdate);
        mProgressBar = (ProgressBar) root.findViewById(R.id.progressbar);

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

            }
        });

        return root;

    }

}