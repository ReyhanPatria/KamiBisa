package com.example.kamibisa.ui.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kamibisa.R;
import com.example.kamibisa.data.model.Donation;
import com.example.kamibisa.data.model.DonationRecord;
import com.example.kamibisa.ui.viewmodel.DonateViewModel;
import com.example.kamibisa.ui.viewmodel.DonationViewModel;
import com.example.kamibisa.ui.viewmodel.factory.DonateViewModelFactory;
import com.example.kamibisa.ui.viewmodel.factory.DonationViewModelFactory;
import com.example.kamibisa.utils.InjectionUtilities;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

import java.time.Instant;
import java.util.Date;

public class DonateActivity extends AppCompatActivity implements View.OnClickListener {
    private static String TAG = "DonateActivity";

    private DonateViewModel donateViewModel;

    private ImageButton backButton;
    private EditText amountEditText;
    private Spinner paymentMethodSpinner;
    private TextView accountTextView;
    private MaterialButton payButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        initializeViewModel();
        observeVariables();
        initializeUi();
        setOnClickListeners();
        setOnItemSelectedListeners();
    }

    private void initializeUi() {
        this.backButton = this.findViewById(R.id.btn_donate_back);
        this.amountEditText = this.findViewById(R.id.edt_donate_amount);
        this.payButton = this.findViewById(R.id.btn_donate_pay);
        this.accountTextView = this.findViewById(R.id.tv_donate_account);
        this.paymentMethodSpinner = this.findViewById(R.id.spin_donate_paymentMethod);
        this.progressBar = this.findViewById(R.id.pb_donate);

        ArrayAdapter<CharSequence> paymentMethodSpinnerAdapter = ArrayAdapter
                .createFromResource(this, R.array.payment_methods, R.layout.custom_spinner_item);
        paymentMethodSpinnerAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        paymentMethodSpinner.setAdapter(paymentMethodSpinnerAdapter);
    }

    private void initializeViewModel() {
        DonateViewModelFactory factory = InjectionUtilities.getInstance()
                .provideDonateViewModelFactory();
        donateViewModel = new ViewModelProvider(this, factory)
                .get(DonateViewModel.class);
    }

    private void observeVariables() {
        donateViewModel.getIsUpdating().observe(this, new Observer<Boolean>() {
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

        donateViewModel.getIsFinished().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean) {
                    finish();
                }
            }
        });
    }

    private void setOnClickListeners() {
        this.backButton.setOnClickListener(this);
        this.payButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_donate_back:
                finish();
                break;

            case R.id.btn_donate_pay:
                donateToDonation();
                break;

            default:
                break;
        }
    }

    private void setOnItemSelectedListeners() {
        this.paymentMethodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "Position " + position);

                String message = "";

                switch(position) {
                    case 0:
                        message = "No Rekening BCA 645385976";
                        break;

                    case 1:
                        message = "No Akun OVO 08228574933";
                        break;

                    case 2:
                        message = "No Akun GoPay 0812948339";
                        break;

                    default:
                        break;
                }

                accountTextView.setText(message);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void donateToDonation() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String donationId = this.getIntent().getStringExtra("donationId");
        String amountString = amountEditText.getText().toString();
        Date donationMadeDate = Date.from(Instant.now());

        if(amountString.isEmpty()) {
            amountEditText.setError("Amount cannot be empty");
            return;
        }

        Integer amount = Integer.parseInt(amountString);

        if(amount < 10000) {
            amountEditText.setError("Minimum amount is Rp 10.000,-");
            return;
        }

        DonationRecord newDonationRecord = new DonationRecord();
        newDonationRecord.setUserId(userId);
        newDonationRecord.setDonationId(donationId);
        newDonationRecord.setDonationAmount(amount);
        newDonationRecord.setDonationMadeDate(donationMadeDate);

        donateViewModel.donateToDonation(newDonationRecord);
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