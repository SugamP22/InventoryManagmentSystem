package com.example.inventorymanagmentsystem.models;

/**
 * I use this enum for the item category so we only allow VEGETABLE, MEAT or FISH. The order here
 * must match the order of options in the Add/Modify type spinner (item_type_options in strings.xml).
 */
public enum ItemType {
    VEGETABLE, MEAT, FISH
}
