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

import pl.myosolutions.restaurants.entities.Customer;
import pl.myosolutions.restaurants.utils.LiveDataTestUtil;
import pl.myosolutions.restaurants.utils.SampleData;

@RunWith(AndroidJUnit4.class)
public class DatabaseCustomersTest {
    public static final String TAG = "Junit";
    private AppDatabase mDb;
    private CustomerDao mDao;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context,
                AppDatabase.class).build();
        mDao = mDb.customerDao();
        Log.i(TAG, "createDb");
    }

    @After
    public void closeDb() {
        mDb.close();
        Log.i(TAG, "closeDb");
    }

    @Test
    public void createAndRetrieveCustomers() {
        mDao.insertAll(SampleData.getCustomers());
        int count = mDao.getCount();
        Log.i(TAG, "createAndRetrieveCustomers: count=" + count);
        assertEquals(SampleData.getCustomers().size(), count);
    }

    @Test
    public void compareStrings() throws InterruptedException {
        mDao.insertAll(SampleData.getCustomers());
        Customer original = SampleData.getCustomers().get(0);
        Customer fromDb = LiveDataTestUtil.getValue(mDao.getCustomerById(0));
        assertEquals(original.getCustomerFirstName(), fromDb.getCustomerFirstName());
        assertEquals(0, fromDb.getId());
    }
}
