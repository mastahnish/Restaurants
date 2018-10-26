package pl.myosolutions.restaurants.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "reservations")
public class Reservation {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int customerId;
    private int tableId;
    private boolean isCancelled;

    public Reservation(int id, int customerId, int tableId, boolean isCancelled) {
        this.id = id;
        this.customerId = customerId;
        this.tableId = tableId;
        this.isCancelled = isCancelled;
    }


    @Ignore
    public Reservation(int customerId, int tableId, boolean isCancelled) {
        this.customerId = customerId;
        this.tableId = tableId;
        this.isCancelled = isCancelled;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", tableId=" + tableId +
                ", isCancelled=" + isCancelled +
                '}';
    }
}
