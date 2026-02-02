package com.example.inventorymanagmentsystem.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.inventorymanagmentsystem.models.Transaction;
import com.example.inventorymanagmentsystem.models.TransactionType;

import java.time.LocalDate;
import java.util.List;

@Dao
public interface TransactionDao {
    @Query("Select date from item_transaction where item_id=:id AND transaction_type = :type ORDER BY date DESC LIMIT 1")
    LocalDate getDateByItemId(TransactionType type, int id);
    @Insert
    void insertTransaction(Transaction transaction);

    @Query("Select * from item_transaction")
    List<Transaction> getAllTransaction();

    @Query("Select * from item_transaction where item_id= :id")
    List<Transaction> getAllTransactionByID(int id);

}
