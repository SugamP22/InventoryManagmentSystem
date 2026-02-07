package com.example.inventorymanagmentsystem.utils;

import androidx.room.TypeConverter;

import com.example.inventorymanagmentsystem.models.ItemType;
import com.example.inventorymanagmentsystem.models.TransactionType;

import java.time.LocalDate;

/**
 * Room type converters: I use these so the DB can store LocalDate as Long (epoch day) and enums
 * (ItemType, TransactionType) as Strings. Room calls these automatically when reading/writing.
 */
public class ConvertersUtil {

    @TypeConverter
    public static Long fromLocalDate(LocalDate date) {
        return date == null ? null : date.toEpochDay();
    }

    @TypeConverter
    public static LocalDate toLocalDate(Long epochDay) {
        return epochDay == null ? null : LocalDate.ofEpochDay(epochDay);
    }

    @TypeConverter
    public static String fromTransactionType(TransactionType type) {
        return type == null ? null : type.name();
    }

    @TypeConverter
    public static TransactionType toTransactionType(String value) {
        return value == null ? null : TransactionType.valueOf(value);
    }

    @TypeConverter
    public static String fromItemType(ItemType type) {
        return type == null ? null : type.name();
    }

    @TypeConverter
    public static ItemType toItemType(String value) {
        return value == null ? null : ItemType.valueOf(value);
    }
}