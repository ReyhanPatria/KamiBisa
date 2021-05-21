package com.example.kamibisa.ui.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.kamibisa.R;
import com.example.kamibisa.ui.view.activity.RegisterActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterPasswordFragment extends Fragment {

    public static String TAG = "RegisterPasswordFragment";

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
    }

    private void initializeViewWidgets() {
        // Gets all widgets
        passwordEditText = rootView.findViewById(R.id.edt_register_password);
        verifyPasswordEditText = rootView.findViewById(R.id.edt_register_verify_password);
        nextButton = rootView.findViewById(R.id.btn_register_next);
        backButton = rootView.findViewById(R.id.btn_register_back);

        // Set OnClickListeners
        // Set next button to TODO
        nextButton.setOnClickListener(v -> {
            // TODO: Registers the user and route it to homepage
        });

        backButton.setOnClickListener(v -> {
            this.getActivity().getSupportFragmentManager()
                    .popBackStack();

            Log.d(TAG, String.valueOf(getActivity().getSupportFragmentManager().getBackStackEntryCount()));

            this.getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_fragment, ((RegisterActivity) getActivity()).getRegisterDataFragment())
                    .commit();
        });
    }
}