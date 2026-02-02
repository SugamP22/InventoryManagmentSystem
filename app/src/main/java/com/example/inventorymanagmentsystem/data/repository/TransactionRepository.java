package com.example.inventorymanagmentsystem.data.repository;

import android.content.Context;

import com.example.inventorymanagmentsystem.data.DatabaseHelper;
import com.example.inventorymanagmentsystem.data.dao.TransactionDao;
import com.example.inventorymanagmentsystem.models.Item;
import com.example.inventorymanagmentsystem.models.Transaction;
import com.example.inventorymanagmentsystem.models.TransactionType;

import java.time.LocalDate;
import java.util.List;

public class TransactionRepository {
    private final TransactionDao transactionDao;

    //class to guid our DB helper class towards or DAO to retrieve data
    public TransactionRepository(Context context) {
        DatabaseHelper db = DatabaseHelper.getDB(context);
        this.transactionDao = db.transactionDao();
    }

    public LocalDate getDateByItemTypeAndID(TransactionType type, int itemId) {
        return transactionDao.getDateByItemId(type, itemId);
    }

    public void insertTransaction(Transaction transaction) {
        transactionDao.insertTransaction(transaction);
    }

    /**
     * Seeds the transaction table with sample data linked to existing items.
     * Only runs when the transaction table is empty.
     */
    public void seedTransactionData(List<Item> items) {
        if (items == null || items.isEmpty()) return;
        if (!transactionDao.getAllTransaction().isEmpty()) return;

        LocalDate today = LocalDate.now();

        // Sample transactions across different items (vegetables, meat, fish)
        transactionDao.insertTransaction(new Transaction(today.minusDays(2), TransactionType.ENTRY, items.get(0).getId()));
        transactionDao.insertTransaction(new Transaction(today.minusDays(5), TransactionType.EXIT, items.get(0).getId()));
        transactionDao.insertTransaction(new Transaction(today.minusDays(1), TransactionType.ENTRY, items.get(8).getId()));
        transactionDao.insertTransaction(new Transaction(today.minusDays(7), TransactionType.EXIT, items.get(14).getId()));
        transactionDao.insertTransaction(new Transaction(today.minusDays(3), TransactionType.ENTRY, items.get(15).getId()));
        transactionDao.insertTransaction(new Transaction(today, TransactionType.EXIT, items.get(10).getId()));
        transactionDao.insertTransaction(new Transaction(today.minusDays(4), TransactionType.ENTRY, items.get(items.size() - 1).getId()));
    }

}
