package com.siiberad.foodapp.ActivityFood;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.siiberad.foodapp.R;
import com.siiberad.foodapp.ViewModel.AddFoodViewModel;
import com.siiberad.foodapp.Database.FoodModel.FoodModel;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Date;

public class AddFoodActivity extends AppCompatActivity {

    private Date date;

    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText hargaEditText;
    private EditText cityEditText;

    Bitmap bitmapImage;
    byte[] byteArray;
    ImageView imgView;


    private AddFoodViewModel addFoodViewModel;

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        nameEditText = findViewById(R.id.nameText);
        phoneEditText = findViewById(R.id.phoneText);
        hargaEditText = findViewById(R.id.hargaText);
        cityEditText = findViewById(R.id.cityText);
        imgView = findViewById(R.id.addimgView);

        date = Calendar.getInstance().getTime();

        addFoodViewModel = ViewModelProviders.of(this).get(AddFoodViewModel.class);

        Button fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(nameEditText.getText().toString()) || TextUtils.isEmpty(phoneEditText.getText().toString()) || TextUtils.isEmpty(cityEditText.getText().toString()) || TextUtils.isEmpty(cityEditText.getText().toString()) || imgView.getDrawable() == null)
                    Toast.makeText(AddFoodActivity.this, "Data Belum Lengkap", Toast.LENGTH_SHORT).show();
                else {
                    addFoodViewModel.addFood(new FoodModel(
                            nameEditText.getText().toString(),
                            phoneEditText.getText().toString(),
                            hargaEditText.getText().toString(),
                            cityEditText.getText().toString(),
                            date,
                            byteArray
                    ));
                    Toast.makeText(AddFoodActivity.this, "Menu Ditambahkan", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        findViewById(R.id.getImageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                final int ACTIVITY_SELECT_IMAGE = 1234;
                startActivityForResult(i, ACTIVITY_SELECT_IMAGE);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case 1234:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();

                    bitmapImage = BitmapFactory.decodeFile(filePath);

                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmapImage, 640, 480, false);

                    imgView.setImageBitmap(resizedBitmap);

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    resizedBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byteArray = stream.toByteArray();

                }
        }
    }
}
