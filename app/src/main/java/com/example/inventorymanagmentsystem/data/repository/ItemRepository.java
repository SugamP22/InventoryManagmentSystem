package com.example.inventorymanagmentsystem.data.repository;

import android.content.Context;

import com.example.inventorymanagmentsystem.data.DatabaseHelper;
import com.example.inventorymanagmentsystem.data.dao.ItemDao;
import com.example.inventorymanagmentsystem.models.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemRepository {
    private final ItemDao itemDao;

    public ItemRepository(Context context) {
        DatabaseHelper db = DatabaseHelper.getDB(context);
        this.itemDao = db.itemDao();
    }

    public List<Item> getDefaultItems() {
        return itemDao.getAllItems();
    }
}
