package com.siiberad.foodapp.Database.FoodModel;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.siiberad.foodapp.Database.DateConverter;

import java.io.Serializable;
import java.util.Date;

@Entity
//@Entity(foreignKeys = @ForeignKey(entity= UserModel.class, parentColumns="id", childColumns="user_id"))
public class FoodModel implements Serializable {

    private boolean isSelected = false;

    @PrimaryKey(autoGenerate = true)
    public int id;
    private String mName;
    private String mPhone;
    private String mHarga;
    private String mCity;
    @TypeConverters(DateConverter.class)
    private Date borrowDate;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] mImage;

    public FoodModel(String mName, String mPhone, String mHarga, String mCity, Date borrowDate, byte[] mImage) {
        this.mName = mName;
        this.mPhone = mPhone;
        this.mHarga = mHarga;
        this.mCity = mCity;
        this.borrowDate = borrowDate;
        this.mImage = mImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String mCity) {
        this.mCity = mCity;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public byte[] getImage() {
        return mImage;
    }

    public void setImage(byte[] mImage) {
        this.mImage = mImage;
    }

    public String getHarga() {
        return mHarga;
    }

    public void setHarga(String mHarga) {
        this.mHarga = mHarga;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
    public boolean isSelected() {
        return isSelected;
    }

//    public int getUser_id() {
//        return user_id;
//    }
//
//    public void setUser_id(int user_id) {
//        this.user_id = user_id;
//    }
}
