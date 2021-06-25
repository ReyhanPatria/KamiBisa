package com.example.kamibisa.ui.view.fragment.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kamibisa.R;
import com.example.kamibisa.data.model.Donation;
import com.example.kamibisa.ui.view.activity.HomeActivity;
import com.example.kamibisa.ui.view.recyclerview.DonationRecyclerViewAdapter;
import com.example.kamibisa.ui.viewmodel.HomeViewModel;
import com.example.kamibisa.ui.viewmodel.factory.HomeViewModelFactory;
import com.example.kamibisa.utils.InjectionUtilities;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    public static String TAG = "HomeFragment";

    private View rootView;
    private HomeViewModel homeViewModel;

    private RecyclerView urgentDonationListRecyclerView;
    private DonationRecyclerViewAdapter urgentDonationListAdapter;

    private RecyclerView selectedDonationListRecyclerView;
    private DonationRecyclerViewAdapter selectedDonationListAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
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
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
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
        initializeDonationRecyclerView();
    }

    private void initializeViewModel() {
        HomeViewModelFactory factory = InjectionUtilities.getInstance().provideHomeViewModelFactory();
        homeViewModel = new ViewModelProvider(this, factory).get(HomeViewModel.class);
    }

    private void observeVariables() {
        homeViewModel.getIsUpdating().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
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

        homeViewModel.getUrgentDonationList()
                .observe(getViewLifecycleOwner(), new Observer<List<Donation>>() {
                    @Override
                    public void onChanged(List<Donation> charities) {
                        urgentDonationListAdapter.setDonationList(charities);
                        urgentDonationListAdapter.notifyDataSetChanged();

                        Log.d(TAG, String.format("Urgent charity list changed. %d items in list",
                                urgentDonationListRecyclerView.getAdapter().getItemCount()));
                    }
                });

        homeViewModel.getSelectedDonationList()
                .observe(getViewLifecycleOwner(), new Observer<List<Donation>>() {
                    @Override
                    public void onChanged(List<Donation> charities) {
                        selectedDonationListAdapter.setDonationList(charities);
                        selectedDonationListAdapter.notifyDataSetChanged();

                        Log.d(TAG, String.format("Selected charity list changed. %d items in list",
                                selectedDonationListRecyclerView.getAdapter().getItemCount()));
                    }
                });
    }

    public void initializeDonationRecyclerView() {
        // Urgent charity list
        urgentDonationListRecyclerView = rootView.findViewById(R.id.rv_home_urgentCharityList);

        LinearLayoutManager urgentLayoutManager = new LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false);
        urgentDonationListRecyclerView.setLayoutManager(urgentLayoutManager);

        urgentDonationListAdapter = new DonationRecyclerViewAdapter(requireContext(),
                homeViewModel.getUrgentDonationList().getValue());
        urgentDonationListRecyclerView.setAdapter(urgentDonationListAdapter);


        // Selected charity list
        selectedDonationListRecyclerView = rootView.findViewById(R.id.rv_home_selectedCharityList);

        LinearLayoutManager selectedLayoutManager = new LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false);
        selectedDonationListRecyclerView.setLayoutManager(selectedLayoutManager);

        selectedDonationListAdapter = new DonationRecyclerViewAdapter(requireContext(),
                homeViewModel.getSelectedDonationList().getValue());
        selectedDonationListRecyclerView.setAdapter(selectedDonationListAdapter);
    }
}