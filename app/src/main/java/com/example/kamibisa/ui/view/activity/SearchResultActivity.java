package com.example.kamibisa.ui.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.example.kamibisa.R;
import com.example.kamibisa.data.model.Donation;
import com.example.kamibisa.ui.view.recyclerview.DonationRecyclerViewAdapter;
import com.example.kamibisa.ui.view.recyclerview.SearchResultRecyclerViewAdapter;
import com.example.kamibisa.ui.viewmodel.SearchResultViewModel;
import com.example.kamibisa.ui.viewmodel.factory.SearchResultViewModelFactory;
import com.example.kamibisa.utils.InjectionUtilities;

import java.util.List;

public class SearchResultActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";

    private SearchResultViewModel searchResultViewModel;

    private SearchView searchView;

    private RecyclerView donationListRecyclerView;
    private SearchResultRecyclerViewAdapter donationListAdapter;

    private ProgressBar progressBar;

    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        this.query = this.getIntent().getStringExtra("query");

        initializeViewModel();
        observeVariables();
        initializeUi();
        setOnQueryListeners();

        searchResultViewModel.loadDonationList(this.query);
    }

    @Override
    protected void onResume() {
        super.onResume();

        searchResultViewModel.loadDonationList(this.query);
    }

    private void initializeViewModel() {
        SearchResultViewModelFactory factory = InjectionUtilities.getInstance()
                .provideSearchResultViewModelFactory();
        this.searchResultViewModel = new ViewModelProvider(this, factory)
                .get(SearchResultViewModel.class);
    }

    private void observeVariables() {
        this.searchResultViewModel.getIsUpdating()
                .observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if(aBoolean) {
                            showProgressBar();
                        }
                        else {
                            hideProgressBar();
                        }
                    }
                });

        this.searchResultViewModel.getDonationList()
                .observe(this, new Observer<List<Donation>>() {
                    @Override
                    public void onChanged(List<Donation> donations) {
                        donationListAdapter.setDonationList(donations);
                        donationListAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void initializeUi() {
        this.searchView = this.findViewById(R.id.search_bar);
        this.searchView.setQuery(this.query, false);

        this.progressBar = this.findViewById(R.id.pb_searchResult);

        initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        donationListRecyclerView = this.findViewById(R.id.rv_searchResult_donationList);

        LinearLayoutManager donationLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        donationListRecyclerView.setLayoutManager(donationLayoutManager);

        donationListAdapter = new SearchResultRecyclerViewAdapter(this,
                searchResultViewModel.getDonationList().getValue());
        donationListRecyclerView.setAdapter(donationListAdapter);
    }

    private void setOnQueryListeners() {
        this.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent newIntent = new Intent(getApplicationContext(), SearchResultActivity.class);
                newIntent.putExtra("query", query);
                startActivity(newIntent);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}