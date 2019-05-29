package com.siiberad.foodapp.ActivityUser;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.siiberad.foodapp.ActivityFood.DetailFoodActivity;
import com.siiberad.foodapp.ActivityFood.EditFoodActivity;
import com.siiberad.foodapp.ActivityFood.MainFoodActivity;
import com.siiberad.foodapp.Adapter.FoodRecyclerViewAdapter;
import com.siiberad.foodapp.Adapter.PickFoodRecyclerViewAdapter;
import com.siiberad.foodapp.Adapter.PickedFoodRecyclerViewAdapter;
import com.siiberad.foodapp.Database.FoodModel.FoodModel;
import com.siiberad.foodapp.Database.UserModel.UserModel;
import com.siiberad.foodapp.R;
import com.siiberad.foodapp.ViewModel.FoodListViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UserPickMenu extends AppCompatActivity implements View.OnClickListener{

    private FoodListViewModel viewModel;
    private PickFoodRecyclerViewAdapter pickFoodRecyclerViewAdapter;
    private RecyclerView recyclerView;
    List<FoodModel> selectedfoodModelList = new ArrayList<>();

    FoodModel foodModel;

    TextView namaUser;
    ImageView pilihMenu;
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pick_menu);

        Toolbar toolbar = findViewById(R.id.toolbarPilih);
        setSupportActionBar(toolbar);

        namaUser = (TextView) findViewById(R.id.textPilih);
        pilihMenu = (ImageView) findViewById(R.id.tambahPilihan);

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        userModel = (UserModel) bundle.getSerializable("menu");

        namaUser.setText(userModel.getNameUser());

        pilihMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedfoodModelList.isEmpty()){
                    Toast.makeText(UserPickMenu.this, "Setidaknya Pilih 1 Menu", Toast.LENGTH_SHORT).show();
                }else {

//                    Collection<FoodModel> entities = selectedfoodModelList;
//                    List<Long> ids =
//
                    foodModel = selectedfoodModelList.get(0);
                    ArrayList<Integer> idSelectedFood = new ArrayList<>();
                    for (FoodModel food: selectedfoodModelList) {
                        idSelectedFood.add(food.getId());
                    }
                    Gson gson = new Gson();
                    String formatJsonID = gson.toJson(idSelectedFood);

                    Intent intent = new Intent(UserPickMenu.this, DetailUserActivity.class);
                    Bundle b = new Bundle();
                    b.putString("ids", formatJsonID);
                    b.putSerializable("user", userModel);
                    intent.putExtras(b);
                    startActivity(intent);
                }
            }
        });

        recyclerView = findViewById(R.id.recyclerViewPick);
        pickFoodRecyclerViewAdapter = new PickFoodRecyclerViewAdapter(new ArrayList<FoodModel>(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(pickFoodRecyclerViewAdapter);
        viewModel = ViewModelProviders.of(this).get(FoodListViewModel.class);
        viewModel.getFoodList().observe(UserPickMenu.this, new Observer<List<FoodModel>>() {
            @Override
            public void onChanged(@Nullable List<FoodModel> foodModels) {
                pickFoodRecyclerViewAdapter.addItems(foodModels);
            }
        });
    }

    @Override
    public void onClick(View v) {

        foodModel = (FoodModel) v.getTag();
        foodModel.setSelected(!foodModel.isSelected());

        v.setBackgroundColor(foodModel.isSelected() ? Color.CYAN : Color.WHITE);

        if(foodModel.isSelected()){
            selectedfoodModelList.add(foodModel);
        }else{
            selectedfoodModelList.contains(foodModel);{
                selectedfoodModelList.remove(foodModel);
            }
        }
    }
}
