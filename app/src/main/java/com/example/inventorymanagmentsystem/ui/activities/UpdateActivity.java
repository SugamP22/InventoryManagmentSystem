package com.example.inventorymanagmentsystem.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.inventorymanagmentsystem.R;
import com.example.inventorymanagmentsystem.data.repository.ItemRepository;
import com.example.inventorymanagmentsystem.data.repository.TransactionRepository;
import com.example.inventorymanagmentsystem.models.Item;
import com.example.inventorymanagmentsystem.models.TransactionType;
import com.example.inventorymanagmentsystem.utils.ConversionUtils;
import com.example.inventorymanagmentsystem.utils.IntentUtils;
import com.example.inventorymanagmentsystem.utils.ValidationUtils;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;

/**
 * Class that looks after updating a certain item in the DB
 */
public class UpdateActivity extends AppCompatActivity implements View.OnClickListener {
    AppCompatButton btnBack, btnUpdate;
    ItemRepository itemRepository;
    TransactionRepository transactionRepository;
    TextInputEditText id, name, category, description, totalQuantity, entry, exit;
    int currentItemID;

    Item itemToUpdate;


    /**
     * on create we connect our xml with our activity ,set listeners, verify out data is not null or empty and then update if possible
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
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
        getCurrentItemByIdAndFillData();
        setListeners();
    }

    /**
     * In this method we try to get the item and then check the data through before filling it inside the TextInputET
     */
    private void getCurrentItemByIdAndFillData() {
        itemToUpdate = itemRepository.getItem(currentItemID);

        if (itemToUpdate != null) {
            //Wrap integers in String.valueOf()
            id.setText(String.valueOf(itemToUpdate.getId()));
            name.setText(itemToUpdate.getName());
            category.setText(itemToUpdate.getType().toString());
            description.setText(itemToUpdate.getDescription());
            totalQuantity.setText(String.valueOf(itemToUpdate.getCurrentQuantity()));

            // Fetch Dates
            LocalDate dateEntry = transactionRepository.getDateByItemTypeAndID(TransactionType.ENTRY, currentItemID);
            LocalDate dateExit = transactionRepository.getDateByItemTypeAndID(TransactionType.EXIT, currentItemID);

            // NULL CHECK: Prevent crash if dates don't exist yet
            entry.setText(dateEntry != null ? dateEntry.toString() : getString(R.string.label_not_available));
            exit.setText(dateExit != null ? dateExit.toString() : getString(R.string.label_not_available));
        }
    }

    /**
     * Update the item if the current available data is valid
     */
    private void validateAndUpdate() {
        if (ValidationUtils.isInputsValid(this, name, category, description)) {
            try {
                itemToUpdate.setType(ConversionUtils.parseType(category.getText().toString()));
                itemToUpdate.setName(name.getText().toString());
                itemToUpdate.setDescription(description.getText().toString());
                itemRepository.updateItem(itemToUpdate);
                Toast.makeText(this, getString(R.string.updateSuccesfully), Toast.LENGTH_LONG).show();
                backToInfoView();
            } catch (IllegalArgumentException e) {
                // Catch Enum errors (e.g., user typed "Pizza" instead of "Meat")
                category.setError(getString(R.string.validItemType));
                Toast.makeText(this, getString(R.string.msg_invalid_item_type), Toast.LENGTH_SHORT).show();
            }
        }


    }


    /**
     * set listeners to the required views
     */
    private void setListeners() {
        btnUpdate.setOnClickListener(this);
        btnBack.setOnClickListener(this);

    }

    /**
     * connecting xml using R.id
     */
    private void connectXML() {
        btnBack = findViewById(R.id.btnBackUpdV);
        btnUpdate = findViewById(R.id.btnUpdate);
        id = findViewById(R.id.idItem);
        name = findViewById(R.id.nameIt);
        category = findViewById(R.id.categoryIt);
        description = findViewById(R.id.descriptionIt);
        totalQuantity = findViewById(R.id.totalQuantityIt);
        entry = findViewById(R.id.entryDateIt);
        exit = findViewById(R.id.exitDateIt);
    }

    /**
     * making views functional
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == btnBack.getId()) {
            backToInfoView();
        } else if (id == btnUpdate.getId()) {
            validateAndUpdate();
        }
    }

    /**
     * To change the view back to info view
     */
    private void backToInfoView() {
        IntentUtils.changeWithExtras(UpdateActivity.this, InfoItemActivity.class, "ITEM_ID", currentItemID);
    }
}