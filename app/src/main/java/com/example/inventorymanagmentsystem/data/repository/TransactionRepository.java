package com.example.inventorymanagmentsystem.data.repository;

import android.content.Context;

import com.example.inventorymanagmentsystem.data.DatabaseHelper;
import com.example.inventorymanagmentsystem.data.dao.TransactionDao;
import com.example.inventorymanagmentsystem.models.Transaction;
import com.example.inventorymanagmentsystem.models.TransactionType;

import java.time.LocalDate;

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


}
