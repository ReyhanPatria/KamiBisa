package com.example.kamibisa.ui.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.kamibisa.R;
import com.example.kamibisa.data.model.User;
import com.example.kamibisa.ui.view.fragment.RegisterDataFragment;
import com.example.kamibisa.ui.view.fragment.RegisterPasswordFragment;
import com.example.kamibisa.ui.viewmodel.RegisterViewModel;
import com.example.kamibisa.ui.viewmodel.factory.RegisterViewModelFactory;
import com.example.kamibisa.utils.InjectionUtilities;

public class RegisterActivity extends AppCompatActivity {
    public static final String TAG = "RegisterActivity";

    private Fragment registerDataFragment;
    private Fragment registerPasswordFragment;
    private ProgressBar progressBarRegister;

    private RegisterViewModel registerViewModel;

    private User newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initializeUiWidgets();
        initializeViewModel();
        observeVariables();

        // Show register data fragment
        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_register_fragment, registerDataFragment)
                .commit();
    }

    private void initializeUiWidgets() {
        // Create register fragments
        registerDataFragment = RegisterDataFragment.newInstance();
        registerPasswordFragment = RegisterPasswordFragment.newInstance();

        // Initialize other widgets
        progressBarRegister = this.findViewById(R.id.pb_register);
    }

    private void initializeViewModel() {
        RegisterViewModelFactory factory = InjectionUtilities.getInstance().provideRegisterViewModelFactory();
        registerViewModel = new ViewModelProvider(this, factory).get(RegisterViewModel.class);
    }

    private void observeVariables() {
        // Observe changes to isUpdating variable
        registerViewModel.getIsUpdating().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                // If isUpdating then show progress bar, else don't show
                if(aBoolean) {
                    showProgressBar();
                }
                else {
                    hideProgressBar();
                }
            }
        });

        // Observe changes to isRegisterComplete variable
        registerViewModel.getIsRegisterComplete().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean) {
                    Intent newIntent = new Intent(getApplicationContext(), HomeActivity.class);
                    newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(newIntent);
                    finish();
                }
            }
        });

        // Observe changes to isEmailUsed variable
        registerViewModel.getIsEmailUsed().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean) {
                    Toast.makeText(
                            RegisterActivity.this,
                            "Email has been used",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }

    public void showProgressBar() {
        progressBarRegister.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void hideProgressBar() {
        progressBarRegister.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void registerUser(String password) {
        registerViewModel.registerUser(newUser, password);
    }

    public Fragment getRegisterDataFragment() {
        return registerDataFragment;
    }

    public Fragment getRegisterPasswordFragment() {
        return registerPasswordFragment;
    }

    public RegisterViewModel getRegisterViewModel() {
        return registerViewModel;
    }

    public User getNewUser() {
        return newUser;
    }

    public void setNewUser(User newUser) {
        this.newUser = newUser;
    }
}