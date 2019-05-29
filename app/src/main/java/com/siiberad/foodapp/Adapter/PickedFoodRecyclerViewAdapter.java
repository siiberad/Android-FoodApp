package com.siiberad.foodapp.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.siiberad.foodapp.Database.FoodModel.FoodModel;
import com.siiberad.foodapp.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class PickedFoodRecyclerViewAdapter extends RecyclerView.Adapter<PickedFoodRecyclerViewAdapter.RecyclerViewHolder> {

    private List<FoodModel> foodModelList;
    private View.OnLongClickListener onLongClickListener;


    public PickedFoodRecyclerViewAdapter(List<FoodModel> foodModelList, View.OnLongClickListener onLongClickListener) {
        this.foodModelList = foodModelList;
        this.onLongClickListener = onLongClickListener;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_food, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        final FoodModel foodModel = foodModelList.get(position);
        holder.nameTextView.setText(foodModel.getName());
        holder.phoneTextView.setText(foodModel.getPhone());
        holder.cityTextView.setText(foodModel.getCity());

        byte[] byteArrayBitmap = foodModel.getImage();
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArrayBitmap, 0, byteArrayBitmap.length);
        holder.imageTextView.setImageBitmap(bmp);

        holder.itemView.setTag(foodModel);
        holder.itemView.setOnLongClickListener(onLongClickListener);
    }

    @Override
    public int getItemCount() {
        return foodModelList.size();
    }

    public void addItems(List<FoodModel> foodModelList) {
        this.foodModelList = foodModelList;
        notifyDataSetChanged();
    }
    public void addItem(FoodModel foodModelList) {
        this.foodModelList.add(foodModelList);
        notifyDataSetChanged();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView phoneTextView;
        private TextView cityTextView;
        private ImageView imageTextView;

        RecyclerViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.nameTextView);
            phoneTextView = view.findViewById(R.id.phoneTextView);
            cityTextView = view.findViewById(R.id.cityTextView);
            imageTextView = view.findViewById(R.id.coverImage);
        }
    }
}