package com.siiberad.foodapp.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.siiberad.foodapp.Database.FoodModel.FoodModel;
import com.siiberad.foodapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PickFoodRecyclerViewAdapter extends RecyclerView.Adapter<PickFoodRecyclerViewAdapter.RecyclerViewHolder> {

    private List<FoodModel> foodModelList;

    private View.OnClickListener onClickListener;

    public PickFoodRecyclerViewAdapter(List<FoodModel> foodModelList, View.OnClickListener onClickListener) {
        this.foodModelList = foodModelList;
        this.onClickListener = onClickListener;
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
        holder.hargaTextView.setText(foodModel.getHarga());
        String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(foodModel.getBorrowDate());
        holder.dateTextView.setText(date_n);

        byte[] byteArrayBitmap = foodModel.getImage();
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArrayBitmap, 0, byteArrayBitmap.length);
        holder.imageTextView.setImageBitmap(bmp);

        holder.itemView.setTag(foodModel);
        holder.itemView.setOnClickListener(onClickListener);

    }

    @Override
    public int getItemCount() {
        return foodModelList.size();
    }
    public void addItems(List<FoodModel> foodModelList) {
        this.foodModelList = foodModelList;
        notifyDataSetChanged();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView phoneTextView;
        private TextView hargaTextView;
        private TextView dateTextView;
        private ImageView imageTextView;

        RecyclerViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.nameTextView);
            phoneTextView = view.findViewById(R.id.phoneTextView);
            hargaTextView = view.findViewById(R.id.hargaTextView);
            dateTextView = view.findViewById(R.id.dateTextView);
            imageTextView = view.findViewById(R.id.coverImage);
        }
    }
}