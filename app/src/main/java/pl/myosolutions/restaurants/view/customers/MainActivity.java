package pl.myosolutions.restaurants.view.customers;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import pl.myosolutions.restaurants.R;
import pl.myosolutions.restaurants.databinding.ActivityMainBinding;
import pl.myosolutions.restaurants.entities.Customer;
import pl.myosolutions.restaurants.view.tables.TableActivity;
import pl.myosolutions.restaurants.viewmodel.CustomersViewModel;

import static pl.myosolutions.restaurants.view.tables.TableActivity.CUSTOMER_ID;

public class MainActivity extends AppCompatActivity implements CustomersAdapter.OnCustomerClickListener {

    private ActivityMainBinding binding;
    private CustomersAdapter adapter;
    private List<Customer> customersData = new ArrayList<>();
    private CustomersViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initViews();
        initRecyclerView();
        initViewModel();
        viewModel.getCustomers();
    }

    private void initViews() {
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.choose_customer);
        binding.swipeRefresh.setOnRefreshListener(() -> viewModel.getCustomers());

    }

    private void initRecyclerView() {
        binding.customersList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.customersList.setLayoutManager(layoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(
                binding.customersList.getContext(), layoutManager.getOrientation());
        binding.customersList.addItemDecoration(divider);
    }


    private void initViewModel() {

        final Observer<List<Customer>> customersObserver =
                customerEntities -> {
                    if(binding.swipeRefresh.isRefreshing()) binding.swipeRefresh.setRefreshing(false);

                    if (adapter == null) {
                        adapter = new CustomersAdapter(customersData,
                                MainActivity.this);
                        binding.customersList.setAdapter(adapter);
                    } else {
                        adapter.updateData(customerEntities);
                    }

                };


        final Observer<Integer> notificationObserver =
                notification -> {
                    if(notification!=null){
                        String name = getResources().getResourceName(notification);
                        if (name == null || !name.startsWith(getApplicationContext().getPackageName() + ":id/")) {
                            Snackbar.make(binding.getRoot(), getString(notification), Snackbar.LENGTH_SHORT).show();
                        }
                    }


                    if(binding.swipeRefresh.isRefreshing()) binding.swipeRefresh.setRefreshing(false);
                };

        viewModel = ViewModelProviders.of(this)
                .get(CustomersViewModel.class);
        viewModel.mCustomers.observe(this, customersObserver);
        viewModel.notification.observe(this, notificationObserver);

    }


    @Override
    public void onCustomerClicked(Customer customer) {
        Intent i = new Intent(this, TableActivity.class);
        i.putExtra(CUSTOMER_ID, customer.getId());
        startActivity(i);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        final SearchView searchView = (SearchView)  menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.processQuery(query).observe(MainActivity.this, customers -> adapter.updateData(customers));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                viewModel.processQuery(newText).observe(MainActivity.this, customers -> adapter.updateData(customers));
                return true;
            }
        });

        return true;
    }

}
