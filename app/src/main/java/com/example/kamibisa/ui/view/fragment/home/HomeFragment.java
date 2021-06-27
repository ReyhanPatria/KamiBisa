package com.example.kamibisa.ui.view.fragment.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kamibisa.R;
import com.example.kamibisa.data.model.Donation;
import com.example.kamibisa.ui.view.activity.DonationInformationActivity;
import com.example.kamibisa.ui.view.activity.HomeActivity;
import com.example.kamibisa.ui.view.recyclerview.DonationRecyclerViewAdapter;
import com.example.kamibisa.ui.viewmodel.HomeViewModel;
import com.example.kamibisa.ui.viewmodel.factory.HomeViewModelFactory;
import com.example.kamibisa.utils.InjectionUtilities;

import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener {
    public static String TAG = "HomeFragment";

    private View rootView;

    private HomeViewModel homeViewModel;

    private ImageButton informationButton;

    private RecyclerView urgentDonationListRecyclerView;
    private DonationRecyclerViewAdapter urgentDonationListAdapter;

    private RecyclerView selectedDonationListRecyclerView;
    private DonationRecyclerViewAdapter selectedDonationListAdapter;

    private RecyclerView categoryDonationListRecyclerView;
    private DonationRecyclerViewAdapter categoryDonationListAdapter;

    private LinearLayout pendidikanCategoryLinearLayout;
    private LinearLayout lingkunganCategoryLinearLayout;
    private LinearLayout kegiatanSosialCategoryLinearLayout;
    private LinearLayout lainnyaCategoryLinearLayout;

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
        setOnClickListeners();
    }

    @Override
    public void onResume() {
        super.onResume();

        homeViewModel.updateDonationList();
    }

    private void initializeUi() {
        this.informationButton = rootView.findViewById(R.id.btn_home_information);

        this.pendidikanCategoryLinearLayout = rootView.findViewById(R.id.ll_home_category_pendidikan);
        this.lingkunganCategoryLinearLayout = rootView.findViewById(R.id.ll_home_category_lingkungan);
        this.kegiatanSosialCategoryLinearLayout = rootView.findViewById(R.id.ll_home_category_kegiatanSosial);
        this.lainnyaCategoryLinearLayout = rootView.findViewById(R.id.ll_home_category_lainnya);

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
                    public void onChanged(List<Donation> donations) {
                        selectedDonationListAdapter.setDonationList(donations);
                        selectedDonationListAdapter.notifyDataSetChanged();

                        Log.d(TAG, String.format("Selected charity list changed. %d items in list",
                                selectedDonationListRecyclerView.getAdapter().getItemCount()));
                    }
                });

        homeViewModel.getCategoryDonationList()
                .observe(getViewLifecycleOwner(), new Observer<List<Donation>>() {
                    @Override
                    public void onChanged(List<Donation> donations) {
                        categoryDonationListAdapter.setDonationList(donations);
                        categoryDonationListAdapter.notifyDataSetChanged();
                    }
                });
    }

    public void initializeDonationRecyclerView() {
        // Urgent charity list
        urgentDonationListRecyclerView = rootView.findViewById(R.id.rv_home_urgentDonationList);

        LinearLayoutManager urgentLayoutManager = new LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false);
        urgentDonationListRecyclerView.setLayoutManager(urgentLayoutManager);

        urgentDonationListAdapter = new DonationRecyclerViewAdapter(requireContext(),
                homeViewModel.getUrgentDonationList().getValue());
        urgentDonationListRecyclerView.setAdapter(urgentDonationListAdapter);



        // Selected charity list
        selectedDonationListRecyclerView = rootView.findViewById(R.id.rv_home_selectedDonationList);

        LinearLayoutManager selectedLayoutManager = new LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false);
        selectedDonationListRecyclerView.setLayoutManager(selectedLayoutManager);

        selectedDonationListAdapter = new DonationRecyclerViewAdapter(requireContext(),
                homeViewModel.getSelectedDonationList().getValue());
        selectedDonationListRecyclerView.setAdapter(selectedDonationListAdapter);



        // Category charity list
        categoryDonationListRecyclerView = rootView.findViewById(R.id.rv_home_categoryDonationList);

        LinearLayoutManager categoryDonationLayoutManager = new LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false);
        categoryDonationListRecyclerView.setLayoutManager(categoryDonationLayoutManager);

        categoryDonationListAdapter = new DonationRecyclerViewAdapter(requireContext(),
                homeViewModel.getCategoryDonationList().getValue());
        categoryDonationListRecyclerView.setAdapter(categoryDonationListAdapter);
    }

    private void setOnClickListeners() {
        this.informationButton.setOnClickListener(this);

        this.pendidikanCategoryLinearLayout.setOnClickListener(this);
        this.lingkunganCategoryLinearLayout.setOnClickListener(this);
        this.kegiatanSosialCategoryLinearLayout.setOnClickListener(this);
        this.lainnyaCategoryLinearLayout.setOnClickListener(this);
    }

    @Override
    @SuppressLint("NonConstantResourceId")
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_home_information:
                gotoDonationInformationPage();
                break;

            case R.id.ll_home_category_pendidikan:
                homeViewModel.updateCategoryDonationList("Pendidikan");
                break;

            case R.id.ll_home_category_lingkungan:
                homeViewModel.updateCategoryDonationList("Lingkungan");

                break;

            case R.id.ll_home_category_kegiatanSosial:
                homeViewModel.updateCategoryDonationList("Kegiatan Sosial");
                break;

            case R.id.ll_home_category_lainnya:
                homeViewModel.updateCategoryDonationList("*");
                break;

            default:
                break;
        }
    }

    private void gotoDonationInformationPage() {
        Intent newIntent = new Intent(this.requireContext(), DonationInformationActivity.class);
        this.requireActivity().startActivity(newIntent);
    }
}