package com.example.inventorymanagmentsystem.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

/**
 * Model for a stock movement (entry or exit). I store these in the "item_transaction" table so we
 * can show last entry/exit dates per item and keep a simple history.
 */
@Entity(tableName = "item_transaction")
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "transaction_type")//
    private TransactionType type;
    @ColumnInfo(name = "date")
    private LocalDate date;

    @ColumnInfo(name = "item_id")
    private int item_id;

    /** I keep an empty constructor so Room can create this entity when reading from the DB. */
    public Transaction() {
    }

    /** I use this when I record a new transaction from the UI (id is auto-generated). */
    @Ignore
    public Transaction(LocalDate date, TransactionType type, int id_item) {
        this.date = date;
        this.type = type;
        this.item_id = id_item;
    }

    public Transaction(int id, int id_item, LocalDate date, TransactionType type) {
        this.id = id;
        this.item_id = id_item;
        this.date = date;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }
}
