package com.example.kamibisa.ui.view.fragment.createBloodDonation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kamibisa.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class BloodDonationSuccessFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "BloodDonationSuccessFragment";

    private static final String ARG_LINK = "link";

    private View rootView;

    private MaterialTextView linkTextView;

    private MaterialButton finishButton;

    private String link;

    public BloodDonationSuccessFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param link is the link that's going to be shown.
     * @return A new instance of fragment BloodDonationSuccessFragment.
     */
    public static BloodDonationSuccessFragment newInstance(String link) {
        BloodDonationSuccessFragment fragment = new BloodDonationSuccessFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LINK, link);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            link = getArguments().getString(ARG_LINK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.rootView = inflater.inflate(R.layout.fragment_blood_donation_success, container, false);
        return this.rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeUi();
        setOnClickListeners();
    }

    private void initializeUi() {
        this.linkTextView = rootView.findViewById(R.id.tv_bloodDonation_success_link);
        this.finishButton = rootView.findViewById(R.id.btn_bloodDonation_success_finish);

        String fullLink = "kamibisa.com/" + this.link;
        linkTextView.setText(fullLink);
    }

    private void setOnClickListeners() {
        this.finishButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == finishButton.getId()) {
            this.requireActivity().finish();
        }
    }
}