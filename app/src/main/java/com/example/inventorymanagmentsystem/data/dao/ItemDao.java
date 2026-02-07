package com.example.inventorymanagmentsystem.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.inventorymanagmentsystem.models.Item;
import com.example.inventorymanagmentsystem.models.ItemType;

import java.util.List;

/**
 * DAO for the item table. I use it to load all items, get one by id, filter by type, get low-stock
 * items, add/update items and adjust quantity (add/remove).
 */
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

    @Query("Update Item set quantity= quantity + :amount where id=:item_id")
    int addQuantity(int amount, int item_id);

    @Query("Update Item set quantity= quantity - :amount where id=:item_id and quantity>=:amount")
    int removeQuantity(int amount, int item_id);

    @Update
    void updateItem(Item item);

}
