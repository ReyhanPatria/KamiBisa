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
import android.widget.TextView;

import com.example.kamibisa.R;
import com.example.kamibisa.data.model.Donation;
import com.example.kamibisa.ui.view.activity.HomeActivity;
import com.example.kamibisa.ui.view.recyclerview.DonationRecyclerViewAdapter;
import com.example.kamibisa.ui.viewmodel.HistoryViewModel;
import com.example.kamibisa.ui.viewmodel.factory.HistoryViewModelFactory;
import com.example.kamibisa.utils.InjectionUtilities;

import java.util.List;

public class HistoryFragment extends Fragment {
    public static String TAG = "HistoryFragment";

    private View rootView;

    private HistoryViewModel historyViewModel;

    private RecyclerView ownedDonationListRecyclerView;
    private DonationRecyclerViewAdapter ownedDonationListAdapter;

    private RecyclerView givenDonationListRecyclerView;
    private DonationRecyclerViewAdapter givenDonationListAdapter;

    private TextView emptyOwnedDonationTextView;
    private TextView emptyGivenDonationTextView;

    public HistoryFragment() {
        // Required empty public constructor
    }

    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
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
        this.rootView = inflater.inflate(R.layout.fragment_history, container, false);
        return this.rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        historyViewModel.updateDonationList();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeViewModel();
        observeVariables();
        initializeUi();
    }

    private void initializeViewModel() {
        HistoryViewModelFactory factory = InjectionUtilities.getInstance().provideHistoryViewModelFactory();
        this.historyViewModel = new ViewModelProvider(this, factory).get(HistoryViewModel.class);
    }

    private void observeVariables() {
        historyViewModel.getIsUpdating()
                .observe(getViewLifecycleOwner(), new Observer<Boolean>() {
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

        historyViewModel.getGivenDonationIdList()
                .observe(getViewLifecycleOwner(), new Observer<List<String>>() {
                    @Override
                    public void onChanged(List<String> strings) {
                        historyViewModel.updateGivenDonationList();
                    }
                });

        historyViewModel.getOwnedDonationList()
                .observe(getViewLifecycleOwner(), new Observer<List<Donation>>() {
                    @Override
                    public void onChanged(List<Donation> donations) {
                        if(donations.isEmpty()) {
                            emptyOwnedDonationTextView.setVisibility(View.VISIBLE);
                        }
                        else {
                            emptyOwnedDonationTextView.setVisibility(View.GONE);
                        }

                        ownedDonationListAdapter.setDonationList(donations);
                        ownedDonationListAdapter.notifyDataSetChanged();
                    }
                });

        historyViewModel.getGivenDonationList()
                .observe(getViewLifecycleOwner(), new Observer<List<Donation>>() {
                    @Override
                    public void onChanged(List<Donation> donations) {
                        if(donations.isEmpty()) {
                            emptyGivenDonationTextView.setVisibility(View.VISIBLE);
                        }
                        else {
                            emptyGivenDonationTextView.setVisibility(View.GONE);
                        }

                        givenDonationListAdapter.setDonationList(donations);
                        givenDonationListAdapter.notifyDataSetChanged();
                    }
                });
    }

    public void initializeUi() {
        this.emptyOwnedDonationTextView = rootView.findViewById(R.id.tv_history_emptyOwnedDonation);
        this.emptyOwnedDonationTextView.setVisibility(View.GONE);

        this.emptyGivenDonationTextView = rootView.findViewById(R.id.tv_history_emptyGivenDonation);
        this.emptyGivenDonationTextView.setVisibility(View.GONE);

        this.initializeDonationRecyclerView();
    }

    public void initializeDonationRecyclerView() {
        // Owned charity list
        ownedDonationListRecyclerView = rootView.findViewById(R.id.rv_history_ownedDonationList);

        LinearLayoutManager ownedLayoutManager = new LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false);
       ownedDonationListRecyclerView.setLayoutManager(ownedLayoutManager);

        ownedDonationListAdapter = new DonationRecyclerViewAdapter(requireContext(),
                historyViewModel.getOwnedDonationList().getValue());
        ownedDonationListRecyclerView.setAdapter(ownedDonationListAdapter);


        // Given charity list
        givenDonationListRecyclerView = rootView.findViewById(R.id.rv_history_givenDonationList);

        LinearLayoutManager givenLayoutManager = new LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false);
        givenDonationListRecyclerView.setLayoutManager(givenLayoutManager);

        givenDonationListAdapter = new DonationRecyclerViewAdapter(requireContext(),
                historyViewModel.getGivenDonationList().getValue());
        givenDonationListRecyclerView.setAdapter(givenDonationListAdapter);
    }
}