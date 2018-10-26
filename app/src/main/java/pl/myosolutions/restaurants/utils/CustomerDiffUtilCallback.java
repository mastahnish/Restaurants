package pl.myosolutions.restaurants.utils;

import android.support.v7.util.DiffUtil;

import java.util.List;

import pl.myosolutions.restaurants.entities.Customer;

public class CustomerDiffUtilCallback extends DiffUtil.Callback {

    private List<Customer> oldList;
    private List<Customer> newList;

    public CustomerDiffUtilCallback(List<Customer> oldList, List<Customer> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

        Customer oldCustomer = oldList.get(oldItemPosition);
        Customer newCustomer = newList.get(newItemPosition);
        return (oldCustomer.getCustomerFirstName() == newCustomer.getCustomerFirstName()
                && oldCustomer.getCustomerLastName() == newCustomer.getCustomerLastName()
                && oldCustomer.getId() == newCustomer.getId() );
    }

}
