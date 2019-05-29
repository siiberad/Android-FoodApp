package com.siiberad.foodapp.Database.FoodModel;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import com.siiberad.foodapp.Database.DateConverter;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
@TypeConverters(DateConverter.class)
public interface FoodModelDao {

    @Query("select * from FoodModel ORDER BY id DESC")
    LiveData<List<FoodModel>> getAllFood();

    @Query("select * from FoodModel where id = :id")
    FoodModel GetMenuById (int id);

    @Insert(onConflict = REPLACE)
    void addFood(FoodModel foodModel);

    @Delete
    void deleteFood(FoodModel foodModel);

    @Update
    void updateFood(FoodModel foodModel);

}
