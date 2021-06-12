package com.example.kamibisa.ui.view.fragment.createDonation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kamibisa.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BeneficiaryDataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BeneficiaryDataFragment extends Fragment {

    private View rootView;

    public BeneficiaryDataFragment() {
        // Required empty public constructor
    }

    public static BeneficiaryDataFragment newInstance() {
        BeneficiaryDataFragment fragment = new BeneficiaryDataFragment();
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
        this.rootView = inflater.inflate(R.layout.fragment_beneficiary_data, container, false);
        return rootView;
    }
}