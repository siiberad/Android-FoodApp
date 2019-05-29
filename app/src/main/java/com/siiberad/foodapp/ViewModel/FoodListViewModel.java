package com.siiberad.foodapp.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.siiberad.foodapp.Database.AppDatabase;
import com.siiberad.foodapp.Database.FoodModel.FoodModel;

import java.util.List;


public class FoodListViewModel extends AndroidViewModel {

    private final LiveData<List<FoodModel>> foodList;

    private AppDatabase appDatabase;

    public FoodListViewModel(Application application) {
        super(application);
        appDatabase = AppDatabase.getDatabase(this.getApplication());
        foodList = appDatabase.foodModelDao().getAllFood();
    }


    public LiveData<List<FoodModel>> getFoodList() {
        return foodList;
    }

    public FoodModel getFoodById(int id){
        return appDatabase.foodModelDao()
                .GetMenuById(id);
    }

    public void deleteItem(FoodModel foodModel) {
        new deleteAsyncTask(appDatabase).execute(foodModel);
    }

    private static class deleteAsyncTask extends AsyncTask<FoodModel, Void, Void> {

        private AppDatabase db;

        deleteAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }
        @Override
        protected Void doInBackground(final FoodModel... params) {
            db.foodModelDao().deleteFood(params[0]);
            return null;
        }
    }
}
