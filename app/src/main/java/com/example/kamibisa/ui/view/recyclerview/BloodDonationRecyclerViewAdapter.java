package com.example.kamibisa.ui.view.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kamibisa.R;
import com.example.kamibisa.data.model.BloodDonation;
import com.example.kamibisa.ui.view.activity.BloodDonationPageActivity;

import java.util.List;

public class BloodDonationRecyclerViewAdapter extends RecyclerView.Adapter<BloodDonationRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "BloodDonationRecyclerViewAdapter";

    private Context context;
    private List<BloodDonation> bloodDonationList;

    public BloodDonationRecyclerViewAdapter(Context context, List<BloodDonation> bloodDonationList) {
        this.context = context;
        this.bloodDonationList = bloodDonationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_blooddonation_display, parent, false);
        BloodDonationRecyclerViewAdapter.ViewHolder viewHolder = new BloodDonationRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BloodDonationRecyclerViewAdapter.ViewHolder holder, int position) {
        String beneficiaryname = bloodDonationList.get(position).getBeneficiaryName();
        String bloodType = String.valueOf(bloodDonationList.get(position).getBeneficiaryBloodType());
        String daysLeft = String.valueOf(bloodDonationList.get(position).getDaysLeft());

        holder.bloodDonationId = bloodDonationList.get(position).getId();

        holder.beneficiaryNameTextView.setText(beneficiaryname);
        holder.bloodTypeTextView.setText(bloodType);
        holder.daysLeftTextView.setText(daysLeft);
    }

    @Override
    public int getItemCount() {
        return bloodDonationList.size();
    }

    public void setBloodDonationList(List<BloodDonation> bloodDonationList) {
        this.bloodDonationList = bloodDonationList;
    }





    // THE ITEM OF THE RECYCLER VIEW
    public class ViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "ViewHolder";

        private String bloodDonationId;

        private RelativeLayout listItemParentLayout;
        private ImageView thumbnailImageView;
        private TextView beneficiaryNameTextView;
        private TextView bloodTypeTextView;
        private TextView daysLeftTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initializeUi();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Move to blood donation page
                    Intent newIntent = new Intent(v.getContext(), BloodDonationPageActivity.class);
                    newIntent.putExtra("bloodDonationId", bloodDonationId);
                    v.getContext().startActivity(newIntent);
                }
            });
        }

        public void initializeUi() {
            this.listItemParentLayout = itemView.findViewById(R.id.rl_listitem_parent);
            this.thumbnailImageView = itemView.findViewById(R.id.img_listitem_thumbnail);
            this.beneficiaryNameTextView = itemView.findViewById(R.id.tv_listitem_beneficiaryName);
            this.bloodTypeTextView = itemView.findViewById(R.id.tv_listitem_bloodType);
            this.daysLeftTextView = itemView.findViewById(R.id.tv_listitem_daysLeft);
        }
    }
}
