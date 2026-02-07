package com.example.inventorymanagmentsystem.utils;

import com.example.inventorymanagmentsystem.R;
import com.example.inventorymanagmentsystem.models.ItemType;

/**
 * I use this to pick the right drawable for each item type on the list and detail screens (vegetable, meat, fish).
 */
public class ImageUtils {
    public static int getImageResource(ItemType type) {
        if (type == null) return R.drawable.ic_launcher_background;

        switch (type) {
            case MEAT:      return R.drawable.imgmeat;
            case VEGETABLE: return R.drawable.imavegetal;
            case FISH:      return R.drawable.imgfish;
            default:        return R.drawable.ic_launcher_background;
        }
    }
}
