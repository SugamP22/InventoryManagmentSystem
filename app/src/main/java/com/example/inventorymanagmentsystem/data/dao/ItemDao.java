package com.example.inventorymanagmentsystem.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.inventorymanagmentsystem.models.Item;
import com.example.inventorymanagmentsystem.models.ItemType;

import java.util.List;


@Dao
public interface ItemDao {
    @Query("Select * from item")
    List<Item> getAllItems();

    @Query("Select * from item where id=:num")
    Item getItem(int num);

    @Query("Select * from item where item_type= :type")
    List<Item> getSpecificItems(ItemType type);

    @Query("Select * from item where quantity<:num")
    List<Item> notices(int num);

    @Insert
    void addItem(Item item);
    @Insert
    long returnID(Item item);

    @Update
    void updateItem(Item item);

}
