package com.example.inventorymanagmentsystem.utils;

import androidx.room.TypeConverter;

import com.example.inventorymanagmentsystem.models.ItemType;
import com.example.inventorymanagmentsystem.models.TransactionType;

import java.time.LocalDate;

public class ConvertersUtil {

    // --- Converter for LocalDate ---
    @TypeConverter
    public static Long fromLocalDate(LocalDate date) {
        // I store the date as the number of days since 01-01-1970 because Room cannot save LocalDate directly
        return date == null ? null : date.toEpochDay(); // Turns date into a number
    }

    @TypeConverter
    public static LocalDate toLocalDate(Long epochDay) {
        // When I read from the DB I turn the stored number back into a LocalDate
        return epochDay == null ? null : LocalDate.ofEpochDay(epochDay); // Turns number back to date
    }

    // --- Converter for TransactionType Enum ---
    @TypeConverter
    public static String fromTransactionType(TransactionType type) {
        // I save the enum as its name (ENTRY / EXIT) so Room can store it as text
        return type == null ? null : type.name(); // Turns Enum into "ENTRY" or "EXIT"
    }

    @TypeConverter
    public static TransactionType toTransactionType(String value) {
        // Here I convert the stored String back into my TransactionType enum
        return value == null ? null : TransactionType.valueOf(value); // Turns String back to Enum
    }

    // --- Converter for ItemType Enum (VEGETABLE, MEAT, FISH) ---
    @TypeConverter
    public static String fromItemType(ItemType type) {
        // Same idea: I save the item category as a simple String
        return type == null ? null : type.name();
    }

    @TypeConverter
    public static ItemType toItemType(String value) {
        // And I rebuild the ItemType enum when I read from the database
        return value == null ? null : ItemType.valueOf(value);
    }
}