package com.siiberad.foodapp.Database.UserModel;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.siiberad.foodapp.Database.FoodModel.FoodModel;

import java.io.Serializable;

//@Entity(foreignKeys = @ForeignKey(entity= FoodModel.class, parentColumns="id", childColumns="food_id"))
@Entity
public class UserModel implements Serializable {


    @PrimaryKey(autoGenerate = true)
    public int id;
    private String mNameUser;
    private String mPhoneUSer;

//    @ColumnInfo(index = true)
//    private int food_id;

    public UserModel(String mNameUser, String mPhoneUSer) {
        this.mNameUser = mNameUser;
        this.mPhoneUSer = mPhoneUSer;

    }

    public String getNameUser() {
        return mNameUser;
    }

    public String getPhoneUSer() {
        return mPhoneUSer;
    }

    public int getId() {
        return id;
    }

//    public int getFood_id() {
//        return food_id;
//    }
//
//    public void setFood_id(int food_id) {
//        this.food_id = food_id;
//    }
}
