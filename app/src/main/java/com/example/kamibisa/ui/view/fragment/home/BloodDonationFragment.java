package com.example.kamibisa.ui.view.fragment.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kamibisa.R;
import com.example.kamibisa.data.model.BloodDonation;
import com.example.kamibisa.ui.view.activity.HomeActivity;
import com.example.kamibisa.ui.view.recyclerview.BloodDonationRecyclerViewAdapter;
import com.example.kamibisa.ui.view.recyclerview.DonationRecyclerViewAdapter;
import com.example.kamibisa.ui.viewmodel.BloodDonationViewModel;
import com.example.kamibisa.ui.viewmodel.HomeViewModel;
import com.example.kamibisa.ui.viewmodel.factory.BloodDonationViewModelFactory;
import com.example.kamibisa.ui.viewmodel.factory.HomeViewModelFactory;
import com.example.kamibisa.utils.InjectionUtilities;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class BloodDonationFragment extends Fragment {
    private static final String TAG = "BloodDonationFragment";

    private View rootView;

    private BloodDonationViewModel bloodDonationViewModel;

    private RecyclerView bloodDonationListRecyclerView;
    private BloodDonationRecyclerViewAdapter bloodDonationListAdapter;

    private MaterialButton createBloodDonationButton;

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
        this.rootView = inflater.inflate(R.layout.fragment_blood_donation, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViewModel();
        observeVariables();
        initializeUi();
    }

    private void initializeUi() {
        this.createBloodDonationButton = rootView.findViewById(R.id.btn_bloodDonation_createBloodDonation);
        initializeBloodDonationRecyclerView();
    }

    private void initializeViewModel() {
        BloodDonationViewModelFactory factory = InjectionUtilities.getInstance().provideBloodDonationViewModelFactory();
        bloodDonationViewModel = new ViewModelProvider(this, factory).get(BloodDonationViewModel.class);
    }

    private void observeVariables() {
        bloodDonationViewModel.getIsUpdating().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean) {
                    ((HomeActivity) requireActivity()).showProgressBar();
                }
                else {
                    ((HomeActivity) requireActivity()).hideProgressBar();
                }
            }
        });

        bloodDonationViewModel.getBloodDonationList()
                .observe(getViewLifecycleOwner(), new Observer<List<BloodDonation>>() {
                    @Override
                    public void onChanged(List<BloodDonation> bloodDonations) {
                        bloodDonationListAdapter.setBloodDonationList(bloodDonations);
                        bloodDonationListAdapter.notifyDataSetChanged();
                    }
                });
    }

    public void initializeBloodDonationRecyclerView() {
        // Urgent charity list
        bloodDonationListRecyclerView = rootView.findViewById(R.id.rv_bloodDonation_bloodDonationList);

        LinearLayoutManager urgentLayoutManager = new LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false);
        bloodDonationListRecyclerView.setLayoutManager(urgentLayoutManager);

        bloodDonationListAdapter = new BloodDonationRecyclerViewAdapter(requireContext(),
                bloodDonationViewModel.getBloodDonationList().getValue());
        bloodDonationListRecyclerView.setAdapter(bloodDonationListAdapter);
    }
}