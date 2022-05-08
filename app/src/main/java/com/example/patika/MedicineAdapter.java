package com.example.patika;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Locale;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.ViewHolder> implements Filterable {
    private ArrayList<Medicine> medicinesData;
    private ArrayList<Medicine> medicinesDataAll;
    private Context mContext;
    private int lastPosition = -1;

    MedicineAdapter(Context context, ArrayList<Medicine> itemsData){
        this.medicinesData = itemsData;
        this.medicinesDataAll = itemsData;
        this.mContext = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item,parent, false));
    }

    @Override
    public void onBindViewHolder(MedicineAdapter.ViewHolder holder, int position) {
        Medicine currentItem = medicinesData.get(position);

        holder.bindTo(currentItem);

        if (holder.getAbsoluteAdapterPosition()>lastPosition){
            Animation animation = AnimationUtils.loadAnimation(mContext,R.anim.slide_in_row);
            holder.itemView.startAnimation(animation);
            lastPosition = holder.getAdapterPosition();
        }

    }

    @Override
    public int getItemCount() {
        return medicinesData.size();
    }

    @Override
    public Filter getFilter() {
        return medicineFilter;
    }
    private Filter medicineFilter= new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Medicine> filteredList = new ArrayList<>();
            FilterResults results = new FilterResults();

            if (charSequence == null || charSequence.length()==0){
                results.count = medicinesDataAll.size();
                results.values = medicinesDataAll;
            }else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (Medicine medicine: medicinesDataAll){
                    if (medicine.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(medicine);
                    }
                }
                results.count = filteredList.size();
                results.values = filteredList;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            medicinesData = (ArrayList) filterResults.values;
            notifyDataSetChanged();

        }
    };

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mTitleText;
        private TextView mInfoText;
        private TextView mPriceText;
        private ImageView mItemImage;
        private RatingBar mRatingBar;

        public ViewHolder( View itemView) {
            super(itemView);

            mTitleText = itemView.findViewById(R.id.itemTitle);
            mInfoText = itemView.findViewById(R.id.subTitle);
            mPriceText = itemView.findViewById(R.id.price);
            mItemImage = itemView.findViewById(R.id.itemImage);
            mRatingBar = itemView.findViewById(R.id.ratingBar);

            itemView.findViewById(R.id.add_to_cart).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Log.d("Activity", "Add cart clicked");
                }
            });
        }

        public void bindTo(Medicine currentItem) {
            mTitleText.setText(currentItem.getName());
            mInfoText.setText(currentItem.getInfo());
            mPriceText.setText(currentItem.getPrice());
            mRatingBar.setRating(currentItem.getRatedInfo());

            Glide.with(mContext).load(currentItem.getImageResorce()).into(mItemImage);
        }
    }

}

