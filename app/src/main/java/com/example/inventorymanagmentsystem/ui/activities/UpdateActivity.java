package com.example.inventorymanagmentsystem.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.inventorymanagmentsystem.R;
import com.example.inventorymanagmentsystem.data.repository.ItemRepository;
import com.example.inventorymanagmentsystem.data.repository.TransactionRepository;
import com.example.inventorymanagmentsystem.models.Item;
import com.example.inventorymanagmentsystem.models.ItemType;
import com.example.inventorymanagmentsystem.models.TransactionType;
import com.example.inventorymanagmentsystem.utils.IntentUtils;
import com.example.inventorymanagmentsystem.utils.ValidationUtils;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;

/**
 * Screen where I modify an existing item's name, category and description. Loads the item by ITEM_ID,
 * fills the form (including type spinner), and on Update saves changes and goes back to info screen.
 */
public class UpdateActivity extends AppCompatActivity implements View.OnClickListener {
    AppCompatButton btnBack, btnUpdate;
    ItemRepository itemRepository;
    TransactionRepository transactionRepository;
    TextInputEditText id, name, description, totalQuantity, entry, exit;
    Spinner spinnerCategory;
    int currentItemID;

    Item itemToUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemRepository = new ItemRepository(this);
        transactionRepository = new TransactionRepository(this);
        setContentView(R.layout.updating_item);
        currentItemID = getIntent().getIntExtra("ITEM_ID", -1);
        if (currentItemID < 0) {
            Toast.makeText(this, getString(R.string.errorIntentLoad), Toast.LENGTH_SHORT).show();
            finish();
        }
        connectXML();
        setupTypeSpinner();
        getCurrentItemByIdAndFillData();
        setListeners();
    }

    /** I fill the category spinner with the same options as in Add screen; order = ItemType enum order */
    private void setupTypeSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.item_type_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
    }

    /**
     * I load the item from DB and fill all fields. For type I set the spinner selection to match the item's type.
     */
    private void getCurrentItemByIdAndFillData() {
        itemToUpdate = itemRepository.getItem(currentItemID);

        if (itemToUpdate != null) {
            id.setText(String.valueOf(itemToUpdate.getId()));
            name.setText(itemToUpdate.getName());
            description.setText(itemToUpdate.getDescription());
            totalQuantity.setText(String.valueOf(itemToUpdate.getCurrentQuantity()));
            // I set spinner to current type using enum ordinal (same order as item_type_options)
            spinnerCategory.setSelection(itemToUpdate.getType().ordinal());

            LocalDate dateEntry = transactionRepository.getDateByItemTypeAndID(TransactionType.ENTRY, currentItemID);
            LocalDate dateExit = transactionRepository.getDateByItemTypeAndID(TransactionType.EXIT, currentItemID);
            entry.setText(dateEntry != null ? dateEntry.toString() : getString(R.string.label_not_available));
            exit.setText(dateExit != null ? dateExit.toString() : getString(R.string.label_not_available));
        }
    }

    /** I validate name and description only; type is always valid from spinner. Then I save and go back to info. */
    private void validateAndUpdate() {
        if (ValidationUtils.isInputsValid(this, name, description)) {
            itemToUpdate.setType(ItemType.values()[spinnerCategory.getSelectedItemPosition()]);
            itemToUpdate.setName(name.getText().toString());
            itemToUpdate.setDescription(description.getText().toString());
            itemRepository.updateItem(itemToUpdate);
            Toast.makeText(this, getString(R.string.updateSuccesfully), Toast.LENGTH_LONG).show();
            backToInfoView();
        }
    }

    private void setListeners() {
        btnUpdate.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    private void connectXML() {
        btnBack = findViewById(R.id.btnBackUpdV);
        btnUpdate = findViewById(R.id.btnUpdate);
        id = findViewById(R.id.idItem);
        name = findViewById(R.id.nameIt);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        description = findViewById(R.id.descriptionIt);
        totalQuantity = findViewById(R.id.totalQuantityIt);
        entry = findViewById(R.id.entryDateIt);
        exit = findViewById(R.id.exitDateIt);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == btnBack.getId()) {
            backToInfoView();
        } else if (id == btnUpdate.getId()) {
            validateAndUpdate();
        }
    }

    private void backToInfoView() {
        IntentUtils.changeWithExtras(UpdateActivity.this, InfoItemActivity.class, "ITEM_ID", currentItemID);
    }
}
