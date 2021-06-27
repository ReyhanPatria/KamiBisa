package com.example.kamibisa.ui.view.fragment.home;

import android.content.Intent;
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
import com.example.kamibisa.ui.view.activity.ContactUsActivity;
import com.example.kamibisa.ui.view.activity.EditProfileActivity;
import com.example.kamibisa.ui.view.activity.FaqActivity;
import com.example.kamibisa.ui.view.activity.HomeActivity;
import com.example.kamibisa.ui.view.activity.LandingActivity;
import com.example.kamibisa.ui.viewmodel.ProfileViewModel;
import com.example.kamibisa.ui.viewmodel.factory.ProfileViewModelFactory;
import com.example.kamibisa.utils.InjectionUtilities;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

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

    @Override
    public void onResume() {
        super.onResume();

        profileViewModel.updateUserData();
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
                gotoEditProfileActivity();
                break;

            case R.id.btn_profile_faq:
                gotoFaqActivity();
                break;

            case R.id.btn_profile_contactUs:
                gotoContactUsActivity();
                break;

            case R.id.btn_profile_logOut:
                logOut();
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

    public void gotoEditProfileActivity() {
        Intent newIntent = new Intent(this.requireContext(), EditProfileActivity.class);
        this.requireActivity().startActivity(newIntent);
    }

    public void gotoFaqActivity() {
        Intent newIntent = new Intent(this.requireContext(), FaqActivity.class);
        this.requireActivity().startActivity(newIntent);
    }

    public void gotoContactUsActivity() {
        Intent newIntent = new Intent(this.requireContext(), ContactUsActivity.class);
        this.requireActivity().startActivity(newIntent);
    }

    public void logOut() {
        FirebaseAuth.getInstance().signOut();

        Intent newIntent = new Intent(this.requireContext(), LandingActivity.class);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(newIntent);

        this.requireActivity().finish();
    }
}