package com.siiberad.foodapp.ActivityFood;

import android.Manifest;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.siiberad.foodapp.ActivityUser.MainUserActivity;
import com.siiberad.foodapp.R;
import com.siiberad.foodapp.Database.FoodModel.FoodModel;
import com.siiberad.foodapp.ViewModel.FoodListViewModel;
import com.siiberad.foodapp.Adapter.FoodRecyclerViewAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainFoodActivity extends AppCompatActivity implements View.OnLongClickListener , View.OnClickListener, Serializable{

    private FoodListViewModel viewModel;
    private FoodRecyclerViewAdapter foodRecyclerViewAdapter;
    private RecyclerView recyclerView;

    private final static int STORAGE_PERMISSION_CODE = 1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_food);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        requestMultiplePermissions ();

        FloatingActionButton fab = findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainFoodActivity.this, AddFoodActivity.class));
                }
            });
            recyclerView = findViewById(R.id.recyclerView);
            foodRecyclerViewAdapter = new FoodRecyclerViewAdapter(new ArrayList<FoodModel>(), (View.OnLongClickListener) this, (View.OnClickListener) this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(foodRecyclerViewAdapter);
            viewModel = ViewModelProviders.of(this).get(FoodListViewModel.class);
            viewModel.getFoodList().observe(MainFoodActivity.this, new Observer<List<FoodModel>>() {
                @Override
                public void onChanged(@Nullable List<FoodModel> foodModels) {
                    foodRecyclerViewAdapter.addItems(foodModels);
                }
            });
        }

        @Override
        public boolean onLongClick(final View v) {

            FoodModel foodModel = (FoodModel) v.getTag();

            AlertDialog.Builder alert = new AlertDialog.Builder(
                    MainFoodActivity.this);
            alert.setMessage("Anda Yakin Ingin Menghapus Menu "+ foodModel.getName() +" ?" );
            alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FoodModel foodModel = (FoodModel) v.getTag();
                    viewModel.deleteItem(foodModel);
                    dialog.dismiss();
                    Toast.makeText(MainFoodActivity.this, "Menu " +foodModel.getName() + " Di Hapus", Toast.LENGTH_SHORT).show();
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
            FoodModel foodModel = (FoodModel) v.getTag();
            Intent intent = new Intent(this, DetailFoodActivity.class);
            Bundle b = new Bundle();
            b.putSerializable("food", foodModel);
            intent.putExtras(b);
            startActivity(intent);
        }
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_food, menu);
            return true;
        }
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if (item.getItemId() == R.id.userMenu) {
                Intent f = new Intent(this, MainUserActivity.class);
                startActivity(f);
                return true;

            }
            return super.onOptionsItemSelected(item);
        }

        public void requestMultiplePermissions () {
            Dexter.withActivity(this)
                    .withPermissions(
//                            Manifest.permission.CAMERA,
//                            Manifest.permission.INTERNET,
                            Manifest.permission.READ_EXTERNAL_STORAGE
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                            Manifest.permission.ACCESS_COARSE_LOCATION,
//                            Manifest.permission.ACCESS_FINE_LOCATION
                    )
                    .withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            // check if all permissions are granted
                            if (report.areAllPermissionsGranted()) {
                                Toast.makeText(getApplicationContext(), "Welcome !", Toast.LENGTH_SHORT).show();
                            }

                            // check for permanent denial of any permission
                            if (report.isAnyPermissionPermanentlyDenied()) {
                                // show alert dialog navigating to Settings
                                openSettingsDialog();
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    }).
                    withErrorListener(new PermissionRequestErrorListener() {
                        @Override
                        public void onError(DexterError error) {
                            Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .onSameThread()
                    .check();
        }
        private void openSettingsDialog () {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainFoodActivity.this);
            builder.setTitle("Required Permissions");
            builder.setMessage("This app require permission to use awesome feature. Grant them in app settings.");
            builder.setPositiveButton("Take Me To SETTINGS", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivityForResult(intent, 101);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }
        @Override
        public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
                                                 @NonNull int[] grantResults){
            if (requestCode == STORAGE_PERMISSION_CODE) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
                }
            }
        }
}
