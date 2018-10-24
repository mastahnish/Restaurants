package pl.myosolutions.restaurants.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import pl.myosolutions.restaurants.R;
import pl.myosolutions.restaurants.database.AppRepository;
import pl.myosolutions.restaurants.entities.Customer;
import pl.myosolutions.restaurants.utils.NetworkUtils;

public class CustomersViewModel extends AndroidViewModel {

    public LiveData<List<Customer>> mCustomers;
    public MutableLiveData<Integer> notification = new MutableLiveData<>();

    private AppRepository mRepository;
    private NetworkUtils networkUtils;

    public CustomersViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(application.getApplicationContext());
        networkUtils = NetworkUtils.getInstance(application.getApplicationContext());
        mCustomers = mRepository.mCustomers;
    }

    public void getCustomers(boolean isForceUpdate){
        boolean isOnline = networkUtils.isConnected();

        if(isForceUpdate && !isOnline){
            notification.setValue(R.string.no_internet);
        }

        mRepository.getCustomers(isOnline, isForceUpdate);
    }

    public LiveData<List<Customer>> processQuery(String newText) {
       return  mRepository.processQuery(newText);
    }
}
