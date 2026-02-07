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

/**
 * Detail screen for one item. I show name, type, description, quantity, last entry/exit dates and
 * let the user add/remove stock (which I record as transactions) or open the modify screen.
 */
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

        getCurrentItemID();

        itemRepository = new ItemRepository(this);
        transactionRepository = new TransactionRepository(this);

        connectXML();
        addListeners();
        getCurrentItem();
    }

    /** I fill all the text views and the image from the item and the latest entry/exit dates. */
    private void fillInfoItem(Item item, LocalDate entry, LocalDate exit) {
        if (item == null) return;

        img.setImageResource(ImageUtils.getImageResource(item.getType()));

        // I show N/A for any null or empty value so the UI never breaks
        String notAvailable = getString(R.string.label_not_available);
        String strId = item.getId() <= 0 ? notAvailable : String.valueOf(item.getId());
        String strName = (item.getName() == null || item.getName().isEmpty()) ? notAvailable : item.getName();
        String strType = (item.getType() == null) ? notAvailable : item.getType().toString();
        String strDescription = (item.getDescription() == null || item.getDescription().isEmpty()) ? notAvailable : item.getDescription();
        String strQuantity = String.valueOf(item.getCurrentQuantity());

        String strEntry = (entry == null) ? notAvailable : entry.toString();
        String strExit = (exit == null) ? notAvailable : exit.toString();

        id.setText(strId);
        name.setText(strName);
        type.setText(strType);
        description.setText(strDescription);
        entryDate.setText(strEntry);
        exitDate.setText(strExit);
        quantity.setText(strQuantity);
    }

    /** I load the item and its last entry/exit dates from the DB and refresh the UI. */
    private void getCurrentItem() {
        LocalDate entry = transactionRepository.getDateByItemTypeAndID(TransactionType.ENTRY, idCurrentItem);
        LocalDate exit = transactionRepository.getDateByItemTypeAndID(TransactionType.EXIT, idCurrentItem);
        Item currentItem = itemRepository.getItem(idCurrentItem);
        fillInfoItem(currentItem, entry, exit);
    }

    /** I read ITEM_ID from the intent; if missing I show an error and finish. */
    private void getCurrentItemID() {
        idCurrentItem = getIntent().getIntExtra("ITEM_ID", -1);
        if (idCurrentItem == -1) {
            Toast.makeText(this, getString(R.string.error_item_not_found), Toast.LENGTH_LONG).show();
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
            IntentUtils.changeWithExtras(InfoItemActivity.this, UpdateActivity.class, "ITEM_ID", idCurrentItem);
        } else if (clickedId == btnPlus.getId()) {
            num++;
            etAddRemove.setText(String.valueOf(num));
        } else if (clickedId == btnMinus.getId()) {
            if (num > 0) num--;
            etAddRemove.setText(String.valueOf(num));
        } else if (clickedId == btnRemove.getId()) {
            handleStockUpdate(false);
        } else if (clickedId == btnAdd.getId()) {
            handleStockUpdate(true);
        }
    }

    /** I add or remove the amount entered, update the DB, and record a transaction. If remove fails (not enough stock) I show an error. */
    private void handleStockUpdate(boolean isAdding) {
        String input = etAddRemove.getText().toString().trim();
        if (input.isEmpty()) {
            Toast.makeText(this, getString(R.string.EmptyerrorMsg), Toast.LENGTH_SHORT).show();
            return;
        };

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

            if (result > 0) {
                String msg = isAdding ? getString(R.string.quantityAddSucces) : getString(R.string.quantityRemoveSucces);
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                Transaction t = new Transaction(LocalDate.now(), type, idCurrentItem);
                transactionRepository.insertTransaction(t);
            } else {
                Toast.makeText(this, getString(R.string.quatityModifyError), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, getString(R.string.EmptyerrorMsg), Toast.LENGTH_SHORT).show();
        }
        refresh();
    }

    /** I reload the item from DB, reset the counter and clear the add/remove input. */
    private void refresh() {
        getCurrentItem();
        num = 0;
        etAddRemove.setText("0");
    }

}