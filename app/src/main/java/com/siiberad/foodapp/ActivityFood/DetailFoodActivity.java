package com.siiberad.foodapp.ActivityFood;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.siiberad.foodapp.Database.AppDatabase;
import com.siiberad.foodapp.R;
import com.siiberad.foodapp.Database.FoodModel.FoodModel;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DetailFoodActivity extends AppCompatActivity implements Serializable {

    ImageView image;
    TextView name, phone, city, date, harga;
    ImageView EditButton;
    FoodModel foodModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_detail_food);

        name= (TextView) findViewById(R.id.nameTextViewDetail);
        phone= (TextView) findViewById(R.id.phoneTextViewDetail);
        harga= (TextView) findViewById(R.id.hargaTextViewDetail);
        city= (TextView) findViewById(R.id.cityTextViewDetail);
        date= (TextView)findViewById(R.id.dateTextViewDetail);
        image= (ImageView) findViewById(R.id.coverImageDetail);
        EditButton= (ImageView) findViewById(R.id.editButton);

        //RECEIVE OUR DATA
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        foodModel = (FoodModel) bundle.getSerializable("food");


        EditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailFoodActivity.this, EditFoodActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("edit", foodModel);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onResume() {
        super.onResume();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                foodModel = AppDatabase.getDatabase(getApplicationContext()).foodModelDao().GetMenuById(foodModel.getId());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                name.setText(foodModel.getName());
                phone.setText("No : " + foodModel.getPhone());
                harga.setText("Rp. " + foodModel.getHarga());
                city.setText("Alamat : " + foodModel.getCity());
                String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(foodModel.getBorrowDate());
                date.setText(date_n);

                byte[] byteArrayBitmap = foodModel.getImage();
                Bitmap bmp = BitmapFactory.decodeByteArray(byteArrayBitmap, 0, byteArrayBitmap.length);
                image.setImageBitmap(bmp);
            }
        }.execute();

    }
}
