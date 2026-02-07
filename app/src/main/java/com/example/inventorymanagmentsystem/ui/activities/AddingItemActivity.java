package com.example.inventorymanagmentsystem.ui.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.inventorymanagmentsystem.R;
import com.example.inventorymanagmentsystem.data.repository.ItemRepository;
import com.example.inventorymanagmentsystem.data.repository.TransactionRepository;
import com.example.inventorymanagmentsystem.models.Item;
import com.example.inventorymanagmentsystem.models.ItemType;
import com.example.inventorymanagmentsystem.models.Transaction;
import com.example.inventorymanagmentsystem.models.TransactionType;
import com.example.inventorymanagmentsystem.utils.CleaningUtils;
import com.example.inventorymanagmentsystem.utils.ConversionUtils;
import com.example.inventorymanagmentsystem.utils.IntentUtils;
import com.example.inventorymanagmentsystem.utils.ValidationUtils;

import java.time.format.DateTimeParseException;

/**
 * Screen where I add a new item to the inventory. User enters name, category (via spinner),
 * description, quantity and date; on Create we validate, save the item and an initial ENTRY transaction.
 */
public class AddingItemActivity extends AppCompatActivity {
    private AppCompatButton btnHome, btnCreate;
    private EditText editTextName, editTextDescription, editTextQuantity, editTextDate;
    private Spinner spinnerType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_item);
        connectXML();

        // I set today's date as default so the user doesn't have to type it
        editTextDate.setText(ConversionUtils.getCurrentDate());

        // Spinner options must match ItemType enum order: VEGETABLE, MEAT, FISH
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.item_type_options, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(typeAdapter);

        btnCreate.setOnClickListener(v -> {
            ItemRepository itemRepository = new ItemRepository(this);
            TransactionRepository transactionRepository = new TransactionRepository(this);
            // I validate only the text fields here; type is always valid because it comes from the spinner
            if (ValidationUtils.isInputsValid(this, editTextName, editTextDescription, editTextQuantity)) {

                try {
                    int quantity = ConversionUtils.parseQuantity(editTextQuantity.getText().toString());
                    // I get the selected type from spinner position so there's no invalid type possible
                    ItemType type = ItemType.values()[spinnerType.getSelectedItemPosition()];

                    Item newItem = new Item();
                    newItem.setName(editTextName.getText().toString().trim());
                    newItem.setType(type);
                    newItem.setDescription(editTextDescription.getText().toString().trim());
                    newItem.setCurrentQuantity(quantity);
                    int id_item = itemRepository.returnIDItem(newItem);
                    Transaction transaction = new Transaction();
                    String dateStr = editTextDate.getText().toString().trim();
                    transaction.setDate(ConversionUtils.stringToLocalDate(dateStr));
                    transaction.setItem_id(id_item);
                    transaction.setType(TransactionType.ENTRY);
                    transactionRepository.insertTransaction(transaction);
                    Toast.makeText(this, getString(R.string.msg_item_created_success), Toast.LENGTH_SHORT).show();
                    CleaningUtils.clearData(editTextName, editTextDescription, editTextQuantity, editTextDate);
                    // I reset the type spinner back to first option after a successful create
                    spinnerType.setSelection(0);
                } catch (NumberFormatException e) {
                    editTextQuantity.setError(getString(R.string.validErrorMSG));
                    Toast.makeText(this, getString(R.string.msg_invalid_quantity), Toast.LENGTH_SHORT).show();
                } catch (DateTimeParseException e) {
                    editTextDate.setError(getString(R.string.hint_date_format));
                    Toast.makeText(this, getString(R.string.msg_invalid_date), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnHome.setOnClickListener(v -> IntentUtils.change(this, MainActivity.class));
    }

    private void connectXML() {
        editTextName = findViewById(R.id.etName);
        editTextDate = findViewById(R.id.etDate);
        editTextDescription = findViewById(R.id.etDescription);
        editTextQuantity = findViewById(R.id.etQuantity);
        spinnerType = findViewById(R.id.spinnerType);
        btnHome = findViewById(R.id.btnHomeAddV);
        btnCreate = findViewById(R.id.btnCreateAddV);
    }
}
