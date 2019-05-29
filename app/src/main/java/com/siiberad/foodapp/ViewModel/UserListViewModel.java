package com.siiberad.foodapp.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.siiberad.foodapp.Database.AppDatabase;
import com.siiberad.foodapp.Database.UserModel.UserModel;

import java.util.List;


public class UserListViewModel extends AndroidViewModel {

    private final LiveData<List<UserModel>> userList;

    private AppDatabase appDatabase;

    public UserListViewModel(Application application) {
        super(application);
        appDatabase = AppDatabase.getDatabase(this.getApplication());
        userList = appDatabase.userModelDao().getAllUsersItems();
    }


    public LiveData<List<UserModel>> getAllUsersItems() {
        return userList;
    }

    public void deleteItem(UserModel userModel) {
        new deleteAsyncTask(appDatabase).execute(userModel);
    }

    private static class deleteAsyncTask extends AsyncTask<UserModel, Void, Void> {

        private AppDatabase db;

        deleteAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }
        @Override
        protected Void doInBackground(final UserModel... params) {
            db.userModelDao().deleteUser(params[0]);
            return null;
        }
    }
}
