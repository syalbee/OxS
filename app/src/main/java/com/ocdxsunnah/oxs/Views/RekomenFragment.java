package com.ocdxsunnah.oxs.Views;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.ocdxsunnah.oxs.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RekomenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RekomenFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ImageButton btWorkout, btFood;

    public RekomenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RekomenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RekomenFragment newInstance(String param1, String param2) {
        RekomenFragment fragment = new RekomenFragment();
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
        // Inflate the layout for this fragmen
        final View root = inflater.inflate(R.layout.fragment_rekomen, container, false);

        btFood = root.findViewById(R.id.btFood);
        btWorkout = root.findViewById(R.id.btWorkout);

        btFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FoodActivity.class));
            }
        });

        btWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), WorkoutActivity.class));
            }
        });

        return root;
    }
}