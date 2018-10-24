package pl.myosolutions.restaurants.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "tables")
public class Table {

    @PrimaryKey()
    private int id;
    private boolean isVacant;
    private int customerId = -1;

    public Table(int id, boolean isVacant, int customerId) {
        this.id = id;
        this.isVacant = isVacant;
        this.customerId = customerId;
    }

    @Ignore
    public Table(int id, boolean isVacant) {
        this.id = id;
        this.isVacant = isVacant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isVacant() {
        return isVacant;
    }

    public void setVacant(boolean vacant) {
        isVacant = vacant;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Table{" +
                "id=" + id +
                ", isVacant=" + isVacant +
                ", customerId=" + customerId +
                '}';
    }
}
