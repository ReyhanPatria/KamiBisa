package com.example.kamibisa.ui.view.fragment.createDonation;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.kamibisa.R;
import com.example.kamibisa.ui.view.activity.CreateDonationActivity;
import com.google.android.material.button.MaterialButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BeneficiaryDataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BeneficiaryDataFragment extends Fragment implements View.OnClickListener {
    private static String TAG = "BeneficiaryDataFragment";

    private View rootView;

    private EditText beneficiaryNameEditText;

    private Spinner categorySpinner;
    private Spinner beneficiaryRelationSpinner;

    private MaterialButton nextButton;
    private MaterialButton backButton;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeUi();
        setOnClickListeners();
    }

    public void initializeUi() {
        this.nextButton = rootView.findViewById(R.id.btn_beneficiaryData_next);
        this.backButton = rootView.findViewById(R.id.btn_beneficiaryData_back);

        this.beneficiaryNameEditText = rootView.findViewById(R.id.edt_beneficiaryData_beneficiaryName);

        this.categorySpinner = rootView.findViewById(R.id.spin_beneficiaryData_category);
        this.beneficiaryRelationSpinner = rootView.findViewById(R.id.spin_beneficiaryData_beneficiaryRelation);

        // Set category spinner data
        ArrayAdapter<CharSequence> categorySpinnerAdapter = ArrayAdapter
                .createFromResource(this.requireContext(),
                        R.array.categories, R.layout.custom_spinner_item);
        categorySpinnerAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        categorySpinner.setAdapter(categorySpinnerAdapter);

        // Set beneficiary relation spinner data
        ArrayAdapter<CharSequence> relationSpinnerAdapter = ArrayAdapter
                .createFromResource(this.requireContext(),
                        R.array.relations, R.layout.custom_spinner_item);
        relationSpinnerAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        beneficiaryRelationSpinner.setAdapter(relationSpinnerAdapter);
    }

    @Override
    @SuppressLint("NonConstantResourceId")
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_beneficiaryData_next:
                gotoDonationDataFragment();
                break;

            case R.id.btn_beneficiaryData_back:
                this.requireActivity().getSupportFragmentManager()
                        .popBackStackImmediate();
                break;

            default:
                break;
        }
    }

    public void setOnClickListeners() {
        this.nextButton.setOnClickListener(this);
        this.backButton.setOnClickListener(this);
    }

    private void gotoDonationDataFragment() {
        Fragment f = ((CreateDonationActivity) this.requireActivity()).getDonationDataFragment();
        ((CreateDonationActivity) this.requireActivity()).changeMenu(f, Boolean.TRUE);
    }
}