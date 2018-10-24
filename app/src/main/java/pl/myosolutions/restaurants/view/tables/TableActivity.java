package pl.myosolutions.restaurants.view.tables;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pl.myosolutions.restaurants.R;
import pl.myosolutions.restaurants.databinding.ActivityTableBinding;
import pl.myosolutions.restaurants.entities.Customer;
import pl.myosolutions.restaurants.entities.Reservation;
import pl.myosolutions.restaurants.entities.Table;
import pl.myosolutions.restaurants.view.customers.CustomersAdapter;
import pl.myosolutions.restaurants.view.customers.MainActivity;
import pl.myosolutions.restaurants.viewmodel.CustomersViewModel;
import pl.myosolutions.restaurants.viewmodel.TablesViewModel;

public class TableActivity extends AppCompatActivity implements TablesAdapter.OnTableClickListener {

    public static final String CUSTOMER_ID = "CUSTOMER_ID";
    private static final String TAG = TableActivity.class.getSimpleName();

    private ActivityTableBinding binding;
    private TablesViewModel viewModel;
    private TablesAdapter adapter;
    private List<Table> tablesData = new ArrayList<>();
    private int customerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_table);
        setSupportActionBar(binding.tooblar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getExtras();
        initRecyclerView();
        initViewModel();
        updateToolbar();
        viewModel.getTables();
    }

    private void updateToolbar() {

        viewModel.mCurrentCustomer.observe(this, customer ->
                binding.tooblar.setTitle(String.format(getString(R.string.choose_table),customer.getCustomerFirstName(), customer.getCustomerLastName())));
    }

    private void initViewModel() {

        final Observer<List<Reservation>> reservationsObserver =
                reservations -> {

                };

        final Observer<List<Table>> tableObserver =
                tableEntities -> {
                    Log.d(TAG, "TEST_NOW tableObserver: " + tableEntities.toString());
                    tablesData.clear();
                    tablesData.addAll(tableEntities);

                    if (adapter == null) {
                        adapter = new TablesAdapter(customerId, tablesData,
                                TableActivity.this);
                        binding.tableList.setAdapter(adapter);
                    } else {
                        adapter.notifyDataSetChanged();
                    }

                };

        final Observer<String> notificationObserver =
                notification -> {
                    if (!TextUtils.isEmpty(notification)) {
                        Snackbar.make(binding.getRoot(), notification, Snackbar.LENGTH_SHORT).show();
                    }
                };



        viewModel = ViewModelProviders.of(this)
                .get(TablesViewModel.class);

        viewModel.mTables.observe(this, tableObserver);
        viewModel.notification.observe(this, notificationObserver);
        viewModel.mReservations.observe(this, reservationsObserver);

        viewModel.setCustomerId(customerId);
    }

    private void getExtras() {
        Intent i = getIntent();

        if (i != null) {
            Bundle extras = i.getExtras();
            if (extras != null) {
                customerId = (extras.getInt(CUSTOMER_ID));
            }
        }
    }

    private void initRecyclerView() {
        binding.tableList.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        binding.tableList.setLayoutManager(layoutManager);
        ((SimpleItemAnimator) binding.tableList.getItemAnimator()).setSupportsChangeAnimations(false);

        DividerItemDecoration divider = new DividerItemDecoration(
                binding.tableList.getContext(), layoutManager.getOrientation());
        binding.tableList.addItemDecoration(divider);
    }

    @Override
    public void onTableClicked(Table table) {
        viewModel.onTableClicked(table);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
