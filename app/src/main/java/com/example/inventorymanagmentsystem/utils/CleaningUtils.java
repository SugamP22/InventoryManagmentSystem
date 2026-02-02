package com.example.inventorymanagmentsystem.utils;

import android.widget.EditText;

public class CleaningUtils {
    public static void clearData(EditText... fields) {
        for (EditText field : fields) {
            field.setText("");
        }

    }
}
