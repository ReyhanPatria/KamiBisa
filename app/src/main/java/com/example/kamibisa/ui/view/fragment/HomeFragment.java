package com.example.kamibisa.ui.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kamibisa.R;
import com.example.kamibisa.data.model.Charity;
import com.example.kamibisa.ui.view.recyclerview.CharityRecyclerViewAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    public static String TAG = "HomeFragment";

    private View rootView;

    // TODO: Get data from database
    private ArrayList<Charity> charityList;

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

        initializeCharityRecyclerView();
    }

    public void initializeCharityRecyclerView() {
        RecyclerView charityListRecyclerView = rootView.findViewById(R.id.rv_home_charity_list);
        RecyclerView.Adapter charityListAdapter = new CharityRecyclerViewAdapter(requireContext(),
                charityList);

        charityListRecyclerView.setAdapter(charityListAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        charityListRecyclerView.setLayoutManager(layoutManager);
    }
}