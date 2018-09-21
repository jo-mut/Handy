package jo.com.handy;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.Date;

@Database(entities = {Handy.class}, version = 3)
public abstract class HandyDatabase extends RoomDatabase{
    public abstract HandyDao handyDao();
    public static HandyDatabase HANDY_DATABASE_INSTANCE;

    public static HandyDatabase getHandyDatabaseInstance(Context context){
        if (HANDY_DATABASE_INSTANCE == null){
            synchronized (HandyDatabase.class){
                if (HANDY_DATABASE_INSTANCE == null){
                    HANDY_DATABASE_INSTANCE = Room.databaseBuilder(context.getApplicationContext(), HandyDatabase.class, "handy_notes_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return HANDY_DATABASE_INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){
                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(HANDY_DATABASE_INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final HandyDao handyDao;

        PopulateDbAsync(HandyDatabase handyDatabase) {
            handyDao = handyDatabase.handyDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {

            return null;
        }
    }
}
