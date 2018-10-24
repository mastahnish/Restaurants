package pl.myosolutions.restaurants.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import pl.myosolutions.restaurants.database.AppRepository;
import pl.myosolutions.restaurants.entities.Customer;
import pl.myosolutions.restaurants.utils.NetworkUtils;

public class CustomersViewModel extends AndroidViewModel {

    public LiveData<List<Customer>> mCustomers;
    private AppRepository mRepository;
    private Context context;

    public CustomersViewModel(@NonNull Application application) {
        super(application);

        this.context = application.getApplicationContext();
        mRepository = AppRepository.getInstance(context);
        mCustomers = mRepository.mCustomers;
    }

    public void getCustomers(boolean isForceUpdate){
        mRepository.getCustomers(NetworkUtils.isConnected(context), isForceUpdate);
    }

    public LiveData<List<Customer>> processQuery(String newText) {
       return  mRepository.processQuery(newText);
    }
}
