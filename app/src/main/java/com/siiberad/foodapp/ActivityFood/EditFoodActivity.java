package com.siiberad.foodapp.ActivityFood;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.siiberad.foodapp.Database.AppDatabase;
import com.siiberad.foodapp.Database.FoodModel.FoodModel;
import com.siiberad.foodapp.Database.FoodModel.FoodModelDao;
import com.siiberad.foodapp.R;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Date;

public class EditFoodActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText hargaEditText;
    private EditText cityEditText;

    Button buttonImage, buttonEdit;

    Bitmap bitmapImage;
    byte[] byteArray;

    ImageView imgView;
    FoodModel foodModel;

    private FoodModelDao foodModelDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food);

        nameEditText = findViewById(R.id.editNameText);
        phoneEditText = findViewById(R.id.editPhoneText);
        hargaEditText = findViewById(R.id.editHargaText);
        cityEditText = findViewById(R.id.editCityText);
        imgView = findViewById(R.id.imgView);

        Date date = Calendar.getInstance().getTime();

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        foodModel = (FoodModel) bundle.getSerializable("edit");

        nameEditText.setText(foodModel.getName());
        phoneEditText.setText(foodModel.getPhone());
        hargaEditText.setText(foodModel.getHarga());
        cityEditText.setText(foodModel.getCity());

        byteArray = foodModel.getImage();
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        imgView.setImageBitmap(bmp);

        foodModelDao = AppDatabase.getDatabase(this).foodModelDao();

        buttonEdit = findViewById(R.id.editMenuButton);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(nameEditText.getText().toString()) || TextUtils.isEmpty(phoneEditText.getText().toString()) || TextUtils.isEmpty(cityEditText.getText().toString()) || TextUtils.isEmpty(cityEditText.getText().toString()) || imgView.getDrawable() == null)
                    Toast.makeText(EditFoodActivity.this, "Data Belum Lengkap", Toast.LENGTH_SHORT).show();
                else {
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids){
                            foodModel.setName(nameEditText.getText().toString());
                            foodModel.setPhone(phoneEditText.getText().toString());
                            foodModel.setHarga(hargaEditText.getText().toString());
                            foodModel.setCity(cityEditText.getText().toString());
                            foodModel.setImage(byteArray);
                            foodModelDao.updateFood(foodModel);
                            return null;
                        }
                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            Toast.makeText(EditFoodActivity.this, "Menu Diedit", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }.execute();
                }
            }
        });

        buttonImage = findViewById(R.id.editImage);
        buttonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI);
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
