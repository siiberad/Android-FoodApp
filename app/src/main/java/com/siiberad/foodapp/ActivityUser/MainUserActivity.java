package com.siiberad.foodapp.ActivityUser;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.siiberad.foodapp.ActivityFood.MainFoodActivity;
import com.siiberad.foodapp.R;
import com.siiberad.foodapp.Database.UserModel.UserModel;
import com.siiberad.foodapp.ViewModel.UserListViewModel;
import com.siiberad.foodapp.Adapter.UserRecyclerViewAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainUserActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private UserListViewModel viewModel;
    private UserRecyclerViewAdapter userRecyclerViewAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
        Toolbar toolbar = findViewById(R.id.toolbarUser);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerViewUser);
        userRecyclerViewAdapter = new UserRecyclerViewAdapter(new ArrayList<UserModel>(), (View.OnLongClickListener) this, (View.OnClickListener) this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(userRecyclerViewAdapter);
        viewModel = ViewModelProviders.of(this).get(UserListViewModel.class);
        viewModel.getAllUsersItems().observe(MainUserActivity.this, new Observer<List<UserModel>>() {
            @Override
            public void onChanged(@Nullable List<UserModel> userModels) {
                userRecyclerViewAdapter.addItems(userModels);
            }
        });
    }

    @Override
    public boolean onLongClick(final View v) {

        UserModel userModel = (UserModel) v.getTag();

        AlertDialog.Builder alert = new AlertDialog.Builder(
                MainUserActivity.this);
        alert.setMessage("Anda Yakin Ingin Menghapus User "+ userModel.getNameUser() +" ?" );
        alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                UserModel userModel = (UserModel) v.getTag();
                viewModel.deleteItem(userModel);
                dialog.dismiss();
                Toast.makeText(MainUserActivity.this, "User " +userModel.getNameUser() + " Di Hapus", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View v) {
        UserModel userModel = (UserModel) v.getTag();
        Intent intent = new Intent(this, UserPickMenu.class);
        Bundle b = new Bundle();
        b.putSerializable("menu", userModel);
        intent.putExtras(b);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add:
                startActivity(new Intent(this, AddUserActivity.class));
                return true;
            case R.id.foodMenu:
                startActivity(new Intent(this, MainFoodActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
