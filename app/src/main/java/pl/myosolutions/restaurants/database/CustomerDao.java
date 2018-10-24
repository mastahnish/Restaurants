package pl.myosolutions.restaurants.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import pl.myosolutions.restaurants.entities.Customer;

@Dao
public interface CustomerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Customer> customers);

    @Query("SELECT * FROM customers WHERE id = :id")
    LiveData<Customer> getCustomerById(int id);

    @Query("SELECT * FROM customers")
    LiveData<List<Customer>> getAll();

    @Query("SELECT COUNT(*) FROM customers")
    int getCount();

    @Query("SELECT * FROM customers WHERE customerLastName LIKE :query OR customerFirstName LIKE :query ")
    LiveData<List<Customer>> getSearchResult(String query);

}
