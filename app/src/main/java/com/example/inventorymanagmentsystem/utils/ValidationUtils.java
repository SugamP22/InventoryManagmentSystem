package com.example.inventorymanagmentsystem.utils;

import android.widget.EditText;
import com.example.inventorymanagmentsystem.models.ItemType;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ValidationUtils {

    public static boolean isInputsValid(EditText... fields) {
        for (EditText field : fields) {
            if (field.getText().toString().trim().isEmpty()) {
                field.setError("This field is required");
                field.requestFocus();
                return false;
            }
        }
        return true;
    }




}