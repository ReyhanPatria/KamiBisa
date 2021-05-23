package com.example.kamibisa.ui.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.kamibisa.R;
import com.example.kamibisa.data.model.User;
import com.example.kamibisa.ui.view.activity.RegisterActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterPasswordFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = "RegisterPasswordFragment";

    private View rootView;

    private EditText passwordEditText;
    private EditText verifyPasswordEditText;
    private Button nextButton;
    private ImageButton backButton;

    public RegisterPasswordFragment() {
        // Required empty public constructor
    }

    public static RegisterPasswordFragment newInstance() {
        RegisterPasswordFragment fragment = new RegisterPasswordFragment();
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
        rootView = inflater.inflate(R.layout.fragment_register_password, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViewWidgets();
        setOnClickListeners();
    }

    private void initializeViewWidgets() {
        passwordEditText = rootView.findViewById(R.id.edt_register_password);
        verifyPasswordEditText = rootView.findViewById(R.id.edt_register_verify_password);
        nextButton = rootView.findViewById(R.id.btn_register_next);
        backButton = rootView.findViewById(R.id.btn_register_back);
    }

    private void setOnClickListeners() {
        nextButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_register_next:
                registerUser();
                break;

            default:
                backToDataFragment();
                break;
        }
    }

    private void registerUser() {
        String password = passwordEditText.getText().toString();
        String verifyPassword = verifyPasswordEditText.getText().toString();

        String warningMessage = "";

        // Validate input fields
        if(!User.validatePasswordLength(password)) {
            warningMessage = "Password must  be 10-14 characters long";
        }
        else if(!User.verifyPassword(password, verifyPassword)) {
            warningMessage = "Password is not the same";
        }
        else if(!User.validatePasswordHasDigit(password)) {
            warningMessage = "Password must contain a number";
        }
        else {
            // Register user
            ((RegisterActivity) this.requireActivity()).registerUser(password);

            return;
        }

        // Show warning message if input fields are wrong
        Toast.makeText(requireContext(), "", Toast.LENGTH_SHORT).show();
    }

    private void backToDataFragment() {
        this.requireActivity().getSupportFragmentManager()
                .popBackStack();

        this.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_register_fragment, ((RegisterActivity) getActivity()).getRegisterDataFragment())
                .commit();
    }
}