package jo.com.handy;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.List;

public class HandyViewModel extends AndroidViewModel {
    private HandyRepository handyRepository;
    private LiveData<List<Handy>> handies;
    private  LiveData<Handy> handy;
    private String id;


    public HandyViewModel(@NonNull Application application) {
        super(application);
        handyRepository = new HandyRepository(application);
        handies = handyRepository.getHandies();

    }

    LiveData<List<Handy>> getHandies(){
        return handies;
    }

    LiveData<Handy> getItemById(String id){
        handy = handyRepository.getItemById(id);
        return handy;
    }

    public void update(Handy handy){
        handyRepository.updateItem(handy);
    }

    public void insert(Handy handy){
        handyRepository.createNewHandy(handy);
    }

    public void deleteHandy(Handy handy){
        handyRepository.deleteItem(handy);
    }
}
