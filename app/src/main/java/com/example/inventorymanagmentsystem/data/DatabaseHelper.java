package com.example.inventorymanagmentsystem.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.inventorymanagmentsystem.data.dao.ItemDao;
import com.example.inventorymanagmentsystem.data.dao.TransactionDao;
import com.example.inventorymanagmentsystem.models.Item;
import com.example.inventorymanagmentsystem.models.ItemType;
import com.example.inventorymanagmentsystem.models.Transaction;
import com.example.inventorymanagmentsystem.utils.ConvertersUtil;

import java.util.concurrent.Executors;

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
                    .allowMainThreadQueries()
                    // When the DB is created for the first time, I want to insert some default data so the home screen is not empty
                    .addCallback(new RoomDatabase.Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            // I use a background thread to insert the initial data so I do not block the UI
                            Executors.newSingleThreadExecutor().execute(() -> {
                                ItemDao dao = instance.itemDao();
                                // These are my default items that will be visible when I open the app for the first time
                                dao.addItem(new Item("Carrots", ItemType.VEGETABLE, 15, "Default vegetable example"));
                                dao.addItem(new Item("Beef", ItemType.MEAT, 10, "Default meat example"));
                                dao.addItem(new Item("Salmon", ItemType.FISH, 8, "Default fish example"));
                            });
                        }
                    })
                    .build();
        }
        return instance;
    }

    public abstract ItemDao itemDao();

    public abstract TransactionDao transactionDao();
}
