package jo.com.handy;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
@TypeConverters(DateConverter.class)
public interface HandyDao {

    @Update
    void updateHandy (Handy handy);

    @Query("select * from Handy")
    LiveData<List<Handy>> getHandies();

    @Query("select * from Handy where id = :id")
    LiveData<Handy> getHandyById(String id);


    @Insert(onConflict = REPLACE)
    void createNewHandy(Handy handy);

    @Delete
    void deleteHandy(Handy handy);

}
