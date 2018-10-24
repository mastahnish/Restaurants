package pl.myosolutions.restaurants.view.tables;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import pl.myosolutions.restaurants.databinding.TableListItemBinding;
import pl.myosolutions.restaurants.entities.Table;

public class TablesAdapter extends RecyclerView.Adapter {

    private List<Table> tables;
    private TablesAdapter.OnTableClickListener onTableClickListener;
    private int customerId;

    public TablesAdapter(int customerId, List<Table> data, TablesAdapter.OnTableClickListener listener) {
        this.customerId = customerId;
        this.tables = data;
        this.onTableClickListener = listener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        TableListItemBinding binding = TableListItemBinding.inflate(inflater, parent, false);
        return new TablesAdapter.TableViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        TablesAdapter.TableViewHolder holder = (TablesAdapter.TableViewHolder) viewHolder;
        if (tables != null && tables.size() > 0) {
            holder.binding.setTable(tables.get(position));
            holder.binding.setCustomerId(customerId);
        }
    }

    @Override
    public int getItemCount() {
        return tables.size();
    }

    class TableViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TableListItemBinding binding;

        public TableViewHolder(View itemView) {
            super(itemView);
            this.binding = DataBindingUtil.bind(itemView);
            this.binding.getRoot().setOnClickListener(this);
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
