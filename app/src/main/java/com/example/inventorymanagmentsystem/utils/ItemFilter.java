package com.example.inventorymanagmentsystem.utils;

import com.example.inventorymanagmentsystem.models.Item;
import com.example.inventorymanagmentsystem.models.Type;

import java.util.ArrayList;
import java.util.List;

public class ItemFilter {

    /**
     * Filters items by spinner position. 0 = all, 1 = meat, 2 = fish, 3 = vegetable.
     */
    public static ArrayList<Item> filter(List<Item> items, int spinnerPosition) {
        ArrayList<Item> filteredList = new ArrayList<>();
        if (spinnerPosition == 0) {
            filteredList.addAll(items);
        } else {
            for (Item item : items) {
                if (spinnerPosition == 1 && item.getType() == Type.MEAT) {
                    filteredList.add(item);
                } else if (spinnerPosition == 2 && item.getType() == Type.FISH) {
                    filteredList.add(item);
                } else if (spinnerPosition == 3 && item.getType() == Type.VEGETABLE) {
                    filteredList.add(item);
                }
            }
        }
        return filteredList;
    }
}
