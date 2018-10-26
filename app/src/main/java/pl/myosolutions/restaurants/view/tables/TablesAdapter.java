package pl.myosolutions.restaurants.view.tables;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import pl.myosolutions.restaurants.databinding.TableListItemBinding;
import pl.myosolutions.restaurants.entities.Table;
import pl.myosolutions.restaurants.utils.TableDiffUtilCallback;

public class TablesAdapter extends RecyclerView.Adapter {

    private List<Table> tables;
    private TablesAdapter.OnTableClickListener onTableClickListener;
    private int customerId;

    TablesAdapter(int customerId, List<Table> data, TablesAdapter.OnTableClickListener listener) {
        this.customerId = customerId;
        this.tables = data;
        this.onTableClickListener = listener;
    }

    void updateItems(List<Table> newTables) {
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new TableDiffUtilCallback(tables, newTables));
            diffResult.dispatchUpdatesTo(this);

            tables.clear();
            tables.addAll(newTables);
    }


    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        TableListItemBinding binding = TableListItemBinding.inflate(inflater, parent, false);
        return new TablesAdapter.TableViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TablesAdapter.TableViewHolder viewHolder = (TablesAdapter.TableViewHolder) holder;
        if (tables != null && tables.size() > 0) {
            viewHolder.binding.setTable(tables.get(position));
            viewHolder.binding.setCustomerId(customerId);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position, @NonNull List payloads) {
        super.onBindViewHolder(viewHolder, position, payloads);

        if (payloads.isEmpty()) {
            onBindViewHolder(viewHolder, position);
            return;
        }

        Bundle bundle = (Bundle) payloads.get(0);
        TablesAdapter.TableViewHolder holder = (TablesAdapter.TableViewHolder) viewHolder;

        Table table = tables.get(position);
        holder.binding.setCustomerId(customerId);

        int newCustomerId = bundle.getInt(TableDiffUtilCallback.CUSTOMER_ID_CHANGE_KEY);
        if (newCustomerId > -1) {
            table.setCustomerId(newCustomerId);
        }

        boolean newIsVacant = bundle.getBoolean(TableDiffUtilCallback.IS_VACANT_FLAG_CHANGED_KEY);
        if (newIsVacant) {
            table.setVacant(newIsVacant);
        }

        holder.binding.setTable(table);

    }


    Table getTableAtPosition(int position) {
        return tables.get(position);
    }


    @Override
    public int getItemCount() {
        return tables.size();
    }

    class TableViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TableListItemBinding binding;

        TableViewHolder(View itemView) {
            super(itemView);
            this.binding = DataBindingUtil.bind(itemView);
            if (this.binding != null) {
                this.binding.getRoot().setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View v) {
            onTableClickListener.onTableClicked(tables.get(getAdapterPosition()));
        }
    }

    interface OnTableClickListener {
        void onTableClicked(Table table);
    }
}
