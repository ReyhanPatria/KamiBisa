package com.example.kamibisa.ui.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.kamibisa.R;

public class CreateDonationFragment extends Fragment {
    public static String TAG = "CreateDonationFragment";

    public CreateDonationFragment() {
        // Required empty public constructor
    }

    public static CreateDonationFragment newInstance(String param1, String param2) {
        CreateDonationFragment fragment = new CreateDonationFragment();
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
        return inflater.inflate(R.layout.fragment_create_donation, container, false);
    }
}