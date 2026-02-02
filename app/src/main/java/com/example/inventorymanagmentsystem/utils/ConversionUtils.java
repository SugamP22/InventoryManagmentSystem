package com.example.inventorymanagmentsystem.utils;

import android.widget.EditText;

import com.example.inventorymanagmentsystem.models.ItemType;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ConversionUtils {
    public static String getText(EditText text) {
        return text.getText().toString().trim();
    }

    // This method now throws the exception for the Activity to catch
    public static int parseQuantity(String num) throws NumberFormatException {
        return Integer.parseInt(num.trim());
    }

    // Helper to validate the Enum input
    public static ItemType parseType(String typeInput) throws IllegalArgumentException {
        // Converts "meat" or "MEAT" to the Enum MEAT
        return ItemType.valueOf(typeInput.trim().toUpperCase());
    }

    //get the current date as a String
    public static String getCurrentDate() {
        return LocalDate.now().toString(); // Returns "yyyy-MM-dd" by default
    }

    public static LocalDate stringToLocalDate(String dateString) throws DateTimeParseException {

        // Since the format is yyyy-MM-dd, parse() handles it automatically
        return LocalDate.parse(dateString);

    }

}
