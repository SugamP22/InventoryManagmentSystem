package com.example.inventorymanagmentsystem.data.repository;

import android.content.Context;

import com.example.inventorymanagmentsystem.data.DatabaseHelper;
import com.example.inventorymanagmentsystem.data.dao.TransactionDao;
import com.example.inventorymanagmentsystem.models.Item;
import com.example.inventorymanagmentsystem.models.Transaction;
import com.example.inventorymanagmentsystem.models.TransactionType;

import java.time.LocalDate;
import java.util.List;

/**
 * I use this class to access the transaction table via TransactionDao. I insert new transactions
 * when the user adds/removes stock and I read the latest entry/exit date per item for the detail screen.
 */
public class TransactionRepository {
    private final TransactionDao transactionDao;

    public TransactionRepository(Context context) {
        DatabaseHelper db = DatabaseHelper.getDB(context);
        this.transactionDao = db.transactionDao();
    }

    /** I use this to show the last entry or exit date for an item on the detail screen. */
    public LocalDate getDateByItemTypeAndID(TransactionType type, int itemId) {
        return transactionDao.getDateByItemId(type, itemId);
    }

    public void insertTransaction(Transaction transaction) {
        transactionDao.insertTransaction(transaction);
    }

    /** I seed sample transactions only when the transaction table is empty, so the detail screen has dates to show. */
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
