package pl.myosolutions.restaurants.view.tables;


import android.util.Log;

import com.squareup.assertj.android.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import pl.myosolutions.restaurants.entities.Table;
import pl.myosolutions.restaurants.utils.SampleData;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE, constants = BuildConfig.class, sdk = 18)
public class TablesAdapterTest {

    private static final String TAG = TablesAdapterTest.class.getSimpleName();

    private TablesAdapter adapter;
    private TablesAdapter.OnTableClickListener listener;
    private List<Table> tables;


    @Before
    public void setUp(){
        listener = customer -> Log.d(TAG, customer.toString());
        tables = SampleData.getTables();

        adapter = new TablesAdapter(1, tables, listener);
    }

    @Test
    public void itemCount() {
        assertThat(adapter.getItemCount()).isEqualTo(SampleData.getTables().size());
    }

    @Test
    public void getItemAtPosition() {
        int lastItemIndex = tables.size() - 1;
        assertThat(adapter.getTableAtPosition(0)).isEqualTo(tables.get(0));
        assertThat(adapter.getTableAtPosition(lastItemIndex)).isEqualTo(tables.get(lastItemIndex));
    }


}
