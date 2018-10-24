package pl.myosolutions.restaurants.utils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

import pl.myosolutions.restaurants.entities.Table;

public class DiffUtilCallback extends DiffUtil.Callback {

    public static final String CUSTOMER_ID_CHANGE_KEY = "CUSTOMER_ID_CHANGE";
    public static final String IS_VACANT_FLAG_CHANGED_KEY = "IS_VACANT_FLAG_CHANGED";

    private List<Table> oldList;
    private List<Table> newList;

    public DiffUtilCallback(List<Table> oldList, List<Table> newList) {
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

        Table oldTable = oldList.get(oldItemPosition);
        Table newTable = newList.get(newItemPosition);
        return (oldTable.getCustomerId() == (newTable.getCustomerId()) && oldTable.isVacant() == (newTable.isVacant()));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        Table oldTable = oldList.get(oldItemPosition);
        Table newTable = newList.get(newItemPosition);

        Bundle payload = new Bundle();
        if (oldTable.getCustomerId() != newTable.getCustomerId()) {
            payload.putInt(CUSTOMER_ID_CHANGE_KEY, newTable.getCustomerId());
        }

        if (oldTable.isVacant() != newTable.isVacant()) {
            payload.putBoolean(IS_VACANT_FLAG_CHANGED_KEY, newTable.isVacant());
        }

        if (payload.isEmpty()) {
            return null;
        } else {
            return payload;
        }
    }
}
