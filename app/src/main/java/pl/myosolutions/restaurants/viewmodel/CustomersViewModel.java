package pl.myosolutions.restaurants.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import java.util.List;

import pl.myosolutions.restaurants.R;
import pl.myosolutions.restaurants.database.AppRepository;
import pl.myosolutions.restaurants.entities.Customer;
import pl.myosolutions.restaurants.utils.NetworkUtils;

public class CustomersViewModel extends AndroidViewModel {

    public LiveData<List<Customer>> mCustomers;
    private AppRepository mRepository;
    public MutableLiveData<String> notification = new MutableLiveData<>();
    private Context context;

    public CustomersViewModel(@NonNull Application application) {
        super(application);

        this.context = application.getApplicationContext();
        mRepository = AppRepository.getInstance(context);
        mCustomers = mRepository.mCustomers;
    }

    public void getCustomers(boolean isForceUpdate){
        boolean isOnline = NetworkUtils.isConnected(context);

        if(isForceUpdate && !isOnline){
            notification.setValue(context.getString(R.string.no_internet));
        }

        mRepository.getCustomers(isOnline, isForceUpdate);
    }

    public LiveData<List<Customer>> processQuery(String newText) {
       return  mRepository.processQuery(newText);
    }
}
