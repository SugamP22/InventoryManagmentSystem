package com.example.inventorymanagmentsystem.data.repository;

import android.content.Context;

import com.example.inventorymanagmentsystem.data.DatabaseHelper;
import com.example.inventorymanagmentsystem.data.dao.ItemDao;
import com.example.inventorymanagmentsystem.models.Item;
import com.example.inventorymanagmentsystem.models.ItemType;

import java.util.List;

/**
 * I use this class to talk to the item table through ItemDao. It gives me one place to get items,
 * add/update them, change quantity and load sample data when the DB is empty.
 */
public class ItemRepository {
    private final ItemDao itemDao;

    public ItemRepository(Context context) {
        DatabaseHelper db = DatabaseHelper.getDB(context);
        this.itemDao = db.itemDao();
    }

    /** I fill the item table with sample vegetables, meat and fish only when it's empty. */
    public void seedDataBase() {
        if (itemDao.getAllItems().isEmpty()) {

            // --- VEGETABLES ---
            itemDao.addItem(new Item("Carrots", ItemType.VEGETABLE, 15, "Fresh crunchy carrots"));
            itemDao.addItem(new Item("Broccoli", ItemType.VEGETABLE, 4, "Green vitamin-rich broccoli"));
            itemDao.addItem(new Item("Spinach", ItemType.VEGETABLE, 20, "Fresh baby spinach leaves"));
            itemDao.addItem(new Item("Potatoes", ItemType.VEGETABLE, 50, "Organic russet potatoes"));
            itemDao.addItem(new Item("Onions", ItemType.VEGETABLE, 30, "Red onions for cooking"));
            itemDao.addItem(new Item("Bell Peppers", ItemType.VEGETABLE, 12, "Colorful mix of peppers"));
            itemDao.addItem(new Item("Zucchini", ItemType.VEGETABLE, 8, "Summer garden zucchini"));
            itemDao.addItem(new Item("Garlic", ItemType.VEGETABLE, 25, "Strong aromatic garlic cloves"));

            // --- MEAT ---
            itemDao.addItem(new Item("Chicken Breast", ItemType.MEAT, 12, "Lean protein chicken breast"));
            itemDao.addItem(new Item("Beef Steak", ItemType.MEAT, 5, "Premium cut ribeye steak"));
            itemDao.addItem(new Item("Pork Chops", ItemType.MEAT, 10, "Thick cut bone-in pork chops"));
            itemDao.addItem(new Item("Ground Turkey", ItemType.MEAT, 15, "Lean ground turkey meat"));
            itemDao.addItem(new Item("Lamb Chops", ItemType.MEAT, 6, "Fresh spring lamb chops"));
            itemDao.addItem(new Item("Bacon", ItemType.MEAT, 1, "Smoked hickory bacon strips"));
            itemDao.addItem(new Item("Sausages", ItemType.MEAT, 18, "Traditional pork sausages"));

            // --- FISH ---
            itemDao.addItem(new Item("Salmon Fillet", ItemType.FISH, 8, "Wild-caught Atlantic salmon"));
            itemDao.addItem(new Item("Tuna Steak", ItemType.FISH, 4, "Sashimi grade yellowfin tuna"));
            itemDao.addItem(new Item("Cod Fillet", ItemType.FISH, 10, "Flaky white Atlantic cod"));
            itemDao.addItem(new Item("Shrimp", ItemType.FISH, 40, "Jumbo peeled and deveined shrimp"));
            itemDao.addItem(new Item("Sardines", ItemType.FISH, 3, "Small oily fish in olive oil"));
            itemDao.addItem(new Item("Sea Bass", ItemType.FISH, 5, "Whole Mediterranean sea bass"));
            itemDao.addItem(new Item("Mackerel", ItemType.FISH, 12, "Rich omega-3 mackerel fillets"));

        }
    }

    public List<Item> getAllItems() {
        return itemDao.getAllItems();
    }

    /** I call this when the user adds stock; returns rows updated so we know if it worked. */
    public int addQuantity(int quantity, int id) {
        return itemDao.addQuantity(quantity, id);
    }

    /** I call this when the user removes stock; returns 0 if not enough quantity. */
    public int removeQuantity(int quantity, int id) {
        return itemDao.removeQuantity(quantity, id);
    }

    public Item getItem(int num) {
        return itemDao.getItem(num);
    }

    public List<Item> getSpecificItems(ItemType type) {
        return itemDao.getSpecificItems(type);
    }

    /** I use this after inserting a new item so I can create the first transaction with the new id. */
    public int returnIDItem(Item item) {
        return (int) itemDao.returnID(item);
    }

    public void addItem(Item item) {
        itemDao.addItem(item);
    }

    /** I use this for the notifications screen: items with quantity below the given threshold. */
    public List<Item> getLowStockItems(int num) {
        return itemDao.notices(num);
    }

    public void updateItem(Item item) {
        itemDao.updateItem(item);
    }

}
