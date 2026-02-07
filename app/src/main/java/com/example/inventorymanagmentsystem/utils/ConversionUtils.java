package com.example.inventorymanagmentsystem.utils;

import android.widget.EditText;

import com.example.inventorymanagmentsystem.models.ItemType;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * I use these helpers to turn user input into the types the app needs: quantity as int, type as
 * ItemType, date as LocalDate. I let exceptions bubble up so the activity can show the right error.
 */
public class ConversionUtils {
    public static String getText(EditText text) {
        return text.getText().toString().trim();
    }

    /** I throw NumberFormatException if the string isn't a valid number so the activity can show "Invalid quantity". */
    public static int parseQuantity(String num) throws NumberFormatException {
        return Integer.parseInt(num.trim());
    }

    /** I convert text like "meat" or "MEAT" to ItemType.MEAT; I throw if it's not a valid type. */
    public static ItemType parseType(String typeInput) throws IllegalArgumentException {
        return ItemType.valueOf(typeInput.trim().toUpperCase());
    }

    /** I return today's date in yyyy-MM-dd format for the date field default. */
    public static String getCurrentDate() {
        return LocalDate.now().toString();
    }

    /** I parse a yyyy-MM-dd string into LocalDate; I throw DateTimeParseException if format is wrong. */
    public static LocalDate stringToLocalDate(String dateString) throws DateTimeParseException {
        return LocalDate.parse(dateString);
    }
}
