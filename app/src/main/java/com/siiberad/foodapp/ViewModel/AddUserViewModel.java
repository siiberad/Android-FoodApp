package com.siiberad.foodapp.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;

import com.siiberad.foodapp.Database.AppDatabase;
import com.siiberad.foodapp.Database.UserModel.UserModel;


public class AddUserViewModel extends AndroidViewModel {

    private AppDatabase appDatabase;

    public AddUserViewModel(Application application) {
        super(application);
        appDatabase = AppDatabase.getDatabase(this.getApplication());
    }

    public void addUser(final UserModel userModel) {
        new addAsyncTask(appDatabase).execute(userModel);
    }

    private static class addAsyncTask extends AsyncTask<UserModel, Void, Void> {

        private AppDatabase db;

        addAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final UserModel... params) {
            db.userModelDao().addUser(params[0]);
            return null;
        }

    }
}
