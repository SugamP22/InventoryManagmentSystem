package com.example.inventorymanagmentsystem.utils;

import android.content.Context;
import android.widget.EditText;

import com.example.inventorymanagmentsystem.R;
import com.google.android.material.textfield.TextInputEditText;

/**
 * I use these to check that required fields are not empty before saving. If any field is empty
 * I set the error message on it, focus it and return false so the activity can avoid submitting.
 */
public class ValidationUtils {

    /** I check each EditText (or TextInputEditText) and return false if any is empty. */
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

    /** Same as isInputsValid but for TextInputEditText (both work since TextInputEditText extends EditText). */
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