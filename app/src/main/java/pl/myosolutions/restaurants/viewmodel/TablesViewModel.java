package pl.myosolutions.restaurants.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

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
    public LiveData<List<Reservation>> mReservations;
    public MutableLiveData<String> notification = new MutableLiveData<>();
    public LiveData<Customer> mCurrentCustomer;
    private AppRepository mRepository;
    private Context context;
    private int customerId;

    public TablesViewModel(@NonNull Application application) {
        super(application);

        this.context = application.getApplicationContext();
        mRepository = AppRepository.getInstance(context);
        mTables = mRepository.mTables;
        mReservations = mRepository.mReservations;
    }


    public void getTables() {
        Log.d(TAG, "getTables");
        mRepository.getTables(NetworkUtils.isConnected(context));
    }

    public void onTableClicked(Table table) {
        int tableId = table.getId();

        if (table.isVacant()) {
            mRepository.insertReservation(new Reservation(customerId, tableId));
            notification.setValue(context.getString(R.string.booking_table) + tableId);

        } else if (table.getCustomerId() == customerId) {
            mRepository.deleteReservation(tableId);
            notification.setValue(context.getString(R.string.reservation_cancelled));

        } else {
            notification.setValue(context.getString(R.string.table_not_vacant));
        }


    }

    public void setCustomerId(int id) {
        customerId = id;
        mCurrentCustomer = mRepository.getCustomerById(id);
    }

}
