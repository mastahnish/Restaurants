package pl.myosolutions.restaurants.view.customers;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pl.myosolutions.restaurants.R;
import pl.myosolutions.restaurants.databinding.CustomerListItemBinding;
import pl.myosolutions.restaurants.entities.Customer;

public class CustomersAdapter extends RecyclerView.Adapter {

    private List<Customer> customers;
    private OnCustomerClickListener onCustomerClickListener;

    public CustomersAdapter(List<Customer> data, OnCustomerClickListener listener) {
        this.customers = data;
        this.onCustomerClickListener = listener;
    }

    public void updateData(List<Customer> data){
        if(data!=null){
            customers = data;
            notifyDataSetChanged();
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CustomerListItemBinding binding = CustomerListItemBinding.inflate(inflater, parent, false);
        return new CustomersViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        CustomersViewHolder holder = (CustomersViewHolder) viewHolder;
        if (customers != null && customers.size() > 0) {
            holder.binding.setCustomer(customers.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    class CustomersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CustomerListItemBinding binding;

        public CustomersViewHolder(View itemView) {
            super(itemView);
            this.binding = DataBindingUtil.bind(itemView);
            this.binding.getRoot().setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if(customers!=null && customers.size() > 0) {
                onCustomerClickListener.onCustomerClicked(customers.get(getAdapterPosition()));
            }
        }
    }

    interface OnCustomerClickListener {
        void onCustomerClicked(Customer customer);
    }

}
