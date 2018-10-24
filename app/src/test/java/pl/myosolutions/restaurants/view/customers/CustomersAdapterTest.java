package pl.myosolutions.restaurants.view.customers;


import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.adapters.ViewBindingAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.squareup.assertj.android.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;


import pl.myosolutions.restaurants.entities.Customer;
import pl.myosolutions.restaurants.utils.SampleData;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE, constants = BuildConfig.class, sdk = 18)
public class CustomersAdapterTest {

    private static final String TAG = CustomersAdapterTest.class.getSimpleName();

    private CustomersAdapter adapter;
    private CustomersAdapter.OnCustomerClickListener listener;
    private List<Customer> customers;


    @Before
    public void setUp(){
        listener = customer -> Log.d(TAG, customer.toString());
        customers = SampleData.getCustomers();

        adapter = new CustomersAdapter(customers, listener);
    }

    @Test
    public void itemCount() {
        assertThat(adapter.getItemCount()).isEqualTo(SampleData.getCustomers().size());
    }

    @Test
    public void getItemAtPosition() {
        int lastItemIndex = customers.size() - 1;
        assertThat(adapter.getCustomerAtPosition(0)).isEqualTo(customers.get(0));
        assertThat(adapter.getCustomerAtPosition(lastItemIndex)).isEqualTo(customers.get(lastItemIndex));
    }


}
