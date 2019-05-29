package com.siiberad.foodapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.siiberad.foodapp.R;
import com.siiberad.foodapp.Database.UserModel.UserModel;

import java.util.List;

public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserRecyclerViewAdapter.RecyclerViewHolder> {

    private List<UserModel> userModelList;
    private View.OnLongClickListener longClickListener;
    private View.OnClickListener onClickListener;

    public UserRecyclerViewAdapter(List<UserModel> userModelList, View.OnLongClickListener longClickListener, View.OnClickListener onClickListener) {
        this.userModelList = userModelList;
        this.longClickListener = longClickListener;
        this.onClickListener = onClickListener;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_user, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        UserModel userModel = userModelList.get(position);

        holder.nameTextView.setText(userModel.getNameUser());
        holder.phoneTextView.setText(userModel.getPhoneUSer());

        holder.itemView.setTag(userModel);
        holder.itemView.setOnLongClickListener(longClickListener);
        holder.itemView.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    public void addItems(List<UserModel> userModelList) {
        this.userModelList = userModelList;
        notifyDataSetChanged();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView phoneTextView;
//        private Button detailUser;

        RecyclerViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.nameTextViewUser);
            phoneTextView = view.findViewById(R.id.phoneTextViewUser);
//            detailUser = view.findViewById(R.id.detailUser);

        }
    }
}