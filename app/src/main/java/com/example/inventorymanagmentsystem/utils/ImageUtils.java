package com.example.inventorymanagmentsystem.utils;

import com.example.inventorymanagmentsystem.R;
import com.example.inventorymanagmentsystem.models.ItemType;

public class ImageUtils {
    // util method to get Img position

        public static int getImageResource(ItemType type) {
            if (type == null) return R.drawable.ic_launcher_background; // Fallback image

            switch (type) {
                case MEAT:      return R.drawable.imgmeat;      // Real drawable names
                case VEGETABLE: return R.drawable.imavegetal;
                case FISH:      return R.drawable.imgfish;
                default:        return R.drawable.ic_launcher_background;
            }
        }

}
