package com.siiberad.foodapp.ActivityUser;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.siiberad.foodapp.R;
import com.siiberad.foodapp.ViewModel.AddUserViewModel;
import com.siiberad.foodapp.Database.UserModel.UserModel;

public class AddUserActivity extends AppCompatActivity {

    private EditText phoneEditText;
    private EditText nameEditText;
    private AddUserViewModel addUserViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        nameEditText = findViewById(R.id.nameTextUser);
        phoneEditText = findViewById(R.id.phoneTextUser);

        addUserViewModel = ViewModelProviders.of(this).get(AddUserViewModel.class);

        Button fab = findViewById(R.id.fabUser);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(nameEditText.getText().toString()) || TextUtils.isEmpty(phoneEditText.getText().toString()))
                    Toast.makeText(AddUserActivity.this, "Data Belum Lengkap", Toast.LENGTH_SHORT).show();
                else {
                    addUserViewModel.addUser(new UserModel(
                            nameEditText.getText().toString(),
                            phoneEditText.getText().toString()
                    ));
                    Toast.makeText(AddUserActivity.this, "User Ditambahkan", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
