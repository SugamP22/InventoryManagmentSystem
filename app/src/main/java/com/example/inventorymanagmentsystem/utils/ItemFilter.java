package com.example.inventorymanagmentsystem.utils;

import com.example.inventorymanagmentsystem.models.Item;
import com.example.inventorymanagmentsystem.models.ItemType;

import java.util.ArrayList;
import java.util.List;

/**
 * I use this to filter the main list by category. Spinner position 0 = all, 1 = meat, 2 = fish, 3 = vegetable
 * (same order as spinner_main in strings.xml).
 */
public class ItemFilter {

    public static ArrayList<Item> filter(List<Item> items, int spinnerPosition) {
        ArrayList<Item> filteredList = new ArrayList<>();
        if (spinnerPosition == 0) {
            filteredList.addAll(items);
        } else {
            for (Item item : items) {
                if (spinnerPosition == 1 && item.getType() == ItemType.MEAT) {
                    filteredList.add(item);
                } else if (spinnerPosition == 2 && item.getType() == ItemType.FISH) {
                    filteredList.add(item);
                } else if (spinnerPosition == 3 && item.getType() == ItemType.VEGETABLE) {
                    filteredList.add(item);
                }
            }
        }
        return filteredList;
    }
}
