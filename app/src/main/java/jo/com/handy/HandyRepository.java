package jo.com.handy;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

public class HandyRepository {
    private HandyDao handyDao;
    private LiveData<List<Handy>> handies;
    LiveData<Handy> handy;
    private  HandyDatabase handyDatabase;

    HandyRepository(Application application){
        handyDatabase = HandyDatabase.getHandyDatabaseInstance(application);
        handyDao = handyDatabase.handyDao();
        handies = handyDao.getHandies();
    }

    LiveData<List<Handy>> getHandies(){
        return handies;
    }

    LiveData<Handy> getItemById(String id){
        handy = handyDao.getHandyById(id);
        return handy;
    }

    public void createNewHandy(Handy handy) {
        new insertAsyncTask(handyDao).execute(handy);
    }

    private static class insertAsyncTask extends AsyncTask<Handy, Void, Void> {

        private HandyDao mAsyncTaskDao;

        insertAsyncTask(HandyDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Handy... params) {
            mAsyncTaskDao.createNewHandy(params[0]);
            return null;
        }
    }

    public void updateItem(Handy handy){
        new updateAsyncTask(handyDatabase)
                .execute(handy);
    }

    public static class updateAsyncTask extends AsyncTask<Handy, Void, Void> {
        private HandyDatabase database;
        updateAsyncTask(HandyDatabase handyDatabase){
            database = handyDatabase;
        }

        @Override
        protected Void doInBackground(final Handy... handies) {
            database.handyDao().updateHandy(handies[0]);
            return null;
        }

    }

    public void deleteItem(Handy handy){
        if (handy != null){
            new deleteAsyncTask(handyDatabase)
                    .execute(handy);
        }
    }

    public static class deleteAsyncTask extends AsyncTask<Handy, Void, Void> {
        private HandyDatabase database;
        deleteAsyncTask(HandyDatabase handyDatabase){
            database = handyDatabase;
        }

        @Override
        protected Void doInBackground(final Handy... handies) {
            database.handyDao().deleteHandy(handies[0]);
            return null;
        }

    }

}
