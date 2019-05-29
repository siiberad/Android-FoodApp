package com.siiberad.foodapp.ActivityUser;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.siiberad.foodapp.ActivityFood.DetailFoodActivity;
import com.siiberad.foodapp.ActivityFood.MainFoodActivity;
import com.siiberad.foodapp.Adapter.PickFoodRecyclerViewAdapter;
import com.siiberad.foodapp.Adapter.PickedFoodRecyclerViewAdapter;
import com.siiberad.foodapp.Database.AppDatabase;
import com.siiberad.foodapp.Database.FoodModel.FoodModel;
import com.siiberad.foodapp.Database.UserModel.UserModel;
import com.siiberad.foodapp.R;
import com.siiberad.foodapp.ViewModel.FoodListViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DetailUserActivity extends AppCompatActivity implements Serializable, View.OnLongClickListener{

    TextView detailUserName, detailUserPhone;
    private FoodListViewModel viewModel;

    private PickedFoodRecyclerViewAdapter pickedFoodRecyclerViewAdapter;
    private RecyclerView recyclerView;
    ArrayList<Integer> selectedId;


    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_detail_user);

        detailUserName = (TextView) findViewById(R.id.detailUserName);
        detailUserPhone = (TextView)findViewById(R.id.detailUserPhone);


        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        String ids = bundle.getString("ids");
        selectedId = new Gson().fromJson(ids, new TypeToken<ArrayList<Integer>>(){}.getType());

        Intent i2 = getIntent();
        Bundle bundle2 = i2.getExtras();
        UserModel userModel = (UserModel) bundle2.getSerializable("user");

        detailUserName.setText(userModel.getNameUser());
        detailUserPhone.setText(userModel.getPhoneUSer());

        recyclerView = findViewById(R.id.recyclerViewDetail);
        pickedFoodRecyclerViewAdapter = new PickedFoodRecyclerViewAdapter(new ArrayList<FoodModel>(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(pickedFoodRecyclerViewAdapter);
        viewModel = ViewModelProviders.of(this).get(FoodListViewModel.class);

        for(final int id:selectedId){

            new AsyncTask<Void, Void, FoodModel>() {
                @Override
                protected FoodModel doInBackground(Void... voids) {
                    return viewModel.getFoodById(id);
                }

                @Override
                protected void onPostExecute(FoodModel foodModel) {
                    super.onPostExecute(foodModel);
                    pickedFoodRecyclerViewAdapter.addItem(foodModel);
                }
            }.execute();
        }

    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(this, MainUserActivity.class));
        finish();

    }


    @Override
    public boolean onLongClick(final View v) {

        FoodModel foodModel = (FoodModel) v.getTag();

        AlertDialog.Builder alert = new AlertDialog.Builder(
                DetailUserActivity.this);
        alert.setMessage("Anda Yakin Ingin Detail Menu "+ foodModel.getName() +" ?" );
        alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                FoodModel foodModel = (FoodModel) v.getTag();
                Intent intent = new Intent(DetailUserActivity.this, DetailFoodActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("food", foodModel);
                intent.putExtras(b);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        alert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
        return true;
    }
}
