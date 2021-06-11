package com.example.kamibisa.ui.view.fragment;

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
import com.example.kamibisa.data.model.Charity;
import com.example.kamibisa.ui.view.activity.HomeActivity;
import com.example.kamibisa.ui.view.recyclerview.CharityRecyclerViewAdapter;
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

    private RecyclerView urgentCharityListRecyclerView;
    private CharityRecyclerViewAdapter urgentCharityListAdapter;

    private RecyclerView selectedCharityListRecyclerView;
    private CharityRecyclerViewAdapter selectedCharityListAdapter;

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
        initializeCharityRecyclerView();
    }

    private void initializeViewModel() {
        HomeViewModelFactory factory = InjectionUtilities.getInstance().provideHomeViewModelFactory();
        homeViewModel = new ViewModelProvider(this, factory).get(HomeViewModel.class);
    }

    private void observeVariables() {
        // TODO: Set variables to be observed
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

        homeViewModel.getUrgentCharityList()
                .observe(getViewLifecycleOwner(), new Observer<List<Charity>>() {
                    @Override
                    public void onChanged(List<Charity> charities) {
                        urgentCharityListAdapter.setCharityList(charities);
                        urgentCharityListAdapter.notifyDataSetChanged();

                        Log.d(TAG, String.format("Urgent charity list changed. %d items in list",
                                urgentCharityListRecyclerView.getAdapter().getItemCount()));
                    }
                });

        homeViewModel.getSelectedCharityList()
                .observe(getViewLifecycleOwner(), new Observer<List<Charity>>() {
                    @Override
                    public void onChanged(List<Charity> charities) {
                        selectedCharityListAdapter.setCharityList(charities);
                        selectedCharityListAdapter.notifyDataSetChanged();

                        Log.d(TAG, String.format("Selected charity list changed. %d items in list",
                                selectedCharityListRecyclerView.getAdapter().getItemCount()));
                    }
                });
    }

    public void initializeCharityRecyclerView() {
        // Urgent charity list
        urgentCharityListRecyclerView = rootView.findViewById(R.id.rv_home_urgentCharityList);

        LinearLayoutManager urgentLayoutManager = new LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false);
        urgentCharityListRecyclerView.setLayoutManager(urgentLayoutManager);

        urgentCharityListAdapter = new CharityRecyclerViewAdapter(requireContext(),
                homeViewModel.getUrgentCharityList().getValue());
        urgentCharityListRecyclerView.setAdapter(urgentCharityListAdapter);


        // Selected charity list
        selectedCharityListRecyclerView = rootView.findViewById(R.id.rv_home_selectedCharityList);

        LinearLayoutManager selectedLayoutManager = new LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false);
        selectedCharityListRecyclerView.setLayoutManager(selectedLayoutManager);

        selectedCharityListAdapter = new CharityRecyclerViewAdapter(requireContext(),
                homeViewModel.getSelectedCharityList().getValue());
        selectedCharityListRecyclerView.setAdapter(selectedCharityListAdapter);
    }
}