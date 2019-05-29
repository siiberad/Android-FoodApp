package com.siiberad.foodapp.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.siiberad.foodapp.Database.FoodModel.FoodModel;
import com.siiberad.foodapp.Database.FoodModel.FoodModelDao;
import com.siiberad.foodapp.Database.UserModel.UserModel;
import com.siiberad.foodapp.Database.UserModel.UserModelDao;

@Database(entities = {FoodModel.class, UserModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "food_db")
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public abstract FoodModelDao foodModelDao();

    public abstract UserModelDao userModelDao();

}
