package com.example.kamibisa.ui.view.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kamibisa.R;

import java.util.List;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "CategoryRecyclerViewAdapter";

    private Context context;

    private List<String> categoryList;

    public CategoryRecyclerViewAdapter(Context context, List<String> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_category_display, parent, false);
        CategoryRecyclerViewAdapter.ViewHolder viewHolder = new CategoryRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryRecyclerViewAdapter.ViewHolder holder, int position) {
        String categoryTitle = categoryList.get(position);

        holder.categoryTitleTextView.setText(categoryTitle);

        Integer iconId = 0;
        switch(categoryList.get(position)) {
            case "Pendidikan":
                iconId = R.drawable.ic_pendidikan;
                break;

            case "Lingkungan":
                iconId = R.drawable.ic_lingkungan;
                break;

            case "Kegiatan Sosial":
                iconId = R.drawable.ic_kegiatan_sosial;
                break;

            case "Lainnya":
                iconId = R.drawable.ic_lainnya;
                break;

            default:
                break;
        }
        holder.categoryButton.setBackgroundResource(iconId);
    }

    @Override
    public int getItemCount() {
        return this.categoryList.size();
    }

    public void setCategoryList(List<String> categoryList) {
        this.categoryList = categoryList;
    }






    public class ViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG  = "ViewHolder";

        private ImageButton categoryButton;

        private TextView categoryTitleTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initializeUi();
        }

        private void initializeUi() {
            this.categoryButton = itemView.findViewById(R.id.btn_listitem_thumbnail);
            this.categoryTitleTextView = itemView.findViewById(R.id.tv_listitem_categoryName);
        }
    }
}
