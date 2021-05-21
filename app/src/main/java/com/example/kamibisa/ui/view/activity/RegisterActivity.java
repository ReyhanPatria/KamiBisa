package com.example.kamibisa.ui.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.kamibisa.R;
import com.example.kamibisa.ui.view.fragment.RegisterDataFragment;
import com.example.kamibisa.ui.view.fragment.RegisterPasswordFragment;

public class RegisterActivity extends AppCompatActivity {

    private Fragment registerDataFragment;
    private Fragment registerPasswordFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerDataFragment = new RegisterDataFragment();
        registerPasswordFragment = new RegisterPasswordFragment();

        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_fragment, registerDataFragment)
                .commit();
    }

    public Fragment getRegisterDataFragment() {
        return registerDataFragment;
    }

    public Fragment getRegisterPasswordFragment() {
        return registerPasswordFragment;
    }
}