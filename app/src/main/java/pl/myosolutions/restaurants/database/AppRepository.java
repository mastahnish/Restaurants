package pl.myosolutions.restaurants.database;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import pl.myosolutions.restaurants.entities.Reservation;
import pl.myosolutions.restaurants.entities.Table;
import pl.myosolutions.restaurants.http.HttpServiceFactory;
import pl.myosolutions.restaurants.http.RestaurantAPI;
import pl.myosolutions.restaurants.entities.Customer;


import static android.content.ContentValues.TAG;
import static pl.myosolutions.restaurants.http.RestaurantAPI.*;

public class AppRepository {
    private static AppRepository instance;

    public LiveData<List<Customer>> mCustomers;
    public LiveData<List<Table>> mTables;
    private AppDatabase mDb;
    private Executor executor = Executors.newSingleThreadExecutor();


    public static AppRepository getInstance(Context context) {
        if (instance == null) {
            instance = new AppRepository(context);
        }

        return instance;
    }

    private AppRepository(Context ctx) {
        mDb = AppDatabase.getInstance(ctx);
        mCustomers = getAllCustomers();
        mTables = getAllTables();
    }

    public void getCustomers(boolean isOnline) {

        if (isOnline) {

            RestaurantService service = HttpServiceFactory.createRetrofitService(RestaurantService.class, RestaurantAPI.API_BASE_URL);

            service.getCustomers()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<Customer>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(List<Customer> customersResponse) {
                            insertAllCustomers(customersResponse);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, "onError: " + e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        } else {
            getAllCustomers();
        }
    }

    public void getTables(boolean isOnline) {
        if (isOnline && mDb.tableDao().getCount() == 0) {

            RestaurantService service = HttpServiceFactory.createRetrofitService(RestaurantService.class, RestaurantAPI.API_BASE_URL);

            service.getTables()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<Boolean>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(List<Boolean> booleans) {
                            List<Table> tables = new ArrayList<>();
                            for (int i = 0; i < booleans.size(); i++) {
                                tables.add(new Table(i + 1, booleans.get(i)));
                            }

                            insertTables(tables);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "onError: " + e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            getAllTables();
        }
    }

    private LiveData<List<Customer>> getAllCustomers() {
        return mDb.customerDao().getAll();
    }

    private LiveData<List<Table>> getAllTables() {
        return mDb.tableDao().getAll();
    }

    public LiveData<Customer> getCustomerById(int customerId) {
        return mDb.customerDao().getCustomerById(customerId);
    }

    private void insertAllCustomers(final List<Customer> customers) {
        executor.execute(() -> mDb.customerDao().insertAll(customers));
    }

    private void insertTables(List<Table> tables) {
        executor.execute(() -> mDb.tableDao().insertAll(tables));
    }

    public void resetReservations() {
        executor.execute(() -> {
            mDb.runInTransaction(() -> {
                List<Reservation> reservationsToReset = mDb.reservationDao().getReservationsToReset(false);

                if(reservationsToReset!=null){
                    for(Reservation reservation : reservationsToReset){
                        mDb.reservationDao().updateReservation(reservation.getCustomerId(), reservation.getTableId(), true);
                        mDb.tableDao().updateTable(reservation.getTableId(), true, -1);
                    }
                }
            });


        });
    }

    public void insertReservation(Reservation reservation) {
        executor.execute(() -> {
            mDb.runInTransaction(() -> {
                Reservation checkForReservation = mDb.reservationDao().getReservationByIdAndCustomerId(reservation.getTableId(), reservation.getCustomerId());

                if(checkForReservation!=null){
                    mDb.reservationDao().updateReservation(reservation.getCustomerId(), reservation.getTableId(), false);
                }else{
                    mDb.reservationDao().insert(reservation);
                }

            });
        });
    }

    public void resetReservation(int tableId, int customerId) {
        executor.execute(() -> mDb.reservationDao().updateReservation(customerId, tableId, true));
    }

    public void updateTable(int tableId, boolean isVacant, int customerId) {
        executor.execute(() -> mDb.tableDao().updateTable(tableId, isVacant, customerId));
    }


    public LiveData<List<Customer>> processQuery(String text) {
        return mDb.customerDao().getSearchResult("%" + text + "%");
    }
}
