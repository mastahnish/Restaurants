package pl.myosolutions.restaurants.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;


import java.util.List;

import pl.myosolutions.restaurants.entities.Table;

@Dao
public interface TableDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Table> table);

    @Query("SELECT * FROM tables")
    LiveData<List<Table>> getAll();

    @Query("SELECT * FROM tables WHERE id =:tableId")
    Table getTableById(int tableId);

    @Query("SELECT COUNT(*) FROM tables")
    int getCount();

    @Query("UPDATE tables SET isVacant = :isVacant, customerId = :customerId WHERE id = :tableId")
    void updateTable(int tableId, boolean isVacant, int customerId);

    @Query("DELETE FROM tables")
    int deleteAll();

}
