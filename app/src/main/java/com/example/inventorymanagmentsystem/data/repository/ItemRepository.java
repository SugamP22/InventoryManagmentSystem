package com.example.inventorymanagmentsystem.data.repository;

import com.example.inventorymanagmentsystem.models.Item;
import com.example.inventorymanagmentsystem.models.Type;

import java.util.ArrayList;

public class ItemRepository {

    public static ArrayList<Item> getDefaultItems() {
        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(new Item(1,Type.FISH, "Atún"));
        itemList.add(new Item(2,Type.MEAT, "Pechuga de POllo"));
        itemList.add(new Item(3,Type.VEGETABLE, "Patatas"));
        itemList.add(new Item(4,Type.FISH, "Salmon"));
        itemList.add(new Item(5,Type.MEAT, "Allitas"));
        itemList.add(new Item(6,Type.VEGETABLE, "Cauliflor"));
        itemList.add(new Item(7,Type.FISH, "Sardinas"));
        itemList.add(new Item(8,Type.MEAT, "Ternera"));
        itemList.add(new Item(9,Type.VEGETABLE, "Zanahorias"));
        itemList.add(new Item(10,Type.FISH, "Merluza"));
        itemList.add(new Item(11,Type.MEAT, "Chuletas de cerdo"));
        itemList.add(new Item(13,Type.VEGETABLE, "Brocoli"));
        itemList.add(new Item(14,Type.FISH, "Bacalao"));
        itemList.add(new Item(15,Type.MEAT, "Jamón"));
        itemList.add(new Item(16,Type.VEGETABLE, "Cebollas"));
        itemList.add(new Item(17,Type.FISH, "Trucha"));
        return itemList;
    }
}
