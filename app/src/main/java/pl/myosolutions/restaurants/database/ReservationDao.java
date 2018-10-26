package pl.myosolutions.restaurants.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import pl.myosolutions.restaurants.entities.Reservation;

@Dao
public interface ReservationDao {

    @Query("SELECT * FROM reservations")
    LiveData<List<Reservation>> getAll();

    @Query("SELECT * FROM reservations WHERE tableId =:tableId AND customerId =:customerId")
    Reservation getReservationByIdAndCustomerId(int tableId, int customerId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Reservation reservation);

    @Query("DELETE FROM reservations WHERE tableId =:tableId")
    int deleteReservation(int tableId);

    @Query("DELETE FROM reservations")
    int deleteAll();

    @Query("UPDATE reservations SET isCancelled =:isCancelled")
    int updateAllReservations(boolean isCancelled);

    @Query("UPDATE reservations SET isCancelled =:isCancelled WHERE tableId =:tableId AND customerId =:customerId")
    int updateReservation(int customerId, int tableId, boolean isCancelled);

    @Query("SELECT * FROM reservations WHERE isCancelled=:isCancelled")
    List<Reservation> getReservationsToReset(boolean isCancelled);

    @Query("SELECT COUNT(*) FROM reservations")
    int getCount();


}