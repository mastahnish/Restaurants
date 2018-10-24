package pl.myosolutions.restaurants.database;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import pl.myosolutions.restaurants.entities.Reservation;
import pl.myosolutions.restaurants.utils.LiveDataTestUtil;
import pl.myosolutions.restaurants.utils.SampleData;

@RunWith(AndroidJUnit4.class)
public class DatabaseReservationsTest {
    public static final String TAG = "Junit";
    private AppDatabase mDb;
    private ReservationDao mDao;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context,
                AppDatabase.class).build();
        mDao = mDb.reservationDao();
        Log.i(TAG, "createDb");
    }

    @After
    public void closeDb() {
        mDb.close();
        Log.i(TAG, "closeDb");
    }

    @Test
    public void createAndRetrieveReservations() throws InterruptedException {
        mDao.insert(new Reservation(3, 1));
        mDao.insert(new Reservation(2, 2));
        mDao.insert(new Reservation(1, 3));
        mDao.deleteReservation(2);

        int count = mDao.getCount();
        int allCount = LiveDataTestUtil.getValue(mDao.getAll()).size();
        Log.i(TAG, "createAndRetrieveReservations: count=" + count);
        assertEquals(allCount, count);
    }

    @Test
    public void compareFields() throws InterruptedException {
        Reservation newReservation = new Reservation(1, 3, 1, false);
        mDao.insert(newReservation);
        Reservation fromDb = LiveDataTestUtil.getValue(mDao.getAll()).get(0);

        assertEquals(newReservation.isCancelled(), fromDb.isCancelled());
        assertEquals(newReservation.getCustomerId(), fromDb.getCustomerId());
        assertEquals(newReservation.getTableId(), fromDb.getTableId());
        assertEquals(newReservation.getId(), fromDb.getId());
    }
}
