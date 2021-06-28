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
import com.example.kamibisa.data.model.Donation;
import com.example.kamibisa.ui.view.activity.DonationActivity;

import java.util.List;

public class SearchResultRecyclerViewAdapter extends RecyclerView.Adapter<SearchResultRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "DonationRecyclerViewAdapter";

    private Context context;
    private List<Donation> donationList;

    public SearchResultRecyclerViewAdapter(Context context, List<Donation> donationList) {
        this.context = context;
        this.donationList = donationList;
    }

    @NonNull
    @Override
    public SearchResultRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_donation_search_display, parent, false);
        SearchResultRecyclerViewAdapter.ViewHolder viewHolder = new SearchResultRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultRecyclerViewAdapter.ViewHolder holder, int position) {
        String donationTitle = donationList.get(position).getTitle();
        String creatorName = donationList.get(position).getCreatorName();

        holder.donationId = donationList.get(position).getId();

        holder.donationTitleTextView.setText(donationTitle);
        holder.creatorNameTextView.setText(creatorName);
    }

    @Override
    public int getItemCount() {
        return donationList.size();
    }

    public void setDonationList(List<Donation> donationList) {
        this.donationList = donationList;
    }





    public class ViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "ViewHolder";

        private String donationId;

        private ImageView thumbnailImageView;

        private TextView donationTitleTextView;
        private TextView creatorNameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initializeUi();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Move to donation page
                    Intent newIntent = new Intent(v.getContext(), DonationActivity.class);
                    newIntent.putExtra("donationId", donationId);
                    v.getContext().startActivity(newIntent);
                }
            });
        }

        public void initializeUi() {
            this.thumbnailImageView = itemView.findViewById(R.id.img_listitem_thumbnail);
            this.donationTitleTextView = itemView.findViewById(R.id.tv_listitem_donationTitle);
            this.creatorNameTextView = itemView.findViewById(R.id.tv_listitem_creatorName);
        }
    }
}
