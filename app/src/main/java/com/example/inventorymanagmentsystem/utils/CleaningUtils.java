package com.example.inventorymanagmentsystem.utils;

import android.widget.EditText;

/**
 * I use this to clear form fields (e.g. after successfully adding an item) so the user starts with empty inputs.
 */
public class CleaningUtils {
    public static void clearData(EditText... fields) {
        for (EditText field : fields) {
            field.setText("");
        }
    }
}
