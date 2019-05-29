package com.siiberad.foodapp.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;

import com.siiberad.foodapp.Database.AppDatabase;
import com.siiberad.foodapp.Database.FoodModel.FoodModel;


public class AddFoodViewModel extends AndroidViewModel {

    private AppDatabase appDatabase;

    public AddFoodViewModel(Application application) {
        super(application);
        appDatabase = AppDatabase.getDatabase(this.getApplication());
    }

    public void addFood(final FoodModel foodModel) {
        new addAsyncTask(appDatabase).execute(foodModel);
    }

    private static class addAsyncTask extends AsyncTask<FoodModel, Void, Void> {

        private AppDatabase db;

        addAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final FoodModel... params) {
            db.foodModelDao().addFood(params[0]);
            return null;
        }
    }
}
