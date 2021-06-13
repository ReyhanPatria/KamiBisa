package com.example.kamibisa.ui.view.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kamibisa.R;
import com.example.kamibisa.data.model.Donation;

import java.util.List;

public class DonationRecyclerViewAdapter extends RecyclerView.Adapter<DonationRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "DonationRecyclerViewAdapter";

    private Context context;
    private List<Donation> donationList;

    public DonationRecyclerViewAdapter(Context context, List<Donation> donationList) {
        this.context = context;
        this.donationList = donationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_charity_display, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DonationRecyclerViewAdapter.ViewHolder holder, int position) {
        String title = donationList.get(position).getTitle();
        String gatheredAmount = String.valueOf(donationList.get(position).getGatheredAmount());
        String daysLeft = String.valueOf(donationList.get(position).getDaysLeft());

        holder.titleTextView.setText(title);
        holder.gatheredAmountTextView.setText(gatheredAmount);
        holder.daysLeftTextView.setText(daysLeft);
    }

    @Override
    public int getItemCount() {
        return donationList.size();
    }

    public void setCharityList(List<Donation> donationList) {
        this.donationList = donationList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "ViewHolder";

        private RelativeLayout listItemParentLayout;
        private ImageView thumbnailImageView;
        private TextView titleTextView;
        private TextView gatheredAmountTextView;
        private TextView daysLeftTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initializeUi();
        }

        public void initializeUi() {
            this.listItemParentLayout = itemView.findViewById(R.id.rl_listitem_parent);
            this.thumbnailImageView = itemView.findViewById(R.id.img_listitem_thumbnail);
            this.titleTextView = itemView.findViewById(R.id.tv_listitem_title);
            this.gatheredAmountTextView = itemView.findViewById(R.id.tv_listitem_gathered_amount);
            this.daysLeftTextView = itemView.findViewById(R.id.tv_listitem_days_left);
        }
    }
}
