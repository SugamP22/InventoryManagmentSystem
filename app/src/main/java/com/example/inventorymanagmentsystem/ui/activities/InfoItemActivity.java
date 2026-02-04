package com.example.inventorymanagmentsystem.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.inventorymanagmentsystem.R;
import com.example.inventorymanagmentsystem.data.repository.ItemRepository;
import com.example.inventorymanagmentsystem.data.repository.TransactionRepository;
import com.example.inventorymanagmentsystem.models.Item;
import com.example.inventorymanagmentsystem.models.Transaction;
import com.example.inventorymanagmentsystem.models.TransactionType;
import com.example.inventorymanagmentsystem.utils.ImageUtils;
import com.example.inventorymanagmentsystem.utils.IntentUtils;

import java.time.LocalDate;

public class InfoItemActivity extends AppCompatActivity implements View.OnClickListener {

    // UI Components
    private AppCompatButton btnHome, btnModify, btnAdd, btnRemove, btnPlus, btnMinus;
    private ImageView img;
    private TextView id, quantity, name, description, type, entryDate, exitDate;
    private EditText etAddRemove;

    // Data variables
    private int idCurrentItem;
    private int num = 0;
    private TransactionRepository transactionRepository;
    private ItemRepository itemRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_information);

        //Get the ID first so we know what to load
        getCurrentItemID();

        //Initialize Repositories once (Better for memory)
        itemRepository = new ItemRepository(this);
        transactionRepository = new TransactionRepository(this);

        //Setup UI
        connectXML();
        addListeners();

        //Initial data load
        getCurrentItem();
    }

    /**
     * Updates the UI text fields with data from the Item and Transaction objects.
     */
    private void fillInfoItem(Item item, LocalDate entry, LocalDate exit) {
        if (item == null) return; // Safety check

        img.setImageResource(ImageUtils.getImageResource(item.getType()));

        // Logic to handle null or empty values for display
        String strId = item.getId() <= 0 ? "N/A" : String.valueOf(item.getId());
        String strName = (item.getName() == null || item.getName().isEmpty()) ? "N/A" : item.getName();
        String strType = (item.getType() == null) ? "N/A" : item.getType().toString();
        String strDescription = (item.getDescription() == null || item.getDescription().isEmpty()) ? "N/A" : item.getDescription();
        String strQuantity = String.valueOf(item.getCurrentQuantity());

        // Handle dates: if null, show "N/A"
        String strEntry = (entry == null) ? "N/A" : entry.toString();
        String strExit = (exit == null) ? "N/A" : exit.toString();

        // Setting texts to UI
        id.setText(strId);
        name.setText(strName);
        type.setText(strType);
        description.setText(strDescription);
        entryDate.setText(strEntry);
        exitDate.setText(strExit);
        quantity.setText(strQuantity);
    }

    /**
     * Fetches the latest data from the database and updates the screen.
     */
    private void getCurrentItem() {
        // Fetch the latest entry/exit dates for this specific item
        LocalDate entry = transactionRepository.getDateByItemTypeAndID(TransactionType.ENTRY, idCurrentItem);
        LocalDate exit = transactionRepository.getDateByItemTypeAndID(TransactionType.EXIT, idCurrentItem);

        // Get the item details
        Item currentItem = itemRepository.getItem(idCurrentItem);

        fillInfoItem(currentItem, entry, exit);
    }

    private void getCurrentItemID() {
        idCurrentItem = getIntent().getIntExtra("ITEM_ID", -1);
        if (idCurrentItem == -1) {
            Toast.makeText(this, "Error: Select item not found", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void addListeners() {
        btnHome.setOnClickListener(this);
        btnModify.setOnClickListener(this);
        btnRemove.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnPlus.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
    }

    private void connectXML() {
        btnHome = findViewById(R.id.home);
        btnModify = findViewById(R.id.modify);
        etAddRemove = findViewById(R.id.etAddRemove);
        id = findViewById(R.id.infoidtv);
        name = findViewById(R.id.infonombretv);
        description = findViewById(R.id.infoDescription);
        type = findViewById(R.id.infotypetv);
        entryDate = findViewById(R.id.infoDateEntry);
        exitDate = findViewById(R.id.infodateExit);
        btnPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);
        img = findViewById(R.id.infoImg);
        quantity = findViewById(R.id.infoCantidadtv);
        btnAdd = findViewById(R.id.add);
        btnRemove = findViewById(R.id.eliminate);
    }

    @Override
    public void onClick(View v) {
        int clickedId = v.getId();

        if (clickedId == btnHome.getId()) {
            finish();
        } else if (clickedId == btnModify.getId()) {
            // to Open Update activity providing it with id
            IntentUtils.changeWithExtras(InfoItemActivity.this, UpdateActivity.class, "ITEM_ID", idCurrentItem);
        } else if (clickedId == btnPlus.getId()) {
            num++;
            etAddRemove.setText(String.valueOf(num));
        } else if (clickedId == btnMinus.getId()) {
            if (num > 0) num--;
            etAddRemove.setText(String.valueOf(num));
        } else if (clickedId == btnRemove.getId()) {
            handleStockUpdate(false); // False for EXIT/Remove
        } else if (clickedId == btnAdd.getId()) {
            handleStockUpdate(true); // True for ENTRY/Add
        }
    }

    /**
     * Logic for adding/removing stock and recording the transaction.
     *
     * @param isAdding true if adding stock, false if removing.
     */
    private void handleStockUpdate(boolean isAdding) {
        String input = etAddRemove.getText().toString().trim();
        if (input.isEmpty()) return;

        int amount = Integer.parseInt(input);

        if (amount != 0) {
            int result;
            TransactionType type;

            if (isAdding) {
                result = itemRepository.addQuantity(amount, idCurrentItem);
                type = TransactionType.ENTRY;
            } else {
                result = itemRepository.removeQuantity(amount, idCurrentItem);
                type = TransactionType.EXIT;
            }

            // result > 0 means the database actually updated a row
            if (result > 0) {
                String msg = isAdding ? getString(R.string.quantityAddSucces) : getString(R.string.quantityRemoveSucces);
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

                // Record the history in Transaction table
                Transaction t = new Transaction(LocalDate.now(), type, idCurrentItem);
                transactionRepository.insertTransaction(t);

            } else {
                // This happens if removeQuantity fails due to "quantity >= amount" check in SQL
                Toast.makeText(this, getString(R.string.quatityModifyError), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, getString(R.string.EmptyerrorMsg), Toast.LENGTH_SHORT).show();
        }
        refresh(); // Update the screen and reset counter
    }

    /**
     * Resets the screen data and input fields after a successful transaction.
     */
    private void refresh() {
        getCurrentItem();     // Fetch fresh data from DB
        num = 0;              // Reset local counter
        etAddRemove.setText("0"); // Reset input field
    }

}