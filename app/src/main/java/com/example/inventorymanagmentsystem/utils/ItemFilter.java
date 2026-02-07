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

    /** I filter by item name: keep only items whose name contains the query (case-insensitive). Empty or null query returns the list unchanged. */
    public static ArrayList<Item> filterByName(List<Item> items, String query) {
        ArrayList<Item> result = new ArrayList<>();
        if (query == null) {
            result.addAll(items);
            return result;
        }
        String q = query.trim().toLowerCase();
        if (q.isEmpty()) {
            result.addAll(items);
            return result;
        }
        for (Item item : items) {
            String name = item.getName();
            if (name != null && name.toLowerCase().contains(q)) {
                result.add(item);
            }
        }
        return result;
    }
}
