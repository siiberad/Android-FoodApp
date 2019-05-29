package com.siiberad.foodapp.Adapter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.siiberad.foodapp.R;
import com.siiberad.foodapp.Database.FoodModel.FoodModel;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class FoodRecyclerViewAdapter extends RecyclerView.Adapter<FoodRecyclerViewAdapter.RecyclerViewHolder> {

    private List<FoodModel> foodModelList;
    private View.OnLongClickListener longClickListener;
    private View.OnClickListener onClickListener;

    public FoodRecyclerViewAdapter(List<FoodModel> foodModelList, View.OnLongClickListener longClickListener, View.OnClickListener onClickListener) {
        this.foodModelList = foodModelList;
        this.longClickListener = longClickListener;
        this.onClickListener = onClickListener;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_food, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        FoodModel foodModel = foodModelList.get(position);
        holder.nameTextView.setText(foodModel.getName());
        holder.phoneTextView.setText(foodModel.getPhone());
        holder.hargaTextView.setText("Rp. "+ foodModel.getHarga());
        String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(foodModel.getBorrowDate());
        holder.dateTextView.setText(date_n);

        byte[] byteArrayBitmap = foodModel.getImage();
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArrayBitmap, 0, byteArrayBitmap.length);
        holder.imageTextView.setImageBitmap(bmp);

        holder.itemView.setTag(foodModel);
        holder.itemView.setOnLongClickListener(longClickListener);
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