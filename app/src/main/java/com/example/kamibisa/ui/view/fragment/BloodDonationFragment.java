package com.example.kamibisa.ui.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kamibisa.R;

public class BloodDonationFragment extends Fragment {
    private static final String TAG = "BloodDonationFragment";

    public BloodDonationFragment() {
        // Required empty public constructor
    }

    public static BloodDonationFragment newInstance() {
        BloodDonationFragment fragment = new BloodDonationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blood_donation, container, false);
    }
}