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

import pl.myosolutions.restaurants.entities.Table;
import pl.myosolutions.restaurants.utils.LiveDataTestUtil;
import pl.myosolutions.restaurants.utils.SampleData;

@RunWith(AndroidJUnit4.class)
public class DatabaseTablesTest {
    public static final String TAG = "Junit";
    private AppDatabase mDb;
    private TableDao mDao;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context,
                AppDatabase.class).build();
        mDao = mDb.tableDao();
        Log.i(TAG, "createDb");
    }

    @After
    public void closeDb() {
        mDb.close();
        Log.i(TAG, "closeDb");
    }

    @Test
    public void createAndRetrieveTables() {
        mDao.insertAll(SampleData.getTables());
        int count = mDao.getCount();
        Log.i(TAG, "createAndRetrieveTables: count=" + count);
        assertEquals(SampleData.getTables().size(), count);
    }

    @Test
    public void compareTables() throws InterruptedException {
        mDao.insertAll(SampleData.getTables());
        Table original = SampleData.getTables().get(0);
        Table fromDb = LiveDataTestUtil.getValue(mDao.getAll()).get(0);

        assertEquals(original.toString(), fromDb.toString());
        assertEquals(original.isVacant(), fromDb.isVacant());
        assertEquals(original.getCustomerId(), fromDb.getCustomerId());
        assertEquals(original.getId(), fromDb.getId());
    }

    @Test
    public void testUpdateSingleTable() {
        mDao.insertAll(SampleData.getTables());

        mDao.updateTable(3, false, 3);
        Table original = SampleData.getTables().get(3);
        Table fromDb = mDao.getTableById(3);

        assertEquals(false, fromDb.isVacant());
        assertEquals(3, fromDb.getCustomerId());
        assertEquals(original.getId(), fromDb.getId());

        assertNotEquals(original.isVacant(), fromDb.isVacant());
        assertNotEquals(original.getCustomerId(), fromDb.getCustomerId());
    }

    @Test
    public void testEmptyCustomerId() {
        mDao.insertAll(SampleData.getTables());
        Table fromDb = mDao.getTableById(3);
        assertEquals(-1, fromDb.getCustomerId());
    }

}
