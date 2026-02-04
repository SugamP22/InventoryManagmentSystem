package com.example.inventorymanagmentsystem.utils;

import android.content.Context;
import android.widget.EditText;

import com.example.inventorymanagmentsystem.R;
import com.google.android.material.textfield.TextInputEditText;


public class ValidationUtils {

    public static boolean isInputsValid(Context context, EditText... fields) {
        for (EditText field : fields) {
            if (field.getText().toString().trim().isEmpty()) {
                field.setError(context.getString(R.string.requiredErrorMsg));
                field.requestFocus();
                return false;
            }
        }
        return true;
    }

    public static boolean isInputEditTValid(Context context, TextInputEditText... fields) {
        for (EditText field : fields) {
            if (field.getText().toString().trim().isEmpty()) {
                field.setError(context.getString(R.string.requiredErrorMsg));
                field.requestFocus();
                return false;
            }
        }
        return true;
    }


}