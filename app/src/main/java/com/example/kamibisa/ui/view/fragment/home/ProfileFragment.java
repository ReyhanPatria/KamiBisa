package com.example.kamibisa.ui.view.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.kamibisa.R;
import com.example.kamibisa.data.model.User;
import com.example.kamibisa.ui.view.activity.HomeActivity;
import com.example.kamibisa.ui.viewmodel.ProfileViewModel;
import com.example.kamibisa.ui.viewmodel.factory.ProfileViewModelFactory;
import com.example.kamibisa.utils.InjectionUtilities;
import com.google.android.material.button.MaterialButton;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "ProfileFragment";

    private View rootView;

    private ProfileViewModel profileViewModel;

    private TextView userNameTextView;
    private TextView userPhoneTextView;

    private MaterialButton editProfileButton;
    private MaterialButton faqButton;
    private MaterialButton contactUsButton;
    private MaterialButton logOutButton;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
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
        this.rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        return this.rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeViewModel();
        observeVariables();
        initializeUi();
        setOnClickListeners();
    }

    private void initializeViewModel() {
        ProfileViewModelFactory factory = InjectionUtilities.getInstance()
                .provideProfileViewModelFactory();
        this.profileViewModel = new ViewModelProvider(this, factory)
                .get(ProfileViewModel.class);
    }

    private void observeVariables() {
        this.profileViewModel.getIsUpdating()
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

        this.profileViewModel.getUserData()
                .observe(getViewLifecycleOwner(), new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        if(user.getId() != null || user.getId() != null) {
                            setUserData(user);
                        }
                    }
                });
    }

    private void initializeUi() {
        this.userNameTextView = rootView.findViewById(R.id.tv_profile_username);
        this.userPhoneTextView = rootView.findViewById(R.id.tv_profile_phone);

        this.editProfileButton = rootView.findViewById(R.id.btn_profile_editProfile);
        this.faqButton = rootView.findViewById(R.id.btn_profile_faq);
        this.contactUsButton = rootView.findViewById(R.id.btn_profile_contactUs);
        this.logOutButton = rootView.findViewById(R.id.btn_profile_logOut);
    }

    private void setOnClickListeners() {
        this.editProfileButton.setOnClickListener(this);
        this.faqButton.setOnClickListener(this);
        this.contactUsButton.setOnClickListener(this);
        this.logOutButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_profile_editProfile:
                // TODO: Create function to start edit profile activity
                break;

            case R.id.btn_profile_faq:
                // TODO: Create function to start faq activity
                break;

            case R.id.btn_profile_contactUs:
                // TODO: Create function to start contact us activity
                break;

            case R.id.btn_profile_logOut:
                // TODO: Create function to log out
                break;

            default:
                break;
        }
    }

    public void setUserData(User user) {
        String userName = user.getUsername();
        String phone = user.getPhone();

        this.userNameTextView.setText(userName);
        this.userPhoneTextView.setText(phone);
    }
}