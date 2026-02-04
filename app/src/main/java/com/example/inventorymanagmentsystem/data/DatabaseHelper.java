package com.example.inventorymanagmentsystem.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.inventorymanagmentsystem.data.dao.ItemDao;
import com.example.inventorymanagmentsystem.data.dao.TransactionDao;
import com.example.inventorymanagmentsystem.models.Item;
import com.example.inventorymanagmentsystem.models.Transaction;
import com.example.inventorymanagmentsystem.utils.ConvertersUtil;

@Database(entities = {Item.class, Transaction.class}, exportSchema = false, version = 1)
@TypeConverters({ConvertersUtil.class})
public abstract class DatabaseHelper extends RoomDatabase {
    private static final String DB_NAME = "inventorymanagmentsystem";
    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getDB(Context context) {
        if (instance == null) {
            // Here I create the single instance of my Room database that I will use in the whole app
            instance = Room.databaseBuilder(context, DatabaseHelper.class, DB_NAME)
                    // If I change the schema and forget migrations, Room will just recreate the DB (OK for this simple app)
                    .fallbackToDestructiveMigration()
                    // I allow main thread queries because this project is small and I prefer simplicity over perfect threading
                    .allowMainThreadQueries().build();
        }
        return instance;
    }

    public abstract ItemDao itemDao();

    public abstract TransactionDao transactionDao();
}
