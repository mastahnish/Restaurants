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
import pl.myosolutions.restaurants.entities.Reservation;
import pl.myosolutions.restaurants.entities.Table;
import pl.myosolutions.restaurants.utils.NetworkUtils;

public class TablesViewModel extends AndroidViewModel {

    private static final String TAG = TablesViewModel.class.getSimpleName();
    public LiveData<List<Table>> mTables;
    public MutableLiveData<Integer> notification = new MutableLiveData<>();
    public LiveData<Customer> mCurrentCustomer;
    private AppRepository mRepository;
    private NetworkUtils networkUtils;
    private int customerId;

    public TablesViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(application.getApplicationContext());
        networkUtils = NetworkUtils.getInstance(application.getApplicationContext());
        mTables = mRepository.mTables;
    }


    public void getTables() {
        mRepository.getTables(networkUtils.isConnected());
    }

    public void onTableClicked(Table table) {
        int tableId = table.getId();

        if (table.isVacant()) {
            mRepository.insertReservation(new Reservation(customerId, tableId, false));
            mRepository.updateTable(tableId, false, customerId);
            notification.setValue(R.string.reservation_success);

        } else if (table.getCustomerId() == customerId) {
            mRepository.resetReservation(tableId, customerId);
            mRepository.updateTable(tableId, true, -1);
            notification.setValue(R.string.reservation_cancelled);

        } else {
            notification.setValue(R.string.table_not_vacant);
        }


    }

    public void setCustomerId(int id) {
        customerId = id;
        mCurrentCustomer = mRepository.getCustomerById(id);
    }
}
